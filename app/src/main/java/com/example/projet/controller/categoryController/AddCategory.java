package com.example.projet.controller.categoryController;




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

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.Dashboard;
import com.example.projet.R;
import com.example.projet.controller.productController.AddProduitActivity;
import com.example.projet.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddCategory extends AppCompatActivity {

    private EditText etCategoryName;
    private ImageView ivCategoryImage;
    private Button btnSelectImage, btnAddCategory,btndetailsCategory,togoDashboard;
    private Bitmap selectedImage;
    private DatabaseHelper dbHelper;
    private static final int REQUEST_IMAGE_PICK = 100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        etCategoryName = findViewById(R.id.etCategoryName);
        ivCategoryImage = findViewById(R.id.ivCategoryImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddCategory = findViewById(R.id.btnAddCategory);
        togoDashboard=findViewById(R.id.togoDashboard);
        togoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategory.this, Dashboard.class);
                startActivity(intent);
            }});

        dbHelper = new DatabaseHelper(this);

        btnSelectImage.setOnClickListener(view -> {
            // Lancer l'intent pour sélectionner une image dans la galerie
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });
        btndetailsCategory=findViewById(R.id.btndetailsCategory);
        btndetailsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCategory.this, CategoryDetailActivity.class);
                startActivity(intent);
            }
        });

        btnAddCategory.setOnClickListener(view -> {
            String categoryName = etCategoryName.getText().toString().trim();
            if (categoryName.isEmpty() || selectedImage == null) {
                Toast.makeText(AddCategory.this, "Veuillez saisir le nom et sélectionner une image", Toast.LENGTH_SHORT).show();
                return;
            }
            // Convertir le Bitmap en tableau d’octets
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();

            boolean inserted = dbHelper.insertCategory(categoryName, imageBytes);
            if (inserted) {
                Toast.makeText(AddCategory.this, "Catégorie ajoutée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddCategory.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ivCategoryImage.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
