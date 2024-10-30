package org.example.womenshopfx.shop;

public class Accessories extends Product {
    public int MIN_SIZE = 0;
    public int MAX_SIZE = 0;

     public Accessories(String brand, String type ,int id, String name, double sellPrice,double  purchasePrice, String description, int quantity) {
         super(brand,type ,id ,name, sellPrice, purchasePrice, description, quantity);
     }

    public Accessories(String brand, String type ,int id, String name, double sellPrice,double  purchasePrice, String description) {
        super(brand,type ,id ,name, sellPrice, purchasePrice, description);
    }




}
