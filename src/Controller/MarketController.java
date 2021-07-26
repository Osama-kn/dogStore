package Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.MyListener;
import model.Dog;
import model.Fruit;
import model.Panel;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<Integer> dogquantity;

    @FXML
    private Label dogIdLable;

    @FXML
    private Label clientIdLabel;

    @FXML
    private Button myCartBtn;

    @FXML
    private Button showProductsbtn;


    private List<Dog> dogs = new ArrayList<>( );

    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;


    private List<Dog> getAllDogs() throws SQLException, ClassNotFoundException{
        List<Dog> Alldogs = new ArrayList<>( );
        try{
            Class.forName( "com.mysql.jdbc.Driver" );
            con = DriverManager.getConnection( "jdbc:mysql://localhost/ch_db","root","" );

            pst = con.prepareStatement( "select * from dogs" );
            rs = pst.executeQuery();

            while (rs.next()){
                Dog dog = new Dog();

                dog.setId( rs.getInt( "id" ));
                dog.setBirthdate( rs.getString( "birthdate" ));
                dog.setName(rs.getString( "name" ));
                dog.setQuantity(rs.getInt( "quantity" ));
                dog.setPrice(rs.getDouble( "price" ));
                dog.setImgSrc(rs.getString( "imgsrc" ));
                Alldogs.add(dog);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Alldogs;
    }


    private void setChosenFruit(Dog dog) {

        clientIdLabel.setText( LoginController.getClientID()+"" );
        fruitNameLable.setText(dog.getName());
        fruitPriceLabel.setText(Main.CURRENCY + dog.getPrice());
        image = new Image(getClass().getResourceAsStream(dog.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #291D36;\n" +"-fx-background-radius: 30;");
        dogIdLable.setText(dog.getId().toString());



        if(dog.getQuantity()!= null) {
            Integer[] qt = new Integer[dog.getQuantity()];
            for (int i = 1; i <= dog.getQuantity(); i++) {
                qt[i-1]=i;
//                quantity_arr.add( i );
            }
            dogquantity.getItems().addAll( qt );
            dogquantity.setValue( 1 );
//        quantity.setItems( quantity_arr );
//            dogquantity.getItems().setAll( quantity_arr );
        }else{
            dogquantity.getItems().clear();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
//        fruits.addAll(getData());
        try {
            dogs.addAll(getAllDogs());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (dogs.size() > 0) {
            setChosenFruit(dogs.get(0));
            myListener = new MyListener() {

                @Override
                public void onClickListener(Dog dog) {
                    setChosenFruit(dog);
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
//            for (int i = 0; i < fruits.size(); i++) {
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
//                AnchorPane anchorPane = fxmlLoader.load();
//
//                ItemController itemController = fxmlLoader.getController();
//                itemController.setData(fruits.get(i),myListener);
//
//                if (column == 3) {
//                    column = 0;
//                    row++;
//                }
//
//                grid.add(anchorPane, column++, row); //(child,column,row)
//                //set grid width
//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//                //set grid height
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMaxHeight(Region.USE_PREF_SIZE);
//
//                GridPane.setMargin(anchorPane, new Insets(10));
//            }

            for (int i = 0; i < dogs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(dogs.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addToPanel() throws ClassNotFoundException, SQLException {
//        JOptionPane.showMessageDialog( null,"clicked to panel" );
//        Cart cart = new Cart();

//        cart.setClientid( );
//        String name = fruitNameLable.getText();
//        cart.addToCart(  );

        Panel panel = new Panel();

        panel.setClientId( LoginController.getClientID() );
        panel.setDogId(Integer.parseInt(dogIdLable.getText()));
        panel.setQuantity( dogquantity.getValue() );

        try {


            Class.forName( "com.mysql.jdbc.Driver" );
            con = DriverManager.getConnection( "jdbc:mysql://localhost/ch_db","root","" );

            pst = con.prepareStatement( "Select * from panel where clientID = ? and dogId = ?" );
            pst.setString( 1,  String.valueOf(LoginController.getClientID()));
            pst.setString( 2,String.valueOf(panel.getDogId()) );
            rs = pst.executeQuery();
            if(rs.next()) {
                pst = con.prepareStatement( "update panel set quantity = ? where clientId = ? and dogId= ?" );

                pst.setString( 1 , String.valueOf( panel.getQuantity() ) );
                pst.setString( 2 , String.valueOf( LoginController.getClientID() ) );
                pst.setString( 3 , String.valueOf( panel.getDogId() ) );


            }else {

                pst = con.prepareStatement( "insert into panel (clientId, dogId, quantity) Values (?, ?, ?)" );
                pst.setString( 1 , String.valueOf( LoginController.getClientID() ) );
                pst.setString( 2 , String.valueOf( panel.getDogId() ) );
                pst.setString( 3 , String.valueOf( panel.getQuantity() ) );
            }
                int rst = pst.executeUpdate();


            if(rst > 0){


                       JOptionPane.showMessageDialog( null,"Le chien a été ajouté au panier" );

                return;


            }else {
                JOptionPane.showMessageDialog( null,"L'ajout au panier a échoué" );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void showMyCart() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../views/cart.fxml"));
        Stage window = (Stage) showProductsbtn.getScene().getWindow();


        window.setScene(new Scene(root) );
        window.centerOnScreen();


    }

    public void showProducts() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/products.fxml"));
        Stage window = (Stage) myCartBtn.getScene().getWindow();


        window.setScene(new Scene(root) );
        window.centerOnScreen();
    }


}
