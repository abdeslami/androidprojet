package com.example.projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.controller.categoryController.AddCategory;
import com.example.projet.controller.productController.AddProduitActivity;
import com.example.projet.controller.userController.UserActivity;

public class Dashboard extends AppCompatActivity {

    private TextView textViewUsername, textViewLoginStatus;

    private Button buttonLogout,btnProduct,btnCategory,btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnProduct = findViewById(R.id.gotoProduct);
        btnCategory = findViewById(R.id.gotoCategory);
        btnUser = findViewById(R.id.gotoUser);

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AddCategory.class);
                startActivity(intent);
            }
        });
        int userInd=getIntent().getIntExtra("USER_ID",-1);
        SharedPreferences sp=getSharedPreferences("UserId",MODE_PRIVATE);
        sp.edit().putInt("USER_ID",userInd).apply();
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
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("USER_ID",userInd);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("nom", "Utilisateur");
        String email = sharedPreferences.getString("email", "email@example.com");

        textViewUsername.setText("");
        textViewLoginStatus.setText("Connecté  " );


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
