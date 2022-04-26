package com.example.kotprog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kotprog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisztracioActivity extends AppCompatActivity {

    private EditText editTextFelh;
    private EditText editTextJelsz;
    private EditText editTextJelszIsm;
    private EditText editTextEmail;
    private Spinner spinnerCsapat;
    private TextView textViewHiba;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisztracio);

        editTextFelh = findViewById(R.id.editTextFelhasznalonevReg);
        editTextEmail = findViewById(R.id.editTextEmailReg);
        editTextJelsz = findViewById(R.id.editTextJelszoReg);
        editTextJelszIsm = findViewById(R.id.editTextJelszoIsmReg);
        spinnerCsapat = findViewById(R.id.spinnerCsapatReg);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.csapat_nev_spinner,R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.spinner_text);
        spinnerCsapat.setAdapter(adapter);

        notificationHandler = new NotificationHandler(this);
    }

    public void onClickRegisztracioReg(View view)
    {
        textViewHiba = findViewById(R.id.textViewHibaReg);

        String felhNev = editTextFelh.getText().toString();
        String email = editTextEmail.getText().toString();
        String jelsz = editTextJelsz.getText().toString();
        String jelszIsm = editTextJelszIsm.getText().toString();
        String csapat = spinnerCsapat.getSelectedItem().toString();

        if (felhNev.isEmpty() || email.isEmpty() || jelsz.isEmpty() || jelszIsm.isEmpty() || csapat.isEmpty() || csapat.equals("Válassza ki a kedvenc csapatát"))
        {
            textViewHiba.setText(R.string.hibaUresMezo);
            textViewHiba.setVisibility(View.VISIBLE);
        }
        else if (!jelsz.equals(jelszIsm))
        {
            textViewHiba.setText(R.string.hibaJelszEgyez);
            textViewHiba.setVisibility(View.VISIBLE);
        }
        else if (jelsz.length() < 6)
        {
            textViewHiba.setText("A jelszónak legalább 6 karakter hósszúnak kell lennie");
            textViewHiba.setVisibility(View.VISIBLE);
        }
        else
        {
            textViewHiba.setVisibility(View.INVISIBLE);

            auth.createUserWithEmailAndPassword(email,jelsz).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        notificationHandler.send("Sikeres regisztráció!");
                        firestore.collection("Felhasznalo").document(email).set(new Felhasznalo(felhNev,email,jelsz,csapat));
                        onRegisztracio();
                    }
                    else {
                        textViewHiba.setText(R.string.regFail);
                        textViewHiba.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public void onRegisztracio()
    {
        Intent intentReg = new Intent(this, MenuActivity.class);

        startActivity(intentReg);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.menuItemVissza);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intentVissza = new Intent(this, KezdolapActivity.class);

        startActivity(intentVissza);

        finish();

        return true;
    }
}