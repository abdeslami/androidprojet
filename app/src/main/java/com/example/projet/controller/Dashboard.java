package com.example.projet.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.auth.Login;
import com.example.projet.controller.categoryController.AddCategory;
import com.example.projet.controller.categoryController.CategoryListAdapter;
import com.example.projet.controller.productController.AddProduitActivity;
import com.example.projet.controller.userController.UserActivity;
import com.example.projet.dao.CategoryDAO;
import com.example.projet.model.Category;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {


    private Button btnProduct,btnCategory,btnUser;
    private CategoryListAdapter adapter;
    private ArrayList<Category> categoryList;
    private CategoryDAO dbHelper;
    private ListView listViewProduit;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_logout) {


            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        btnProduct = findViewById(R.id.gotoProduct);
        btnCategory = findViewById(R.id.gotoCategory);
        btnUser = findViewById(R.id.gotoUser);
        listViewProduit = findViewById(R.id.listViewCategories);


        categoryList = new ArrayList<>();
        dbHelper = new CategoryDAO(getApplicationContext());
        categoryList = dbHelper.loadCategories();


        adapter = new CategoryListAdapter(this, categoryList);
        listViewProduit.setAdapter(adapter);

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
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("USER_ID",userInd);
                startActivity(intent);
            }
        });




    }
}
