package com.example.projet.controller.productController;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddProduitActivity extends AppCompatActivity {

    private EditText etProduitNom, etProduitPrix, etProduitDescription, etProduitCategorieId;
    private ImageView ivProduitImage;
    private Button btnSelectImage, btnAddProduit,todetailsProduit;
    private DatabaseHelper dbHelper;
    private Bitmap selectedImage;
    private byte[] imageBytes;
    private static final int REQUEST_IMAGE_PICK = 103;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produit);

        etProduitNom = findViewById(R.id.etProduitNom);
        etProduitPrix = findViewById(R.id.etProduitPrix);
        etProduitDescription = findViewById(R.id.etProduitDescription);
        etProduitCategorieId = findViewById(R.id.etProduitCategorieId);
        ivProduitImage = findViewById(R.id.ivProduitImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddProduit = findViewById(R.id.btnAddProduit);
        todetailsProduit=findViewById(R.id.todetailsProduit);
        todetailsProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProduitActivity.this, ProduitDetailActivity.class);
                startActivity(intent);
            }});

        dbHelper = new DatabaseHelper(this);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        btnAddProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = etProduitNom.getText().toString().trim();
                String prixStr = etProduitPrix.getText().toString().trim();
                String description = etProduitDescription.getText().toString().trim();
                String categorieIdStr = etProduitCategorieId.getText().toString().trim();

                if(nom.isEmpty() || prixStr.isEmpty() || description.isEmpty() || categorieIdStr.isEmpty() || imageBytes == null) {
                    Toast.makeText(AddProduitActivity.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                    return;
                }

                double prix = Double.parseDouble(prixStr);
                int categorieId = Integer.parseInt(categorieIdStr);

                boolean inserted = dbHelper.insertProduit(nom, prix, description, imageBytes, categorieId);
                if(inserted) {
                    Toast.makeText(AddProduitActivity.this, "Produit ajouté", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddProduitActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivProduitImage.setImageBitmap(selectedImage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                imageBytes = baos.toByteArray();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
