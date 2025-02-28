package com.example.projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.database.DatabaseHelper;

public class Registre extends AppCompatActivity {

    private EditText editTextNom, editTextPrenom, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLogin;

    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        editTextNom = findViewById(R.id.textNom);
        editTextPrenom = findViewById(R.id.textPrenom);
        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.textPasswordRegister);
        editTextConfirmPassword = findViewById(R.id.textConfirmPassword);
        buttonRegister = findViewById(R.id.buttonregister);
        textViewLogin = findViewById(R.id.textviewlogin);

        dbHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(v -> {
            String nom = editTextNom.getText().toString().trim();
            String prenom = editTextPrenom.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(Registre.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(Registre.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            } else {
                boolean userInserted = dbHelper.insertUser(nom, prenom, email, password);

                if (userInserted) {
                    Toast.makeText(Registre.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Registre.this, Login.class));
                    finish();
                } else {
                    Toast.makeText(Registre.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewLogin.setOnClickListener(v -> {
            startActivity(new Intent(Registre.this, Login.class));
        });
    }
}
