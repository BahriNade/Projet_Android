package com.example.myapplication2;

public class Produit {
        String id;
    String name;
            String prix;
            String categorie;

public Produit() {
        }

public Produit(String id, String name, String prix, String categorie) {
        this.id = id;
        this.name = name;
        this.prix = prix;
        this.categorie = categorie;
        }

public String getId() {
        return id;
        }

public String getName() {
        return name;
        }

public String getPrix() {
        return prix;
        }

public String getCategorie() {
        return categorie;
        }
        }