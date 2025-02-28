package com.example.projet.controller.categoryController;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.dao.CategoryDAO;
import com.example.projet.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {

    private ListView listViewCategories;
    private CategoryListAdapter adapter;
    private List<Category> categoryList;
    private CategoryDAO dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        listViewCategories = findViewById(R.id.listViewCategories);
        categoryList = new ArrayList<>();
        dbHelper = new CategoryDAO(getApplicationContext());
        categoryList = dbHelper.loadCategories();


        adapter = new CategoryListAdapter(this, categoryList);
        listViewCategories.setAdapter(adapter);

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = categoryList.get(position);
                showOptionsDialog(selectedCategory, position);
            }
        });
    }



    private void showOptionsDialog(final Category category, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisissez une action");
        String[] options = {"Modifier", "Supprimer"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(CategoryDetailActivity.this, EditCategoryActivity.class);
                    intent.putExtra("CATEGORY_ID", category.getId());
                    startActivity(intent);
                } else if (which == 1) {
                    // Supprimer : confirmation et suppression
                    deleteCategory(category, position);
                }
            }
        });
        builder.show();
    }

    private void deleteCategory(final Category category, final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer la catégorie")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette catégorie ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean deleted = dbHelper.deleteCategory(category.getId());
                        if (deleted) {
                            categoryList.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(CategoryDetailActivity.this, "Catégorie supprimée", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CategoryDetailActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Non", null)
                .show();
    }
}
