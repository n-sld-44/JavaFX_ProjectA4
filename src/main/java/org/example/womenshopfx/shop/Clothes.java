package org.example.womenshopfx.shop;

public class Clothes extends Product{

    private int size;

    public int MIN_SIZE = 34;
    public int MAX_SIZE = 54;

    public Clothes(String brand, String type, int id,String name, double purchasePrice, double sellPrice,String description, int quantity,int size) {
        super(brand,type,id,name, purchasePrice, sellPrice,description,quantity);
        this.size = size;

    }
    public Clothes(String brand, String type, int id,String name, double purchasePrice, double sellPrice,String description) {
        super(brand,type,id,name, purchasePrice, sellPrice,description);
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
    public void setSize(int size) throws  IllegalArgumentException {
        if (size <MIN_SIZE || size/2 != 0 || MAX_SIZE >54) {
            throw new IllegalArgumentException("Size must be even and greater than 34 and less than 54");
        }
        this.size = size;

    }


}
