package org.example.womenshopfx.shop;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;


/**
 * Sample Skeleton for 'womenShop.fxml' Controller Class
 */

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WomenShopController implements Initializable {
    // Attributes
    DBManager dbManager;

    @FXML
    private Button addCustomItem;

    @FXML
    private Button addItemToCatalog;

    @FXML
    private Button buyButton;

    @FXML
    private Button deleteVersionButton;
    @FXML
    private Text capitalLabel;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Text costTextField;

    @FXML
    private TextFlow descriptionField;

    @FXML
    private Text expensesTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private Text gainTextField;

    @FXML
    private Text incomesTextField;

    @FXML
    private ListView<Item> itemInventory;

    @FXML
    private Slider quantitySlider;

    @FXML
    private ComboBox<String> selectorBrand;

    @FXML
    private ComboBox<String> selectorType;

    @FXML
    private Button sellButton;

    @FXML
    private Text priceText;

    @FXML
    private TextField applyDiscount;

    @FXML
    private ComboBox<Integer> sizeSelector;

    @FXML
    private ComboBox<String> sortBy;

    @FXML
    private ListView<Product> viewItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
        try {
            dbManager = new DBManager();
            System.out.println("DBManager created");

        } catch (IOException e) {
            System.out.println("DBManager not created");
            throw new RuntimeException(e);

        }
        try{
            List<String> brands =dbManager.data.getBrands();

            ObservableList<String> brandList = FXCollections.observableList( brands);
            selectorBrand.setItems(brandList);
            // Event Listener to filter by brand
            selectorBrand.setOnAction( event -> {
                String brandSelected = selectorBrand.getValue();
                filterProduct("product_brand",brandSelected);
            });

            List<String> types = dbManager.data.getTypes();

            ObservableList<String> typeList = FXCollections.observableArrayList( types);
            selectorType.setItems(typeList);
            // Event Listener to filter by type
            selectorType.setOnAction( event -> {
                String typeSelected = selectorType.getValue();
                filterProduct("product_type",typeSelected);
            });

            List<String> sortOptions = List.of("Name", "Sell Price","Buy Price");
            ObservableList<String> sortList = FXCollections.observableArrayList( sortOptions);
            sortBy.setItems(sortList);
            // Event Listener to sort by
            sortBy.setOnAction( event -> {
                List<Product> listProducts = viewItems.getItems();
                String sortSelected = sortBy.getValue();
                switch (sortSelected){
                    case "Name":
                        listProducts.sort(Product::compareByName);
                        break;
                    case "Sell Price":
                        listProducts.sort(Product::compareBySellPrice);
                        break;
                    case "Buy Price":
                        listProducts.sort(Product::compareByPurchasePrice);
                        break;
                }
                ObservableList<Product> products = FXCollections.observableArrayList(listProducts);
                viewItems.setItems(products);
            });

        } catch (Exception e) {
            System.out.println("Error affecting to ComboBoxes");
            throw new RuntimeException(e);
        }
        capitalLabel.setText("Capital: "+String.format("%.2f",dbManager.data.getCapital())+"$");
        expensesTextField.setText("Expenses: "+String.format("%.2f",dbManager.data.getTotalExpenses())+"$");
        incomesTextField.setText("Incomes: "+String.format("%.2f",dbManager.data.getTotalIncomes())+"$");

        addItemToCatalog.setOnAction(event -> openPopUpAddItem());

        deleteButton.setOnAction(event -> {
            Product product = viewItems.getSelectionModel().getSelectedItem();
            if (product != null) {
                dbManager.deleteProduct(product);
                fetchProducts();
            }
        });

        viewItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //ToDO
                displayProductDetails(newValue);
            }
        });

        fetchProducts();




    }

    private void fetchProducts() {
        List<Product> listProducts = dbManager.loadProducts();
        if (listProducts != null) {
            ObservableList<Product> products = FXCollections.observableArrayList(listProducts);
            viewItems.setItems(products);
        }

    }

    private void fetchVersion(int id) {
        List<Item> listItems = dbManager.loadVersionsItem(id);
        if (listItems != null) {
            ObservableList<Item> products = FXCollections.observableArrayList(listItems);
            itemInventory.setItems(products);
        }

    }


    private void filterProduct(String selectorType, String filter){
        if (filter.isEmpty()){
            fetchProducts();
            return;
        }

        List<Product> filteredProducts = dbManager.loadFilteredProducts(selectorType,filter);

        ObservableList<Product> products;
        if (filteredProducts != null) {
            products = FXCollections.observableArrayList(filteredProducts);
        }
        else {
            products = FXCollections.observableArrayList();
        }
        viewItems.setItems(products);
    }

    private void openPopUpAddItem() {

        try {

            //Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/womenshopfx/popupAddItem.fxml"));
            Parent popupContent = loader.load();


            //Get controller
            WomenShopController popupController = loader.getController();

            //New stage for popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block events to other windows

            //Set scene
            Scene popupScene = new Scene(popupContent, 800, 550);
            popupStage.setScene(popupScene);
            popupStage.setTitle("Add Item");

            //Close button
            Button closeButton = (Button) popupContent.lookup("#closeButton");
            closeButton.setOnAction(event -> popupStage.close());

            //Brand box
            Node node = popupContent.lookup("#comboBoxBrand");
            if (node instanceof ComboBox) {
                ComboBox<String> brandBox = (ComboBox<String>) node;
                List<String> brands = dbManager.data.getBrands();
                ObservableList<String> brandList = FXCollections.observableList(brands);
                brandBox.setItems(brandList);
            }

            //Type box
            node = popupContent.lookup("#comboBoxType");
            if (node instanceof ComboBox typeBox) {

                List<String> types = dbManager.data.getTypes();
                ObservableList<String> typeList = FXCollections.observableList(types);
                typeBox.setItems(typeList);
            }

            //add BrandField
            node = popupContent.lookup("#addBrandField");
            if (node instanceof TextField addBrandField) {
                addBrandField.setOnAction(event -> {
                    String brand = addBrandField.getText();
                    if (dbManager.data.getBrands().size() > 1000) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Too many brands");
                        alert.setContentText("You can't add more than 1000 brands, please delete some before");
                        alert.showAndWait();
                        return;
                    }
                    if (brand.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Brand is empty");
                        alert.setContentText("Please enter a brand");
                        alert.showAndWait();
                        return; // Exit method
                    }
                    System.out.println(dbManager.data.getBrands());
                    System.out.println(brand);

                    if (dbManager.data.getBrands().contains(brand)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Brand already exists");
                        alert.setContentText("Please enter a new brand");
                        alert.showAndWait();
                        addBrandField.clear();

                        return; // Exit method
                    } else {
                        dbManager.data.addBrand(brand);
                        dbManager.data.sortBrands();
                        dbManager.writePropertiesJson();
                        List<String> brands = dbManager.data.getBrands();
                        ObservableList<String> brandList = FXCollections.observableList(brands);
                        selectorBrand.setItems(brandList);
                        addBrandField.clear();
                    }
                });
            }

            TextField nameField = (TextField) popupContent.lookup("#nameField");
            TextField purchasePriceField = (TextField) popupContent.lookup("#purchasePriceField");
            TextField sellPriceField = (TextField) popupContent.lookup("#sellPriceField");
            //Add button
            Button addButton = (Button) popupContent.lookup("#addButton");
            addButton.setOnAction(event -> {
                        //Get values from fields
                        String name = nameField.getText();
                        if (name.isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Name is empty");
                            alert.setContentText("Please enter a name");
                            alert.showAndWait();
                            return; // Exit method
                        }
                        if (name.length() > 255) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Name is too long");
                            alert.setContentText("Please enter a name with less than 255 characters");
                            alert.showAndWait();
                            nameField.clear();
                            return; // Exit method
                        }
                        String brand = ((ComboBox<String>) popupContent.lookup("#comboBoxBrand")).getValue().toString();
                        if (brand.isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Brand is empty");
                            alert.setContentText("Please select a brand");
                            alert.showAndWait();
                            return; // Exit method
                        }
                        String type = ((ComboBox<String>) popupContent.lookup("#comboBoxType")).getValue().toString();
                        if (type.isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Type is empty");
                            alert.setContentText("Please select a type");
                            alert.showAndWait();
                            return; // Exit method
                        }
                        String description = ((TextArea) popupContent.lookup("#descriptionField")).getText();
                        try {
                            double purchasePrice = Double.parseDouble(((TextField) popupContent.lookup("#purchasePriceField")).getText());
                            double sellPrice = Double.parseDouble(((TextField) popupContent.lookup("#sellPriceField")).getText());
                            if (purchasePrice < 0 || sellPrice < 0) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Price is negative");
                                alert.setContentText("Please enter a positive price");
                                purchasePriceField.clear();
                                sellPriceField.clear();
                                alert.showAndWait();

                                return; // Exit method
                            }
                            else {
                                Product product = new Product(brand, type, 0, name,purchasePrice , sellPrice, description);
                                dbManager.addProduct(product);
                                fetchProducts();
                                popupStage.close();

                            }
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Price is not a number");
                            alert.setContentText("Please enter a number");
                            alert.showAndWait();
                            return; // Exit method
                        }

                        //Add product to DB



            }
            );
            //Show popup
            popupStage.show();


        }catch (IOException e){
            System.out.println("Error loading popup");
            e.printStackTrace();

        }





        }

    private void displayProductDetails(Product product) {
        // Clear previous description
        descriptionField.getChildren().clear();
        // Add new description
        Text text = new Text(product.getDescription());
        descriptionField.getChildren().add(text);

        fetchVersion(product.getId());


        // Set quantity
        quantitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (itemInventory.getSelectionModel().getSelectedItem() != null) {
                int quantity = newValue.intValue();
                Item item = itemInventory.getSelectionModel().getSelectedItem();
                
                quantitySlider.maxProperty().setValue(10);
                quantitySlider.minProperty().setValue(0);
                quantitySlider.setValue(quantity);
                gainTextField.setText(String.format("%.2f",product.getSellPrice() * quantity)+ "$");
                costTextField.setText(String.format("%.2f",product.getPurchasePrice() * quantity) + "$");
            }

        });
        //Delete Version Button
        deleteVersionButton.setOnAction(event -> {
            Item item = itemInventory.getSelectionModel().getSelectedItem();
            if (item != null) {
                if (item.getQuantity() == 0) {
                    dbManager.deleteVersion(item);
                    fetchVersion(product.getId());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Item has stock");
                    alert.setContentText("Please sell all stock before deleting");
                    alert.showAndWait();
                }
            }
        });


        // Buy Button
        buyButton.setOnAction(event -> {
            if (itemInventory.getSelectionModel().getSelectedItem() != null) {
                Item item = itemInventory.getSelectionModel().getSelectedItem();
                int quantity = (int) quantitySlider.getValue();
                if (quantity > 0) {
                    if (dbManager.data.getCapital() < product.getPurchasePrice() * quantity) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Not enough capital");
                        alert.setContentText("Please select a lower quantity");
                        alert.showAndWait();
                        return; // Exit method
                    }
                    else {
                        dbManager.buyItem(item, quantity, product.getPurchasePrice());

                        capitalLabel.setText("Capital: "+String.format("%.2f",dbManager.data.getCapital())+"$");
                        expensesTextField.setText("Expenses: "+String.format("%.2f",dbManager.data.getTotalExpenses())+"$");

                        fetchVersion(product.getId());
                        quantitySlider.setValue(0);
                        quantitySlider.maxProperty().setValue(0);
                        costTextField.setText("0.0$");
                        gainTextField.setText("0.0$");


                    }
                }

            }
        });

        // Apply Discount
        applyDiscount.setOnAction(event -> {
            if (itemInventory.getSelectionModel().getSelectedItem() != null) {
                Item item = itemInventory.getSelectionModel().getSelectedItem();
                double discount = Double.parseDouble(applyDiscount.getText());
                if (discount < 0 || discount > 100) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Discount must be between 0 and 100");
                    alert.setContentText("Please enter a valid discount");
                    alert.showAndWait();
                    return; // Exit method
                }
                item.setDiscount((int) discount);
                priceText.setText(String.format("%.2f",item.getDiscountedPrice(product.getSellPrice())) + "$");
                dbManager.pushVersion(item);
            }
        });

        itemInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                priceText.setText(String.format("%.2f",newValue.getDiscountedPrice(product.getSellPrice())) + "$");
            }
            else {
                priceText.setText("select an item");
            }
        });


        // Sell Button
        sellButton.setOnAction(event -> {
            if (itemInventory.getSelectionModel().getSelectedItem() != null) {
                Item item = itemInventory.getSelectionModel().getSelectedItem();
                int quantity = (int) quantitySlider.getValue();
                if (quantity > item.getQuantity()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Not enough stock");
                    alert.setContentText("Please select a lower quantity");
                    alert.showAndWait();
                    return; // Exit method
                }
                if (quantity > 0) {
                    dbManager.sellItem(item, quantity, product.getSellPrice());
                    capitalLabel.setText("Capital: "+String.format("%.2f",dbManager.data.getCapital())+"$");
                    incomesTextField.setText("Incomes: "+String.format("%.2f",dbManager.data.getTotalIncomes())+"$");
                    fetchVersion(product.getId());
                    quantitySlider.setValue(0);
                    quantitySlider.maxProperty().setValue(0);
                    costTextField.setText("0.0$");
                    gainTextField.setText("0.0$");

                }
            }
        });

        sizeSelector.setOnAction(event -> {
            if (itemInventory.getSelectionModel().getSelectedItem() != null) {
                Item item = itemInventory.getSelectionModel().getSelectedItem();
                int size = sizeSelector.getValue();
                item.setSize(size);
            }
        });
        int[] rangeArray = IntStream.range(product.getMinSize(), product.getMaxSize()).toArray();
        ObservableList<Integer> range = FXCollections.observableArrayList(Arrays.stream(rangeArray).boxed().collect(Collectors.toList()));
        sizeSelector.setItems(range);

        addCustomItem.setOnAction(event -> {

            Item item = itemInventory.getSelectionModel().getSelectedItem();
            int size = sizeSelector.getValue();
            String color = colorPicker.getValue().toString();
            int quantity = (int) quantitySlider.getValue();
            if (!color.isEmpty() && (size != 0 || product.getMinSize() == product.getMaxSize())) {

                dbManager.addCustomItem(color, size,product.getId());
                fetchVersion(product.getId());
                quantitySlider.setValue(0);
                quantitySlider.maxProperty().setValue(0);
                costTextField.setText("0.0$");
                gainTextField.setText("0.0$");
                colorPicker.setValue(null);
                sizeSelector.setValue(null);

            }
        });


        /*
        // Set color
        colorPicker.setValue(product.getColor());
        // Set size
        sizeSelector.setValue(product.getSize());

        */
    }
}




