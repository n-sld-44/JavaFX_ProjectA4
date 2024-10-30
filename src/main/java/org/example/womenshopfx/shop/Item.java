package org.example.womenshopfx.shop;

public class Item {

    private int quantity;
    private int size;
    private String color;
    private int discount;

    private int id;
    private int productId;


    public Item(int quantity, int size, String color, int id, int productId) {
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.id = id;
        this.productId = productId;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return String.format("Quantity: %d\nSize: %d\nColor: %s", quantity, size, color);
    }

    public double getDiscountedPrice(double price) {
        return price-price*((double) this.discount/100);
    }

    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
