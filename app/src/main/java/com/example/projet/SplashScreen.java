package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000; // 3 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen); // Assurer l'affichage du layout

        // Handler pour attendre 3 secondes avant de rediriger
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Vérification de l'état de la connexion de l'utilisateur
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                // Redirection en fonction de l'état de connexion
                Intent intent;
                if (isLoggedIn) {
                    intent = new Intent(SplashScreen.this, Dashboard.class);
                } else {
                    intent = new Intent(SplashScreen.this, Login.class);
                }
                startActivity(intent);
                finish(); // Ferme SplashScreen après la redirection
            }
        }, SPLASH_TIME_OUT); // 3000 ms (3 secondes)
    }
}
