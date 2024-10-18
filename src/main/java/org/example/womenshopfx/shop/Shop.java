package org.example.womenshopfx.shop;

import java.util.List;

public class Shop {

    private double capital;
    private double income = 0;
    private double expenses = 0;
    List<Product> products;

    public Shop(int capital) {
        this.capital = capital;
    }


    public void sellItem(Product product, int nbItems) {
        if (nbItems < 0) {
            System.out.println("Invalid number of items");
        } else
        if (product.getQuantity()<nbItems) {
            System.out.println("Not enough items in stock");
        } else {
            product.setQuantity(nbItems*-1);
            income += product.getSellPrice() * nbItems;
        }
    }

    public void buyItem(Product product, int nbItems) {
        if (nbItems < 0) {
            System.out.println("Invalid number of items");
        } else {
            if (product.getPurchasePrice()*nbItems>capital){
                System.out.println("Not enough capital");
            } else {
                product.setQuantity(product.getQuantity()+nbItems);
                expenses += product.getPurchasePrice() * nbItems;
            }
        }
    }




}
