package org.example.womenshopfx.shop;

public abstract class Product {

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
        return this.name + ", " + this.purchasePrice + ", " + this.sellPrice;
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
}
