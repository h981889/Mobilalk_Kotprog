package com.example.kotprog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kotprog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KezdolapActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextJelsz;
    private TextView textViewHiba;

    private FirebaseAuth auth;

    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kezdolap);

        editTextEmail = findViewById(R.id.editTextEmailcim);
        editTextJelsz = findViewById(R.id.editTextJelszo);
        textViewHiba = findViewById(R.id.textViewHibaKezd);

        auth = FirebaseAuth.getInstance();

        notificationHandler = new NotificationHandler(this);
    }

    public void onClickBejelentkezes(View view)
    {
        String email = editTextEmail.getText().toString();
        String jelszo = editTextJelsz.getText().toString();

        if(email.isEmpty() || jelszo.isEmpty())
        {
            textViewHiba.setText(R.string.hibaUresMezo);
            textViewHiba.setVisibility(View.VISIBLE);
        }
        else
        {
            auth.signInWithEmailAndPassword(email,jelszo).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        notificationHandler.send("Sikeres belépés!");
                        onBelepes();
                    }
                    else {
                        textViewHiba.setText(R.string.loginFail);
                        textViewHiba.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public void onClickRegisztracio(View view)
    {
        Intent intentRegisztracio = new Intent(this, RegisztracioActivity.class);

        startActivity(intentRegisztracio);

        finish();
    }

    public void onClickVendegBejelentkezes(View view)
    {
        auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    notificationHandler.send("Sikeres vendég belépés!");
                    onBelepes();
                } else {
                    textViewHiba.setText(R.string.loginFail);
                    textViewHiba.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void onBelepes(){

        Intent intentMenu = new Intent(this, MenuActivity.class);

        startActivity(intentMenu);

        finish();
    }
}