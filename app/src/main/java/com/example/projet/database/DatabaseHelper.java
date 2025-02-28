package com.example.projet.database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projet.model.Produit;
import com.example.projet.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shop.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASSWORD = "password";

    private static final String TABLE_CATEGORIES = "category";
    private static final String COL_CATEGORY_ID = "id";
    private static final String COL_CATEGORY_NAME = "name";
    private static final String COL_CATEGORY_IMAGE = "image";

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

    public boolean deleteProduit(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCTS, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

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


    public boolean insertCategory(String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, name);
        values.put(COL_CATEGORY_IMAGE, image);
        long result = db.insert(TABLE_CATEGORIES, null, values);
        return result != -1;
    }

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

    public boolean updateUser(int userId, String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);
        int result = db.update(TABLE_USERS, values, COL_USER_ID + "=?", new String[]{String.valueOf(userId)});
        return result > 0;
    }
    @SuppressLint("Range")
    public User getUserDetails(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COL_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
        User user=null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
                String email = cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD));

                user = new User(userId, name,name, email, password);

                cursor.close();
                return null;
            }
            cursor.close();
        }
        return user;
    }
    public boolean insertUser(String nom, String prenom, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, nom);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public int checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID}, COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
            cursor.close();
            return userId;
        } else {
            return -1;
        }
    }
    @SuppressLint("Range")
    public User getInfoUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COL_USER_ID, COL_USER_NAME, COL_USER_EMAIL, COL_USER_PASSWORD},
                COL_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null);
        User user=null;

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
            String email = cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD));
            user = new User(id, name, name, email, password);

            cursor.close();

        }
        return user;
    }





    public boolean updateUserPassword(int userId, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_PASSWORD, newPassword);
        int result = db.update(TABLE_USERS, values, COL_USER_ID + "=?", new String[]{String.valueOf(userId)});
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