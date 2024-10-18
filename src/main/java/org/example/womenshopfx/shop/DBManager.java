package org.example.womenshopfx.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.womenshopfx.shop.Data;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


 public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/womenshop";

    private String user;
    private String password;



    public Data data ;

     public DBManager() throws IOException {
         ObjectMapper mapper = new ObjectMapper();
         File file = new File("src/main/resources/properties.json");
         data = mapper.readValue(file, Data.class);
     }

     public List<Product> loadProducts() {
        try {
            this.loadCreds();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Product> products= new ArrayList<Product>();

        Connection dbCon = this.connector();
        if (dbCon != null) {
            try{
                Statement stmt = dbCon.createStatement();
                String query = "SELECT * FROM products";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {

                    int id_sql = rs.getInt("id");
                    // ID = 9bits for brand 9 bits for type 9 bits for id 5 bits for size
                    String brand = data.getBrands().get( (int) id_sql & 0b11111111100000000000000000000000 >> 23);
                    String type = data.getTypes().get( (int) id_sql & 0b0000000001111111110000000000000 >> 14);
                    int id = (int) id_sql & 0b00000000000000000011111111100000>>5;

                    // 5 bits 0->31 => +28 to get 28-59
                    int size = ((int) id_sql & 0b00000000000000000000000000011111)+28;

                    String name = rs.getString("name");
                    double purchasePrice = rs.getDouble("purchasePrice");
                    double sellPrice = rs.getDouble("sellPrice");
                    String description = rs.getString("Description");
                    int quantity = rs.getInt("quantity");

                    //ADD new cases if new product types are added
                    switch (type) {
                        case "Clothes":
                            products.add(new Clothes(brand, type, id, name, purchasePrice, sellPrice, description, size,quantity));
                            break;
                        case "Accessories":
                            products.add(new Accessories(brand, type, id, name, purchasePrice, sellPrice, description,quantity));
                            break;
                        case "Shoes":
                            products.add(new Shoes(brand, type, id, name, purchasePrice, sellPrice, description, size,quantity));
                            break;

                    }
                }
                this.close(dbCon, stmt, rs);
                return products;


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else System.out.println("Error: DB connection is null");
        return null;
    }







    private void loadCreds() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/creds.txt"));
        try{
            user = reader.readLine();
            password = reader.readLine();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }

    private Connection connector(){
        try{
            return DriverManager.getConnection(URL,user,password);
        }
        catch(Exception e){
            System.err.println(e);
            return null;
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        try{
            if(con != null){
                con.close();
            }
            if(stmt != null){
                stmt.close();
            }
            if(rs != null){
                rs.close();
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}
