package com.example.projet.model;

public class Category {
    private  int id;
    private  String nom;
    private  String image;
    public  Category(int id,String nom,String image){
        this.id=id;
        this.nom=nom;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
