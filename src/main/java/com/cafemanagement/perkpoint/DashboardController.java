package com.cafemanagement.perkpoint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class DashboardController implements Initializable {

    @FXML
    private Button customers_button;

    @FXML
    private Button dashboard_button;

    @FXML
    private AnchorPane dashboard_page;

    @FXML
    private Button inventory_add_button;

    @FXML
    private Button inventory_button;

    @FXML
    private Button inventory_clear_button;

    @FXML
    private Button inventory_delete_button;

    @FXML
    private Button inventory_import_button;

    @FXML
    private ImageView inventory_import_image;

    @FXML
    private AnchorPane inventory_page;

    @FXML
    private TableColumn<ProductData, String> inventory_product_date;

    @FXML
    private TableColumn<ProductData, String> inventory_product_id;

    @FXML
    private TableColumn<ProductData, String> inventory_product_name;

    @FXML
    private TableColumn<ProductData, String> inventory_product_price;

    @FXML
    private TableColumn<ProductData, String> inventory_product_status;

    @FXML
    private TableColumn<ProductData, String> inventory_product_stock;

    @FXML
    private TableColumn<ProductData, String> inventory_product_type;

    @FXML
    private TableView<ProductData> inventory_table;

    @FXML
    private Button inventory_update_button;

    @FXML
    private AnchorPane main_background;

    @FXML
    private Button menu_button;

    @FXML
    private Button signout_button;

    @FXML
    private Label username_display;

    @FXML
    private TextField inventory_new_product_id;

    @FXML
    private TextField inventory_new_product_name;

    @FXML
    private TextField inventory_new_product_price;

    @FXML
    private ComboBox<?> inventory_new_product_status;

    @FXML
    private TextField inventory_new_product_stock;

    @FXML
    private ComboBox<?> inventory_new_product_type;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    public void displayUsername(){

        String user = Data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username_display.setText(user);
    }

    private String[] typeList = {"Meals", "Drinks"};
    public void inventoryTypeList(){
        List<String> typeL = new ArrayList<>();

        for(String data : typeList){
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_new_product_type.setItems(listData);

    }

    private String[] statusList = {"Available", "Unavailable"};
    public void inventoryStatusList(){
        List<String> statusL = new ArrayList<>();

        for(String data : statusList){
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_new_product_status.setItems(listData);

    }

    public void logout(){

        try{
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to Sign Out?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){

                signout_button.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Login Page");

                stage.setScene(scene);
                stage.show();

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public ObservableList<ProductData> inventoryDataList(){

        ObservableList<ProductData> productListData = FXCollections.observableArrayList();

        String sql = "select * from product";

        connect = Database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ProductData prodData;

            while (result.next()){
                prodData = new ProductData(result.getInt("id"),
                        result.getString("product_id"), result.getString("product_name"),
                        result.getString("type"), result.getInt("stock"),
                        result.getInt("price"), result.getString("status"),
                        result.getString("image"), result.getDate("date"));

                productListData.add(prodData);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return productListData;
    }

    private  ObservableList<ProductData> inventoryListData;
    public void inventoryShowData(){

        inventoryListData = inventoryDataList();

        inventory_product_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_product_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_product_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_product_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_product_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_table.setItems(inventoryListData);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayUsername();
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();

    }
}
