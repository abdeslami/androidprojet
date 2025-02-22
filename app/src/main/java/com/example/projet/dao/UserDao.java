package com.example.projet.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet.database.DatabaseHelper;
import com.example.projet.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public UserDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Ouvrir la base de données
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    // Fermer la base de données
    public void close() {
        dbHelper.close();
    }

    // Ajouter un utilisateur
    public long save(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put("nom", user.getNom());
        values.put("prenom", user.getPrenom());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        long id = database.insert("users", null, values);
        close();
        return id;
    }

    // Récupérer un utilisateur par ID
    public User findById(int id) {
        open();
        User user = null;
        Cursor cursor = database.query("users", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nom")),
                    cursor.getString(cursor.getColumnIndexOrThrow("prenom")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password"))
            );
            cursor.close();
        }
        close();
        return user;
    }

    // Récupérer tous les utilisateurs
    public List<User> findAll() {
        open();
        List<User> users = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("prenom")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password"))
                );
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return users;
    }

    // Mettre à jour un utilisateur
    public int update(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put("nom", user.getNom());
        values.put("prenom", user.getPrenom());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        int rows = database.update("users", values, "id = ?", new String[]{String.valueOf(user.getId())});
        close();
        return rows;
    }

    // Supprimer un utilisateur
    public void delete(int id) {
        open();
        database.delete("users", "id = ?", new String[]{String.valueOf(id)});
        close();
    }
}
