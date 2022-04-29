package com.example.kotprog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.FileObserver;
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

public class TabellaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Tabella> tabellak;
    private TabellaAdapter tabellaAdapter;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference collRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabella);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null)
        {
            Intent intent = new Intent(this, KezdolapActivity.class);
            startActivity(intent);
            finish();
        }

        firestore = FirebaseFirestore.getInstance();
        collRef = firestore.collection("Tabella");
        tabellak = new ArrayList<>();
        tabellaAdapter = new TabellaAdapter(this,tabellak);
        recyclerView = findViewById(R.id.recViewTabella);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(tabellaAdapter);

        getAllTabella();
    }

    public void getAllTabella()
    {
        tabellak.clear();
        collRef.orderBy("helyezes").get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                for (QueryDocumentSnapshot doc : task.getResult())
                {
                    Tabella t = doc.toObject(Tabella.class);
                    tabellak.add(t);
                }
                tabellaAdapter.notifyDataSetChanged();
            }
            else
            {
                String[] csapatok = getResources().getStringArray(R.array.tabella_csapat);
                int[] pontok = getResources().getIntArray(R.array.tabella_pontok);


                for (int i = 0; i < csapatok.length; i++)
                {
                    Tabella t = new Tabella(i+1, csapatok[i], pontok[i]);
                    tabellak.add(t);
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