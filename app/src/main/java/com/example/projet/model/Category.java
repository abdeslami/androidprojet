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

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public byte[] getImage() {
            return image;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }
    }
