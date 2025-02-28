package com.example.projet.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet.database.DatabaseHelper;
import com.example.projet.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static final String TABLE_CATEGORIES = "category";
    private static final String COL_CATEGORY_ID = "id";
    private static final String COL_CATEGORY_NAME = "name";
    private static final String COL_CATEGORY_IMAGE = "image";
    private DatabaseHelper dbHelper;

    public CategoryDAO(Context context) {

        this.dbHelper = new DatabaseHelper(context);
    }

    public boolean insertCategory(String name, byte[] image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, name);
        values.put(COL_CATEGORY_IMAGE, image);
        long result = db.insert(TABLE_CATEGORIES, null, values);
        return result != -1;
    }

    public boolean deleteCategory(int categoryId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("category", "id=?", new String[]{String.valueOf(categoryId)});
        return result > 0;
    }
    public boolean updateCategory(int id, String name, byte[] image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, name);
        values.put(COL_CATEGORY_IMAGE, image);
        int result = db.update(TABLE_CATEGORIES, values, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    @SuppressLint("Range")
    public ArrayList<Category> loadCategories() {
        ArrayList<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("category", new String[]{"id", "name", "image"}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                Category category = new Category(id, name, image);
                categoryList.add(category);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return categoryList;
    }

}
