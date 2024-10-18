package org.example.womenshopfx.shop;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;


/**
 * Sample Skeleton for 'womenShop.fxml' Controller Class
 */

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WomenShopController implements Initializable {
    // Attributes
    DBManager dbManager;


    //FXML Fields
    @FXML // fx:id="capitalLabel"
    private Text capitalLabel; // Value injected by FXMLLoader

    @FXML // fx:id="expensesLabel"
    private Text expensesLabel; // Value injected by FXMLLoader

    @FXML // fx:id="incomesLabel"
    private Text incomesLabel; // Value injected by FXMLLoader

    @FXML // fx:id="selectorBrand"
    private ComboBox<String> selectorBrand; // Value injected by FXMLLoader

    @FXML // fx:id="selectorType"
    private ComboBox<String> selectorType; // Value injected by FXMLLoader

    @FXML // fx:id="sortBy"
    private ComboBox<String> sortBy; // Value injected by FXMLLoader

    @FXML // fx:id="viewItems"
    private ListView<Product> viewItems; // Value injected by FXMLLoader

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
            List<String> brands = dbManager.data.getBrands();
            ObservableList<String> brandList = FXCollections.observableList( brands);
            selectorBrand.setItems(brandList);

            List<String> types = dbManager.data.getTypes();
            ObservableList<String> typeList = FXCollections.observableList( types);
            selectorType.setItems(typeList);

            List<String> sortOptions = List.of("Name", "Sell Price","Buy Price");
            ObservableList<String> sortList = FXCollections.observableArrayList( sortOptions);
            sortBy.setItems(sortList);

        } catch (Exception e) {
            System.out.println("Error affecting to ComboBoxes");
            throw new RuntimeException(e);
        }
        capitalLabel.setText("Capital: "+dbManager.data.getCapital()+"$");





    }
}

