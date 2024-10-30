package org.example.womenshopfx.shop;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.stream.Collectors;

public class Data {

    private String name;
    private double capital;

    private double totalIncomes;
    private double totalExpenses;
    private List<String> brands;
    private List<String> types;

    public Data() {
    }

    public Data(String name, double capital, List<String> brands, List<String> types, double totalIncomes, double totalExpenses) {
        this.name = name;
        this.capital = capital;

        this.brands = brands;
        this.types = types;
        this.totalIncomes = totalIncomes;
        this.totalExpenses = totalExpenses;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBrand(String brand) {
        brands.add(brand);
    }

    public double getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(double totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }


    public void sortBrands() {
        if (brands != null) {
            brands.sort(String::compareTo);
        }
    }

    public void trimStrings() {
        if (brands != null) {
            brands = brands.stream().map(String::trim).collect(Collectors.toList());
        }
        if (types != null) {
            types = types.stream().map(String::trim).collect(Collectors.toList());
        }
    }
}

