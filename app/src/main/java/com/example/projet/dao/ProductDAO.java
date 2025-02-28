package com.example.projet.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet.database.DatabaseHelper;
import com.example.projet.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final String TABLE_PRODUCTS = "product";
    private static final String COL_PRODUCT_ID = "id";
    private static final String COL_PRODUCT_NAME = "name";
    private static final String COL_PRODUCT_PRICE = "price";
    private static final String COL_PRODUCT_DESC = "description";
    private static final String COL_PRODUCT_IMAGE = "image";
    private static final String COL_PRODUCT_CATEGORY_ID = "category_id";
    private static final String TABLE_CATEGORIES = "category";
    private static final String COL_CATEGORY_ID = "id";
    private static final String COL_CATEGORY_NAME = "name";
    private static final String COL_CATEGORY_IMAGE = "image";
    private final DatabaseHelper dbHelper;

    public ProductDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }
    public boolean insertProduit(String nom, double prix, String description, byte[] image, int categorieId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, nom);
        values.put(COL_PRODUCT_PRICE, prix);
        values.put(COL_PRODUCT_DESC, description);
        values.put(COL_PRODUCT_IMAGE, image);
        values.put(COL_PRODUCT_CATEGORY_ID, categorieId);
        long result = db.insert(TABLE_PRODUCTS, null, values);
        return result != -1;
    }
    public boolean updateProduit(int id, String nom, double prix, String description, byte[] image, int categorieId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, nom);
        values.put(COL_PRODUCT_PRICE, prix);
        values.put(COL_PRODUCT_DESC, description);
        values.put(COL_PRODUCT_IMAGE, image);
        values.put(COL_PRODUCT_CATEGORY_ID, categorieId);
        int result = db.update(TABLE_PRODUCTS, values, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteProduit(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    @SuppressLint("Range")
    public List<Produit> getAllProduits() {
        List<Produit> produitList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID));
                String nom = cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME));
                double prix = cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(COL_PRODUCT_DESC));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(COL_PRODUCT_IMAGE));
                int categorieId = cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_CATEGORY_ID));
                Produit produit = new Produit(id, nom, prix, description, image, categorieId);
                produitList.add(produit);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return produitList;
    }
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIES, new String[]{COL_CATEGORY_NAME}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String categoryName = cursor.getString(cursor.getColumnIndex(COL_CATEGORY_NAME));
                categories.add(categoryName);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return categories;
    }


}
