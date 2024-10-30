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


     public Data data;

     public DBManager() throws IOException {
         ObjectMapper mapper = new ObjectMapper();
         File file = new File("src/main/resources/properties.json");
         data = mapper.readValue(file, Data.class);
         data.trimStrings();
     }

     public List<Product> loadProducts() {
         try {
             this.loadCreds();
             System.out.println("Creds loaded");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         List<Product> products = new ArrayList<Product>();

         Connection dbCon = this.connector();
         if (dbCon != null) {
             System.out.println("Connection established");
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "SELECT * FROM products";
                 ResultSet rs = stmt.executeQuery(query);
                 while (rs.next()) {

                    /* This got refactored, it had too much complexity
                    int id_sql = rs.getInt("id");
                    // ID = 9bits for brand 9 bits for type 9 bits for id 5 bits for size
                    String brand = data.getBrands().get( (int) (id_sql & 0b11111111100000000000000000000000) >> 23);
                    String type = data.getTypes().get( (int) ((id_sql & 0b0000000001111111110000000000000) >> 14));
                    int id = (int) ((id_sql & 0b00000000000000000011111111100000)>>5);
                    int size = ((int) (id_sql & 0b00000000000000000000000000011111))+28;

                    */


                     int id = rs.getInt("product_id");
                     String type = rs.getString("product_type");
                     String brand = rs.getString("product_brand");
                     String name = rs.getString("product_name");
                     double purchasePrice = rs.getDouble("product_purchased_price");
                     double sellPrice = rs.getDouble("product_sell_price");
                     String description = rs.getString("product_description");


                     //ADD new cases if new product types are added
                     switch (type) {
                         case "Clothes":
                             products.add(new Clothes(brand, type, id, name, purchasePrice, sellPrice, description));
                             break;
                         case "Accessories":
                             products.add(new Accessories(brand, type, id, name, purchasePrice, sellPrice, description));
                             break;
                         case "Shoes":
                             products.add(new Shoes(brand, type, id, name, purchasePrice, sellPrice, description));
                             break;

                     }
                 }
                 this.close(dbCon, stmt, rs);
                 return products;


             } catch (SQLException e) {
                 e.printStackTrace();
             }

         } else System.out.println("Error: DB connection is null");
         return null;
     }


     private void loadCreds() throws FileNotFoundException {
         BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/creds.txt"));
         try {
             this.user = reader.readLine();
             this.password = reader.readLine();
         } catch (Exception e) {
             System.err.println(e);
         }
     }

     private Connection connector() {
         try {
             return DriverManager.getConnection(URL, user, password);
         } catch (Exception e) {
             System.err.println(e);
             return null;
         }
     }

     private void close(Connection con, Statement stmt, ResultSet rs) {
         try {
             if (con != null) {
                 con.close();
             }
             if (stmt != null) {
                 stmt.close();
             }
             if (rs != null) {
                 rs.close();
             }
         } catch (Exception e) {
             System.err.println(e);
         }
     }

     public List<Product> loadFilteredProducts(String selectorType, String filter) {
         try {
             this.loadCreds();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         List<Product> products = new ArrayList<Product>();
         Connection dbCon = this.connector();
         if (dbCon != null) {
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "SELECT * FROM products WHERE " + selectorType + " = '" + filter + "'";
                 ResultSet rs = stmt.executeQuery(query);
                 while (rs.next()) {
                     int id = rs.getInt("product_id");
                     String type = rs.getString("product_type");
                     String brand = rs.getString("product_brand");
                     String name = rs.getString("product_name");
                     double purchasePrice = rs.getDouble("product_purchased_price");
                     double sellPrice = rs.getDouble("product_sell_price");
                     String description = rs.getString("product_description");
                     switch (type) {
                         case "Clothes":
                             products.add(new Clothes(brand, type, id, name, purchasePrice, sellPrice, description));
                             break;
                         case "Accessories":
                             products.add(new Accessories(brand, type, id, name, purchasePrice, sellPrice, description));
                             break;
                         case "Shoes":
                             products.add(new Shoes(brand, type, id, name, purchasePrice, sellPrice, description));
                             break;
                     }
                 }
                 this.close(dbCon, stmt, rs);
                 return products;
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         } else {
             System.out.println("Error: DB connection is null");

         }
         return null;
     }

     public void addProduct(Product product) {
         try {
             this.loadCreds();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         Connection dbCon = this.connector();
         if (dbCon != null) {
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "INSERT INTO products (product_type, product_brand, product_name, product_purchased_price, product_sell_price, product_description) VALUES ('" + product.getType() + "', '" + product.getBrand() + "', '" + product.getName() + "', '" + product.getPurchasePrice() + "', '" + product.getSellPrice() + "', '" + product.getDescription() + "')";
                 stmt.executeUpdate(query);
                 this.close(dbCon, stmt, null);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         } else {
             System.out.println("Error: DB connection is null");
         }
     }

     public List<Item> loadVersionsItem(int id) {
         try {
             this.loadCreds();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         List<Item> items = new ArrayList<Item>();

         Connection dbCon = this.connector();
         if (dbCon != null) {
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "SELECT * FROM product_versions WHERE product_id = " + id;
                 ResultSet rs = stmt.executeQuery(query);
                 while (rs.next()) {
                     int versions_id = rs.getInt("versions_id");
                     String color = rs.getString("color");
                     int size = rs.getInt("size");
                     int quantity = rs.getInt("quantity");
                     items.add(new Item(quantity, size, color, versions_id, id));
                 }
                 this.close(dbCon, stmt, rs);
                 return items;
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         } else System.out.println("Error: DB connection is null");
         return null;
     }

     public void buyItem(Item item, int quantity, double price) {
         try {
             this.loadCreds();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         Connection dbCon = this.connector();
         if (dbCon != null) {
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "UPDATE product_versions SET quantity = quantity + " + quantity + " WHERE versions_id = " + item.getId();
                 stmt.executeUpdate(query);
                 this.close(dbCon, stmt, null);
                 this.data.setCapital(this.data.getCapital() - quantity * price);
                 this.data.setTotalExpenses(this.data.getTotalExpenses() + quantity * price);
                 writePropertiesJson();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         } else {
             System.out.println("Error: DB connection is null");
         }
     }

     public void sellItem(Item item, int quantity, double sellPrice) {
         try {
             this.loadCreds();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         Connection dbCon = this.connector();
         if (dbCon != null) {
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "UPDATE product_versions SET quantity = quantity - " + quantity + " WHERE versions_id = " + item.getId();
                 stmt.executeUpdate(query);
                 this.close(dbCon, stmt, null);
                 this.data.setCapital(this.data.getCapital() + quantity * (sellPrice*(1- (double) item.getQuantity() /100)));
                 this.data.setTotalIncomes(this.data.getTotalIncomes() + quantity * (sellPrice*(1- (double) item.getQuantity() /100)));
                 writePropertiesJson();


             } catch (SQLException e) {
                 e.printStackTrace();
             }
         } else {
             System.out.println("Error: DB connection is null");
         }
     }

     public void writePropertiesJson() {
         ObjectMapper mapper = new ObjectMapper();
         File file = new File("src/main/resources/properties.json");
         try {
             mapper.writeValue(file, data);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void addCustomItem(String color, int size, int productId) {
         try {
             this.loadCreds();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         Item item = new Item(0, size, color, 0, productId);
         Connection dbCon = this.connector();
         if (dbCon != null) {
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "INSERT INTO product_versions (product_id, color, size) VALUES (" + item.getProductId() + ", '" + item.getColor() + "', " + item.getSize()  + ")";
                 stmt.executeUpdate(query);
                 this.close(dbCon, stmt, null);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         } else {
             System.out.println("Error: DB connection is null");
         }
     }

     public void pushVersion(Item item) {
         try {
             this.loadCreds();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         Connection dbCon = this.connector();
         if (dbCon != null) {
             try {
                 Statement stmt = dbCon.createStatement();
                 String query = "UPDATE product_versions SET discount = "+item.getDiscount()+" SET quantity" + item.getQuantity()  + " WHERE versions_id = " + item.getId();

                 stmt.executeUpdate(query);
                 this.close(dbCon, stmt, null);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         } else {
             System.out.println("Error: DB connection is null");
         }
     }

     public void deleteProduct(Product product) {
            try {
                this.loadCreds();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Connection dbCon = this.connector();
            if (dbCon != null) {
                try {
                    Statement stmt = dbCon.createStatement();
                    String query = "DELETE FROM product_versions WHERE product_id = " + product.getId();
                    stmt.executeUpdate(query);

                    stmt = dbCon.createStatement();
                    query = "DELETE FROM products WHERE product_id = " + product.getId();
                    stmt.executeUpdate(query);


                    this.close(dbCon, stmt, null);


                }

                catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Error: DB connection is null");
            }
     }

     public void deleteVersion(Item item) {
            try {
                this.loadCreds();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Connection dbCon = this.connector();
            if (dbCon != null) {
                try {
                    Statement stmt = dbCon.createStatement();
                    String query = "DELETE FROM product_versions WHERE versions_id = " + item.getId();
                    stmt.executeUpdate(query);
                    this.close(dbCon, stmt, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error: DB connection is null");
            }
     }
 }

