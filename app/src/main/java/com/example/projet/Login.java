package com.example.projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.database.DatabaseHelper;

public class Login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        dbHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                int userId = dbHelper.checkUserCredentials(email, password);

                if (userId != -1) {
                    Toast.makeText(Login.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this, Dashboard.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("UserID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("USER_ID", userId);
                    editor.apply();
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(Login.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewRegister.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Registre.class));
        });
    }
}
