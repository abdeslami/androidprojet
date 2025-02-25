package com.example.projet.controller.productController;

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
import com.example.projet.model.Produit;

import java.util.List;

public class ProduitListAdapter extends BaseAdapter {

    private Context context;
    private List<Produit> produitList;
    private LayoutInflater inflater;

    public ProduitListAdapter(Context context, List<Produit> produitList) {
        this.context = context;
        this.produitList = produitList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return produitList.size();
    }

    @Override
    public Object getItem(int position) {
        return produitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produitList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitemproduct, parent, false);
            holder = new ViewHolder();
            holder.ivProduitImage = convertView.findViewById(R.id.ivProduitImage);
            holder.tvProduitNom = convertView.findViewById(R.id.tvProduitNom);
            holder.tvProduitPrix = convertView.findViewById(R.id.tvProduitPrix);
            holder.tvProduitDescription = convertView.findViewById(R.id.tvProduitDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Produit produit = produitList.get(position);
        holder.tvProduitNom.setText(produit.getNom());
        holder.tvProduitPrix.setText(produit.getPrix() + " €");
        holder.tvProduitDescription.setText(produit.getDescription());
        if (produit.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(produit.getImage(), 0, produit.getImage().length);
            holder.ivProduitImage.setImageBitmap(bitmap);
        } else {
            holder.ivProduitImage.setImageResource(R.drawable.ic_launcher_background);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivProduitImage;
        TextView tvProduitNom;
        TextView tvProduitPrix;
        TextView tvProduitDescription;
    }
}

