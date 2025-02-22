package com.example.projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    private TextView textViewUsername, textViewLoginStatus;
    private GridView gridViewCategoriesProducts;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialisation des vues
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewLoginStatus = findViewById(R.id.textViewLoginStatus);
        gridViewCategoriesProducts = findViewById(R.id.gridViewCategoriesProducts);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Récupérer les informations de l'utilisateur depuis SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("nom", "Utilisateur");
        String email = sharedPreferences.getString("email", "email@example.com");

        // Mettre à jour l'interface avec les données de l'utilisateur
        textViewUsername.setText("Nom d'utilisateur: " + username);
        textViewLoginStatus.setText("Connecté avec: " + email);

        // Simuler un GridView avec des catégories et produits (à adapter à ta logique)
        // Remplir le GridView avec des données (ici, tu utiliserais un Adapter)
        // gridViewCategoriesProducts.setAdapter(new MyAdapter(...));

        // Bouton de déconnexion
        buttonLogout.setOnClickListener(v -> {
            // Effacer les données de connexion de SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Retourner à l'écran de connexion
            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
