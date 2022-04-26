package com.example.kotprog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kotprog.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private FirebaseUser user;

    private Button buttonHirek;
    private Button buttonTabella;

    private Button buttonCsapat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonHirek = findViewById(R.id.buttonHirekMenu);
        buttonTabella = findViewById(R.id.buttonTabellaMenu);
        buttonCsapat = findViewById(R.id.buttonCsapatomMenu);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void onClickHirekMenu(View view)
    {
        Intent intentHirekMenu = new Intent(this, HirekActivity.class);

        startActivity(intentHirekMenu);
    }

    public void onClickTabellaMenu(View view)
    {
        Intent intentTabellaMenu = new Intent(this, TabellaActivity.class);

        startActivity(intentTabellaMenu);
    }

    public void onClickCsapatMenu(View view)
    {
        Intent intentCsapatMenu = new Intent(this, CsapatActivity.class);

        startActivity(intentCsapatMenu);
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
            menuItemVissza.setVisible(false);
        }
        else if(user.getEmail() != null)
        {
            getMenuInflater().inflate(R.menu.menu_bar_bejelentkezett, menu);
            MenuItem menuItemVissza = menu.findItem(R.id.menuItemVissza);
            MenuItem menuItemKij = menu.findItem(R.id.menuItemKijelentkezes);
            MenuItem menuItemFelh = menu.findItem(R.id.menuItemFelhasznalo);

            menuItemVissza.setVisible(false);
        }
        else
        {
//            Intent intentVissza = new Intent(this, KezdolapActivity.class);
//            startActivity(intentVissza);
//            finish();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Vissza") && user.isAnonymous())
        {
            user.delete();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, KezdolapActivity.class);
            startActivity(intent);
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