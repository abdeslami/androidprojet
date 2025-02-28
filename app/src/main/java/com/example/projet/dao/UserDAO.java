package com.example.projet.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet.database.DatabaseHelper;
import com.example.projet.model.User;

public class UserDAO {

    private static final String TABLE_USERS = "users";

    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_PRENOM = "prenom";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASSWORD = "password";

    private final DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    @SuppressLint("Range")
    public User getInfoUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;

        try (Cursor cursor = db.query(TABLE_USERS,
                new String[]{COL_USER_ID, COL_USER_NAME, COL_USER_PRENOM, COL_USER_EMAIL, COL_USER_PASSWORD},
                COL_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
                String prenom = cursor.getString(cursor.getColumnIndex(COL_USER_PRENOM));
                String email = cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD));

                user = new User(id, name, prenom, email, password);
            }
        }
        return user;
    }

    public boolean insertUser(String nom, String prenom, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, nom);
        values.put(COL_USER_PRENOM, prenom);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public int checkUserCredentials(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int userId = -1;

        try (Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID},
                COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                userId = cursor.getInt(0);
            }
        }
        return userId;
    }

    public boolean updateUser(int userId, String name, String prenom, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_PRENOM, prenom);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);

        int result = db.update(TABLE_USERS, values, COL_USER_ID + "=?", new String[]{String.valueOf(userId)});
        return result > 0;
    }

    @SuppressLint("Range")
    public User getUserDetails(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;

        try (Cursor cursor = db.query(TABLE_USERS, null, COL_USER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
                String prenom = cursor.getString(cursor.getColumnIndex(COL_USER_PRENOM));
                String email = cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD));

                user = new User(userId, name, prenom, email, password);
            }
        }
        return user;
    }
}
