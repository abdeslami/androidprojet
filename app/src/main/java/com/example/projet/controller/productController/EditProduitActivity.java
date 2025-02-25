package com.example.projet.controller.productController;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProduitActivity extends AppCompatActivity {

    private EditText etProduitNom, etProduitPrix, etProduitDescription, etProduitCategorieId;
    private ImageView ivProduitImage;
    private Button btnSelectImage, btnUpdateProduit;
    private DatabaseHelper dbHelper;
    private int produitId;
    private Bitmap selectedImage;
    private byte[] imageBytes;
    private static final int REQUEST_IMAGE_PICK = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produit);

        etProduitNom = findViewById(R.id.etProduitNom);
        etProduitPrix = findViewById(R.id.etProduitPrix);
        etProduitDescription = findViewById(R.id.etProduitDescription);
        etProduitCategorieId = findViewById(R.id.etProduitCategorieId);
        ivProduitImage = findViewById(R.id.ivProduitImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdateProduit = findViewById(R.id.btnUpdateProduit);

        dbHelper = new DatabaseHelper(this);
        produitId = getIntent().getIntExtra("PRODUIT_ID", -1);

        if (produitId == -1) {
            Toast.makeText(this, "Produit introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadProduitDetails();

        btnSelectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });

        btnUpdateProduit.setOnClickListener(view -> showConfirmationDialog());
    }

    @SuppressLint("Range")
    private void loadProduitDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("product",
                new String[]{"name", "price", "description", "image", "category_id"},
                "id=?",
                new String[]{String.valueOf(produitId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String nom = cursor.getString(cursor.getColumnIndex("name"));
            double prix = cursor.getDouble(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            imageBytes = cursor.getBlob(cursor.getColumnIndex("image"));
            int categorieId = cursor.getInt(cursor.getColumnIndex("category_id"));

            etProduitNom.setText(nom);
            etProduitPrix.setText(String.valueOf(prix));
            etProduitDescription.setText(description);
            etProduitCategorieId.setText(String.valueOf(categorieId));

            if (imageBytes != null) {
                selectedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                ivProduitImage.setImageBitmap(selectedImage);
            }
            cursor.close();
        }
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Voulez-vous vraiment mettre à jour ce produit ?")
                .setPositiveButton("Oui", (dialog, which) -> updateProduit())
                .setNegativeButton("Non", null)
                .show();
    }

    @SuppressLint("Range")
    private void updateProduit() {
        String nom = etProduitNom.getText().toString().trim();
        String prixStr = etProduitPrix.getText().toString().trim();
        String description = etProduitDescription.getText().toString().trim();
        String categorieIdStr = etProduitCategorieId.getText().toString().trim();

        if (nom.isEmpty() || prixStr.isEmpty() || description.isEmpty() || categorieIdStr.isEmpty()) {
            Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        double prix = Double.parseDouble(prixStr);
        int categorieId = Integer.parseInt(categorieIdStr);

        if (imageBytes == null) {
            Toast.makeText(this, "L'ancienne image sera conservée", Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("product",
                    new String[]{"image"},
                    "id=?",
                    new String[]{String.valueOf(produitId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                imageBytes = cursor.getBlob(cursor.getColumnIndex("image"));
                cursor.close();
            }
        }

        boolean updated = dbHelper.updateProduit(produitId, nom, prix, description, imageBytes, categorieId);
        if (updated) {
            Toast.makeText(this, "Produit mis à jour avec succès", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivProduitImage.setImageBitmap(selectedImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    imageBytes = baos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erreur lors du chargement de l'image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
