package com.example.kotprog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kotprog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CsapatActivity extends AppCompatActivity {

    private static final String LOG_TAG = CsapatActivity.class.getName();
    private RecyclerView recyclerView;
    private ArrayList<Csapat> csapatok;
    private CsapatAdapter csapatAdapter;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference collRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csapat);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        collRef = firestore.collection("Csapat");
        recyclerView = findViewById(R.id.recViewCsapat);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        csapatok = new ArrayList<>();
        csapatAdapter = new CsapatAdapter(this,csapatok);
        recyclerView.setAdapter(csapatAdapter);
        getAllCsapat();
    }

    public void getAllCsapat()
    {
        csapatok.clear();
        collRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for (QueryDocumentSnapshot doc : task.getResult())
                {
                    Csapat cs = doc.toObject(Csapat.class);
                    csapatok.add(cs);
                    Log.d(LOG_TAG,"belul: " + String.valueOf(csapatok.size()));
                }
                csapatAdapter.notifyDataSetChanged();
            }
            else
            {
                String[] nevek = getResources().getStringArray(R.array.csapat_nev);
                String[] stadionok = getResources().getStringArray(R.array.csapat_stadion);
                String[] edzok = getResources().getStringArray(R.array.csapat_edzo);
                String[] alapitasok = getResources().getStringArray(R.array.csapat_alapitas);
                TypedArray cimerek = getResources().obtainTypedArray(R.array.csapat_cimer);

                for (int i = 0; i < nevek.length; i++)
                {
                    Csapat cs = new Csapat(nevek[i], stadionok[i], edzok[i], alapitasok[i], cimerek.getResourceId(i,0));
                    csapatok.add(cs);
                }
            }
        });
    }

    public void getAllCsapatSorted(String method)
    {
        csapatok.clear();
        collRef.orderBy(method).get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for (QueryDocumentSnapshot doc : task.getResult())
                {
                    Csapat cs = doc.toObject(Csapat.class);
                    csapatok.add(cs);
                    Log.d(LOG_TAG,"belul: " + String.valueOf(csapatok.size()));
                }
                csapatAdapter = new CsapatAdapter(this,csapatok);
                recyclerView.setAdapter(csapatAdapter);
            }
            else
            {
                String[] nevek = getResources().getStringArray(R.array.csapat_nev);
                String[] stadionok = getResources().getStringArray(R.array.csapat_stadion);
                String[] edzok = getResources().getStringArray(R.array.csapat_edzo);
                String[] alapitasok = getResources().getStringArray(R.array.csapat_alapitas);
                TypedArray cimerek = getResources().obtainTypedArray(R.array.csapat_cimer);

                for (int i = 0; i < nevek.length; i++)
                {
                    Csapat cs = new Csapat(nevek[i], stadionok[i], edzok[i], alapitasok[i], cimerek.getResourceId(i,0));
                    csapatok.add(cs);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if(user.isAnonymous())
        {
            getMenuInflater().inflate(R.menu.menu_bar_csapat_bejelentkezett, menu);
            MenuItem menuItemVissza = menu.findItem(R.id.menuItemVissza);
            MenuItem menuItemKij = menu.findItem(R.id.menuItemKijelentkezes);
            MenuItem menuItemRendezABC = menu.findItem(R.id.menuItemRendezABC);
            MenuItem menuItemRendezEv = menu.findItem(R.id.menuItemRendezEvszam);
            MenuItem menuItemFelh = menu.findItem(R.id.menuItemFelhasznalo);

            menuItemFelh.setVisible(false);
        }
        else if(user.getEmail() != null)
        {
            getMenuInflater().inflate(R.menu.menu_bar_csapat_bejelentkezett, menu);
            MenuItem menuItemVissza = menu.findItem(R.id.menuItemVissza);
            MenuItem menuItemKij = menu.findItem(R.id.menuItemKijelentkezes);
            MenuItem menuItemRendezABC = menu.findItem(R.id.menuItemRendezABC);
            MenuItem menuItemRendezEv = menu.findItem(R.id.menuItemRendezEvszam);
            MenuItem menuItemFelh = menu.findItem(R.id.menuItemFelhasznalo);
        }
        else
        {
            Intent intentVissza = new Intent(this, KezdolapActivity.class);
            startActivity(intentVissza);
            finish();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Vissza"))
        {
            Intent intentVissza = new Intent(this, MenuActivity.class);
            startActivity(intentVissza);
            finish();
        }
        else if (item.getTitle().equals("Kijelentkezés"))
        {
            if(user.isAnonymous())
            {
                user.delete();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, KezdolapActivity.class);
                startActivity(intent);
                finish();
            }
            else if (user.getEmail() != null)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, KezdolapActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else if (item.getTitle().equals("Rendezés ABC"))
        {
            getAllCsapatSorted("nev");
            csapatAdapter.notifyDataSetChanged();
        }
        else if (item.getTitle().equals("Rendezés Évszám"))
        {
            getAllCsapatSorted("alapitva");
            csapatAdapter.notifyDataSetChanged();
        }
        else if (item.getTitle().equals("Felhasználó"))
        {
            Intent intent = new Intent(this, FelhasznaloActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}