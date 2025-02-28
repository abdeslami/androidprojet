package com.example.projet.controller.categoryController;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet.R;
import com.example.projet.model.Category;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter {

    private Context context;
    private List<Category> categoryList;
    private LayoutInflater inflater;

    public CategoryListAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itemcategory, parent, false);
            holder = new ViewHolder();
            holder.ivCategoryImage = convertView.findViewById(R.id.ivCategoryImage);
            holder.tvCategoryName = convertView.findViewById(R.id.tvCategoryName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getName());

        byte[] imageBytes = category.getImage();
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.ivCategoryImage.setImageBitmap(bitmap);
        } else {
            holder.ivCategoryImage.setImageResource(R.drawable.ic_launcher_background);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivCategoryImage;
        TextView tvCategoryName;
    }
}