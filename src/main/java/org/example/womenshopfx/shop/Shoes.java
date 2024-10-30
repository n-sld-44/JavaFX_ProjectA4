package org.example.womenshopfx.shop;

public class Shoes extends Product {
    private int size;
    public int MIN_SIZE = 36;
    public int MAX_SIZE = 50;

    public Shoes(String brand, String type, int id, String name, double purchasePrice, double sellPrice, String description, int quantity, int size) throws IllegalArgumentException {
        super(brand, type, id, name, purchasePrice, sellPrice, description, quantity);
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException("Size must be between 36 and 50");
        }
        this.size = size;
    }

    public Shoes(String brand, String type, int id, String name, double purchasePrice, double sellPrice, String description) throws IllegalArgumentException {
        super(brand, type, id, name, purchasePrice, sellPrice, description);
    }

    @Override
    public int getMinSize() {
        return MIN_SIZE;
    }

    @Override
    public int getMaxSize() {
        return MAX_SIZE;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) throws IllegalArgumentException {
        if (size < 36 || size > 50) {
            throw new IllegalArgumentException("Size must be greater than 36 and less than 50");
        }
        this.size = size;
    }
}


