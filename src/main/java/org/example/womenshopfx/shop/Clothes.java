package org.example.womenshopfx.shop;

public class Clothes extends Product{

    private int size;


    public Clothes(String brand, String type, int id,String name, double purchasePrice, double sellPrice,String description, int quantity,int size) {
        super(brand,type,id,name, purchasePrice, sellPrice,description,quantity);
        this.size = size;

    }






    public int getSize() {
        return size;
    }
    public void setSize(int size) throws  IllegalArgumentException {
        if (size <34 || size/2 != 0 || size >54) {
            throw new IllegalArgumentException("Size must be even and greater than 34 and less than 54");
        }
        this.size = size;

    }
    @Override
    public String toString() {
        return super.toString() + ", " + this.size;
    }

}
