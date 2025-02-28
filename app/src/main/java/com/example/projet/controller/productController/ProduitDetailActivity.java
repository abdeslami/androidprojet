package com.example.projet.controller.productController;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.dao.ProductDAO;
import com.example.projet.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class ProduitDetailActivity extends AppCompatActivity {

    private ListView listViewProduits;
    private ProduitListAdapter adapter;
    private List<Produit> produitList;
    private ProductDAO dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produit_list);

        listViewProduits = findViewById(R.id.listViewProduits);
        produitList = new ArrayList<>();
        dbHelper = new ProductDAO(getApplicationContext());

        loadProduits();

        adapter = new ProduitListAdapter(this, produitList);
        listViewProduits.setAdapter(adapter);

        listViewProduits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produit selectedProduit = produitList.get(position);
                showOptionsDialog(selectedProduit, position);
            }
        });
    }

    private void loadProduits() {
        produitList.clear();
        produitList.addAll(dbHelper.getAllProduits());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showOptionsDialog(final Produit produit, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisissez une action");
        String[] options = {"Modifier", "Supprimer"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(ProduitDetailActivity.this, EditProduitActivity.class);
                    intent.putExtra("PRODUIT_ID", produit.getId());
                    startActivity(intent);
                } else if (which == 1) {
                    deleteProduit(produit, position);
                }
            }
        });
        builder.show();
    }

    private void deleteProduit(final Produit produit, final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer le produit")
                .setMessage("Êtes-vous sûr de vouloir supprimer ce produit ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean deleted = dbHelper.deleteProduit(produit.getId());
                        if (deleted) {
                            produitList.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(ProduitDetailActivity.this, "Produit supprimé", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProduitDetailActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Non", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProduits();
    }
}
