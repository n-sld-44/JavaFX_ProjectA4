package org.example.womenshopfx.shop;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Data {

    private String name;
    private int capital;

    private List<String> brands;
    private List<String> types;

    public Data() {
    }

    public Data(String name, int capital, List<String> brands, List<String> types) {
        this.name = name;
        this.capital = capital;

        this.brands = brands;
        this.types = types;
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




    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", capital=" + capital +
                ", brands=" + brands +
                ", types=" + types +
                '}';
    }
}

