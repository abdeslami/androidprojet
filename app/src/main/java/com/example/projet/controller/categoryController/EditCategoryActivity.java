package com.example.projet.controller.categoryController;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditCategoryActivity extends AppCompatActivity {

    private EditText etCategoryName;
    private ImageView ivCategoryImage;
    private Button btnSelectImage, btnUpdateCategory;
    private DatabaseHelper dbHelper;
    private int categoryId;
    private Bitmap selectedImage;
    private byte[] imageBytes;

    private static final int REQUEST_IMAGE_PICK = 101;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        etCategoryName = findViewById(R.id.etCategoryName);
        ivCategoryImage = findViewById(R.id.ivCategoryImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdateCategory = findViewById(R.id.btnUpdateCategory);

        dbHelper = new DatabaseHelper(this);

        categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        if (categoryId == -1) {
            Toast.makeText(this, "Catégorie introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCategoryDetails();

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etCategoryName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(EditCategoryActivity.this, "Veuillez saisir un nom", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (imageBytes == null) {
                    Toast.makeText(EditCategoryActivity.this, "Veuillez sélectionner une image", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean updated = dbHelper.updateCategory(categoryId, name, imageBytes);
                if (updated) {
                    Toast.makeText(EditCategoryActivity.this, "Catégorie mise à jour", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditCategoryActivity.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("Range")
    private void loadCategoryDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("category", new String[]{"name", "image"}, "id=?", new String[]{String.valueOf(categoryId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            imageBytes = cursor.getBlob(cursor.getColumnIndex("image"));

            etCategoryName.setText(name);
            if (imageBytes != null) {
                selectedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                ivCategoryImage.setImageBitmap(selectedImage);
            }
            cursor.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ivCategoryImage.setImageBitmap(selectedImage);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                imageBytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
