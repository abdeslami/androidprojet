package com.example.projet.controller.userController;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.controller.Dashboard;
import com.example.projet.R;
import com.example.projet.database.DatabaseHelper;
import com.example.projet.model.User;

public class UserActivity extends AppCompatActivity {

    private EditText etNom, etEmail, etPrenom, etPassword;
    private Button btnSaveChanges,togoDashboard;
    private DatabaseHelper dbHelper;
    private int userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenome);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        dbHelper = new DatabaseHelper(this);
        togoDashboard = findViewById(R.id.togoDashboard);
        togoDashboard.setOnClickListener(view -> {
            Intent intent = new Intent(UserActivity.this, Dashboard.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserID", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("USER_ID",-1);


        if (userId == -1) {
            Toast.makeText(this, "Utilisateur introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserDetails();
        getInfoUser(userId);

        btnSaveChanges.setOnClickListener(view -> saveUserChanges());
    }

    private void loadUserDetails() {
        User userData = dbHelper.getUserDetails(userId);

        if (userData != null) {
            etNom.setText(userData.getNom());
            etEmail.setText(userData.getEmail());
        }
    }

    private void saveUserChanges() {
        String nom = etNom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean updated = dbHelper.updateUser(userId, nom, email, password);
        if (updated) {
            Toast.makeText(this, "Informations mises à jour", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
        }
    }


    private void getInfoUser(int userId) {
        User user = dbHelper.getInfoUser(userId);
        etNom.setText(user.getNom());
        etEmail.setText(user.getEmail());
        etPassword.setText(user.getPassword());
        etPrenom.setText(user.getPrenom());

    }

}
