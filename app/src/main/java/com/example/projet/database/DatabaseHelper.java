package com.example.projet.database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projet.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom de la base de données
    private static final String DATABASE_NAME = "shopoujda1.db";
    private static final int DATABASE_VERSION = 2;

    // Table Utilisateurs
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASSWORD = "password";

    // Table Catégories
    private static final String TABLE_CATEGORIES = "category";
    private static final String COL_CATEGORY_ID = "id";
    private static final String COL_CATEGORY_NAME = "name";
    private static final String COL_CATEGORY_IMAGE = "image";

    // Table Produits
    private static final String TABLE_PRODUCTS = "product";
    private static final String COL_PRODUCT_ID = "id";
    private static final String COL_PRODUCT_NAME = "name";
    private static final String COL_PRODUCT_PRICE = "price";
    private static final String COL_PRODUCT_DESC = "description";
    private static final String COL_PRODUCT_IMAGE = "image";
    private static final String COL_PRODUCT_CATEGORY_ID = "category_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Insertion d'un produit
    public boolean insertProduit(String nom, double prix, String description, byte[] image, int categorieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, nom);
        values.put(COL_PRODUCT_PRICE, prix);
        values.put(COL_PRODUCT_DESC, description);
        values.put(COL_PRODUCT_IMAGE, image);
        values.put(COL_PRODUCT_CATEGORY_ID, categorieId);
        long result = db.insert(TABLE_PRODUCTS, null, values);
        return result != -1;
    }

    // Mise à jour d'un produit
    public boolean updateProduit(int id, String nom, double prix, String description, byte[] image, int categorieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, nom);
        values.put(COL_PRODUCT_PRICE, prix);
        values.put(COL_PRODUCT_DESC, description);
        values.put(COL_PRODUCT_IMAGE, image);
        values.put(COL_PRODUCT_CATEGORY_ID, categorieId);
        int result = db.update(TABLE_PRODUCTS, values, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Suppression d'un produit
    public boolean deleteProduit(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Récupération de tous les produits
    @SuppressLint("Range")
    public List<Produit> getAllProduits() {
        List<Produit> produitList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
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
    /**
     *
     *
     * */

    public boolean insertCategory(String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, name);
        values.put(COL_CATEGORY_IMAGE, image);
        long result = db.insert(TABLE_CATEGORIES, null, values);
        return result != -1;
    }

    // Dans DatabaseHelper.java
    public boolean deleteCategory(int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("category", "id=?", new String[]{String.valueOf(categoryId)});
        return result > 0;
    }
    public boolean updateCategory(int id, String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, name);
        values.put(COL_CATEGORY_IMAGE, image);
        int result = db.update(TABLE_CATEGORIES, values, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_EMAIL + " TEXT UNIQUE, " +
                COL_USER_PASSWORD + " TEXT)";

        String createCategoriesTable = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COL_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY_NAME + " TEXT UNIQUE, " +
                COL_CATEGORY_IMAGE + " BLOB)";

        String createProductsTable = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT, " +
                COL_PRODUCT_PRICE + " REAL, " +
                COL_PRODUCT_DESC + " TEXT, " +
                COL_PRODUCT_IMAGE + " BLOB, " +
                COL_PRODUCT_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY (" + COL_PRODUCT_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(id))";

        // Exécution des requêtes de création
        db.execSQL(createUsersTable);
        db.execSQL(createCategoriesTable);
        db.execSQL(createProductsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
}