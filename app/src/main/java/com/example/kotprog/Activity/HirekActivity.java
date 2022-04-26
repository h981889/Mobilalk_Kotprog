package com.example.kotprog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kotprog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HirekActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Hir> hirek;
    private HirekAdapter hirekAdapter;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference collRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hirek);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        collRef = firestore.collection("Hir");
        hirek = new ArrayList<>();
        hirekAdapter = new HirekAdapter(this,hirek);
        recyclerView = findViewById(R.id.recViewHirek);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(hirekAdapter);

        getAllHir();

    }

    public void getAllHir()
    {
        hirek.clear();
        collRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for (QueryDocumentSnapshot doc : task.getResult())
                {
                    Hir h = doc.toObject(Hir.class);
                    hirek.add(h);
                }
                hirekAdapter.notifyDataSetChanged();
            }
            else
            {
                String[] cimek = getResources().getStringArray(R.array.hirek_cim);
                String[] hir = getResources().getStringArray(R.array.hirek_hir);
                TypedArray kepek = getResources().obtainTypedArray(R.array.hirek_kep);

                for (int i = 0; i < cimek.length; i++)
                {
                    Hir h = new Hir(cimek[i],hir[i],kepek.getResourceId(i,0));
                    hirek.add(h);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if(user.isAnonymous())
        {
            getMenuInflater().inflate(R.menu.menu_bar_bejelentkezett, menu);
            MenuItem menuItemVissza = menu.findItem(R.id.menuItemVissza);
            MenuItem menuItemKij = menu.findItem(R.id.menuItemKijelentkezes);
            MenuItem menuItemFelh = menu.findItem(R.id.menuItemFelhasznalo);

            menuItemFelh.setVisible(false);
        }
        else if(user.getEmail() != null)
        {
            getMenuInflater().inflate(R.menu.menu_bar_bejelentkezett, menu);
            MenuItem menuItemVissza = menu.findItem(R.id.menuItemVissza);
            MenuItem menuItemKij = menu.findItem(R.id.menuItemKijelentkezes);
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
        else if (item.getTitle().equals("Felhasználó"))
        {
            Intent intent = new Intent(this, FelhasznaloActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}