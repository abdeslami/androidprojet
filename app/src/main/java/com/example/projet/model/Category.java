package com.example.projet.model;


    public class Category {
        private int id;
        private String name;
        private byte[] image;

        public Category(int id, String name, byte[] image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }

        // Getters et Setters
        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public byte[] getImage() {
            return image;
        }
    }
