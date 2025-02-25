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

import com.example.projet.controller.categoryController.AddCategory;
import com.example.projet.controller.productController.AddProduitActivity;

public class Dashboard extends AppCompatActivity {

    private TextView textViewUsername, textViewLoginStatus;

    private Button buttonLogout,btnProduct,btnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnProduct = findViewById(R.id.gotoProduct);
        btnCategory = findViewById(R.id.gotoCategory);

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AddCategory.class);
                startActivity(intent);
            }
        });
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AddProduitActivity.class);
                startActivity(intent);
            }
        });
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewLoginStatus = findViewById(R.id.textViewLoginStatus);
        buttonLogout = findViewById(R.id.buttonLogout);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("nom", "Utilisateur");
        String email = sharedPreferences.getString("email", "email@example.com");

        textViewUsername.setText("Nom d'utilisateur: " + username);
        textViewLoginStatus.setText("Connecté avec: " + email);


        buttonLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(Dashboard.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
