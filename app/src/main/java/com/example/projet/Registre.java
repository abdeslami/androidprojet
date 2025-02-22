package com.example.projet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registre extends AppCompatActivity {

    private EditText editTextNom, editTextPrenom, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        // Initialisation des vues
        editTextNom = findViewById(R.id.textNom);
        editTextPrenom = findViewById(R.id.textPrenom);
        editTextEmail = findViewById(R.id.emailRegister);
        editTextPassword = findViewById(R.id.textPasswordRegister);
        editTextConfirmPassword = findViewById(R.id.textConfirmPassword);
        buttonRegister = findViewById(R.id.buttonregister);
        textViewLogin = findViewById(R.id.textviewlogin);

        // Action du bouton Inscription
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
                // Sauvegarder les informations dans SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nom", nom);
                editor.putString("prenom", prenom);
                editor.putString("email", email);
                editor.putString("password", password); // ⚠️ Ne pas enregistrer en clair dans une vraie application !
                editor.apply();

                Toast.makeText(Registre.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();

                // Rediriger vers la page de connexion
                startActivity(new Intent(Registre.this, Login.class));
                finish();
            }
        });

        // Action pour aller à la page de connexion
        textViewLogin.setOnClickListener(v -> {
            startActivity(new Intent(Registre.this, Login.class));
        });
    }
}
