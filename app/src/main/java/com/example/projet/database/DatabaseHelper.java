package com.example.projet.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom de la base de données
    private static final String DATABASE_NAME = "oujdashop.db";
    private static final int DATABASE_VERSION = 1;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création des tables
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