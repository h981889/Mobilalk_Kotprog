package com.example.kotprog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kotprog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class FelhasznaloActivity extends AppCompatActivity {


    private EditText editTextFelh;
    private EditText editTextJelsz;
    private static final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private String csapat;

    private Felhasznalo felhasznaloOrig;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DocumentReference docRef;
    private CollectionReference collRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_felhasznalo);

        editTextFelh = findViewById(R.id.editTextFelhNevFelhasznalo);
        editTextJelsz = findViewById(R.id.editTextJelszoFelhasznalo);

        felhasznaloOrig = new Felhasznalo();

        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        docRef = firestore.collection("Felhasznalo").document(email);

        getFelhasznaloByEmail();
    }

    public void getFelhasznaloByEmail()
    {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    felhasznaloOrig = task.getResult().toObject(Felhasznalo.class);
                    editTextFelh.setText(felhasznaloOrig.getFelhasznalonev());
                    editTextJelsz.setText(felhasznaloOrig.getJelszo());
                    csapat = felhasznaloOrig.getKedvencCsapat();
                }
            }
        });
    }

    public void onClickModositasFelhasznalo(View view)
    {
        if(!felhasznaloOrig.getJelszo().equals(editTextJelsz.getText().toString()))
        {
            AuthCredential cred = EmailAuthProvider.getCredential(email,editTextJelsz.getText().toString());

            user.reauthenticate(cred);

            docRef.update("felhasznalonev",editTextFelh.getText().toString());
            docRef.update("jelszo",editTextJelsz.getText().toString());
        }
        else if (!felhasznaloOrig.getFelhasznalonev().equals(editTextFelh.getText().toString()))
        {
            docRef.update("felhasznalonev",editTextFelh.getText().toString());
        }
    }

    public void onClickTorlesFelhasznalo(View view)
    {
        docRef.delete();
        user.delete();
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, KezdolapActivity.class);
        startActivity(intent);
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

        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);

        finish();

        return true;
    }
}