package com.example.projet.model;

public class Produit {
    private int id;
    private String nom;
    private double prix;
    private String description;
    private byte[] image;
    private int categorieId;

    public Produit(int id, String nom, double prix, String description, byte[] image, int categorieId) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.image = image;
        this.categorieId = categorieId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public String getDescription() {

        return description;
    }
    public void setDescription(String description) {

        this.description = description;
    }
    public byte[] getImage() {

        return image;
    }
    public void setImage(byte[] image) {

        this.image = image;
    }
    public int getCategorieId() {

        return categorieId;
    }
    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }
}
