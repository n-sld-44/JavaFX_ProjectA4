package org.example.womenshopfx.shop;

import java.util.Locale;

public class Product {

    public static final int MIN_SIZE = 0;
    public static final int MAX_SIZE = 0;

    private String brand;
    private String type;
    private int id;

    private String name;
    private double purchasePrice;
    private double sellPrice;
    private String description;
    private String image;
    private int quantity;


    public Product(String brand, String type,int id, String name, double purchasePrice,double sellPrice,String description, int quantity) throws IllegalArgumentException {
        this.brand = brand;
        this.type = type;
        this.id = id;
        this.name = name;
        if (purchasePrice < 0 || sellPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;

        this.description = description;
        this.quantity = quantity;
    }

    public Product(String brand, String type,int id, String name, double purchasePrice,double sellPrice,String description) throws IllegalArgumentException {
        this.brand = brand;
        this.type = type;
        this.id = id;
        this.name = name;
        if (purchasePrice < 0 || sellPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;

        this.description = description;
    }


    public int getMinSize() {
        return MIN_SIZE;
    }
    public int getMaxSize() {
        return MAX_SIZE;
    }





    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }



    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;

    }

    public double getSellPrice() {
        return sellPrice;
    }
    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Name: %s\nBrand: %s\nPurchase Price: %.2f\nSell Price: %.2f", this.name,this.brand, this.purchasePrice, this.sellPrice);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity)  throws IllegalArgumentException {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public int compareByName(Product product) {
        return this.name.compareTo(product.name);
    }

    public int compareBySellPrice(Product product) {
        return Double.compare(this.sellPrice, product.sellPrice);
    }

    public int compareByPurchasePrice(Product product) {
        return Double.compare(this.purchasePrice, product.purchasePrice);
    }


    public void applyDiscount(double discount) throws IllegalArgumentException {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        this.sellPrice = this.sellPrice * (1 - discount / 100);
    }


}
