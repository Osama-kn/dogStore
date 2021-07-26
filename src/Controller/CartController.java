package Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import main.ProductListener;
import model.Dog;
import model.Fruit;
import model.Panel;
import model.Product;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    PreparedStatement pst2;
    ResultSet rs2;

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
    private Button addToCart;

    @FXML
    private Button backToProductsBtn;

    @FXML
    private Button orderBtn;

    @FXML
    private Button deleteFromCartBtn;

    @FXML
    private Label totalLabel;

    @FXML
    private Label quantityPriceLabel;

    private List<Dog> dogs = new ArrayList<>( );
    private List<Product> products = new ArrayList<>( );


    private Image image;
    private MyListener myListener;
    private ProductListener productListener;

    private int totalPrice = 0;


    private List<Dog> getAllDogs() throws SQLException, ClassNotFoundException{
        List<Dog> Alldogs = new ArrayList<>( );
        try{
            Class.forName( "com.mysql.jdbc.Driver" );
            con = DriverManager.getConnection( "jdbc:mysql://localhost/ch_db","root","" );
            pst = con.prepareStatement( "select * from dogs d, panel p where d.id=p.dogId and p.clientId=?" );
            pst.setString( 1,String.valueOf(LoginController.getClientID()) );
            rs = pst.executeQuery();

            while (rs.next()){
                Dog dog = new Dog();

                dog.setId( rs.getInt( "id" ));
                dog.setBirthdate( rs.getString( "birthdate" ));
                dog.setName(rs.getString( "name" ));
                dog.setQuantity(rs.getInt( "quantity" ));
                dog.setPrice(rs.getDouble( "price" ));
                dog.setImgSrc(rs.getString( "imgsrc" ));
                dog.setChosenQuantity( rs.getInt( "p.quantity" ) );
                totalPrice += rs.getInt( "d.price" ) * rs.getInt( "p.quantity" ) ;
                Alldogs.add(dog);

            }
            totalLabel.setText( Main.CURRENCY + totalPrice  );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Alldogs;
    }

    private List<Product> getAllProducts() throws SQLException, ClassNotFoundException{
        List<Product> allProducts = new ArrayList<>( );
        try{
            Class.forName( "com.mysql.jdbc.Driver" );
            con = DriverManager.getConnection( "jdbc:mysql://localhost/ch_db","root","" );
            pst = con.prepareStatement( "select * from products d, panel p where d.id=p.dogId and p.clientId=?" );
            pst.setString( 1,String.valueOf(LoginController.getClientID()) );
            rs = pst.executeQuery();

            while (rs.next()){
                Product product = new Product();

                product.setId( rs.getInt( "id" ));
                product.setTitle(rs.getString( "title" ));
                product.setQuantity(rs.getInt( "quantity" ));
                product.setPrice(rs.getDouble( "price" ));
                product.setImgSrc(rs.getString( "imgsrc" ));
                product.setChosenQuantity( rs.getInt( "p.quantity" ) );
                totalPrice += rs.getInt( "d.price" ) * rs.getInt( "p.quantity" ) ;
                allProducts.add(product);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allProducts;
    }


    private void setChosenDog(Dog dog) {
        quantityPriceLabel.setText( String.valueOf(Main.CURRENCY + dog.getPrice() * dog.getChosenQuantity() ) );
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
            }
            dogquantity.getItems().addAll( qt );
            dogquantity.setValue( dog.getChosenQuantity() );

        }else{
            dogquantity.getItems().clear();
        }

    }

    private void setChosenProduct(Product product) {
        quantityPriceLabel.setText( String.valueOf(Main.CURRENCY + product.getPrice() * product.getChosenQuantity() ) );
        clientIdLabel.setText( LoginController.getClientID()+"" );
        fruitNameLable.setText(product.getTitle());
        fruitPriceLabel.setText(Main.CURRENCY + product.getPrice());
        image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #291D36;\n" +"-fx-background-radius: 30;");
        dogIdLable.setText(product.getId().toString());



        if(product.getQuantity()!= null) {
            Integer[] qt = new Integer[product.getQuantity()];
            for (int i = 1; i <= product.getQuantity(); i++) {
                qt[i-1]=i;
//                quantity_arr.add( i );
            }
            dogquantity.getItems().addAll( qt );
            dogquantity.setValue( product.getChosenQuantity() );
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
            products.addAll(getAllProducts());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (dogs.size() > 0) {
            setChosenDog(dogs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Dog dog) {
                    setChosenDog(dog);
                }
            };
        }
        if (products.size() > 0) {
            setChosenProduct(products.get(0));
            productListener = new ProductListener() {
                @Override
                public void onClickListener(Product product) {
                    setChosenProduct(product);
                }
            };
        }

        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < dogs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/cartItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CartItemController ItemController = fxmlLoader.getController();
                ItemController.setData(dogs.get(i),myListener);

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


            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/productItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductItemController productItemController = fxmlLoader.getController();
                productItemController.cartSetData( products.get(i), productListener );

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

//                JOptionPane.showMessageDialog( null,"La quantité a été modifiée" );
                updateSceene();
//              updateSceene();

                return;


            }else {
                JOptionPane.showMessageDialog( null,"Le changement a échoué" );
            }
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public void deleteFromCart() throws SQLException {
        try {

            pst = con.prepareStatement( "delete from panel where clientId=? and dogId= ?" );
            pst.setString( 1 , String.valueOf( LoginController.getClientID() ) );
            pst.setString( 2 , String.valueOf( dogIdLable.getText() ) );
            int rst = pst.executeUpdate();
            if(rst>0){
//                JOptionPane.showMessageDialog( null,"Le chien a été supprimé" );
                updateSceene();
            }
        }catch (SQLException | IOException e){
            e.printStackTrace();

        }

    }

    public void backtoProducts()throws IOException {

            Parent root = FXMLLoader.load( getClass().getResource( "../views/market.fxml" ) );
            Stage window = (Stage) addToCart.getScene().getWindow();
            window.setScene( new Scene( root ) );
            window.centerOnScreen();


    }

    public void addToOrder() throws SQLException, IOException {


        pst = con.prepareStatement( "select * from panel p, dogs d where p.dogId=d.id and p.clientId=? " );
        pst.setString( 1 , String.valueOf( LoginController.getClientID() ) );
        rs = pst.executeQuery();

        while (rs.next()){
            pst2 = con.prepareStatement( "Insert into orders (clientId, dogId, quantity, price) Values (?, ?, ?,?)");
            pst2.setString( 1 , String.valueOf( LoginController.getClientID() ) );
            pst2.setString( 2 , String.valueOf( rs.getInt( "p.dogId" ) ) );
            pst2.setString( 3 , String.valueOf( rs.getInt( "p.quantity" )  ) );
            int totalprice = (rs.getInt( "d.price" ) * rs.getInt( "p.quantity" ) )  ;
            pst2.setString( 4 , String.valueOf( totalprice ));
            int rs2 = pst2.executeUpdate();

        }

        pst = con.prepareStatement( "select * from panel p, products d where p.dogId=d.id and p.clientId=? " );
        pst.setString( 1 , String.valueOf( LoginController.getClientID() ) );
        rs = pst.executeQuery();

        while (rs.next()){
            pst2 = con.prepareStatement( "Insert into orders (clientId, dogId, quantity, price) Values (?, ?, ?,?)");
            pst2.setString( 1 , String.valueOf( LoginController.getClientID() ) );
            pst2.setString( 2 , String.valueOf( rs.getInt( "p.dogId" ) ) );
            pst2.setString( 3 , String.valueOf( rs.getInt( "p.quantity" )  ) );
            int totalprice = (rs.getInt( "d.price" ) * rs.getInt( "p.quantity" ) )  ;
            pst2.setString( 4 , String.valueOf( totalprice ));
            int rs2 = pst2.executeUpdate();

        }

        pst = con.prepareStatement( "Delete from panel where clientId=? " );
        pst.setString( 1 , String.valueOf( LoginController.getClientID() ) );
        int rs = pst.executeUpdate();

        if(rs>0){
            JOptionPane.showMessageDialog( null,"Votre commande a été envoyée" );
            updateSceene();
        }


    }

    public void updateSceene() throws IOException {
        try {


        Parent root = FXMLLoader.load(getClass().getResource("../views/cart.fxml"));
        Stage window = (Stage) addToCart.getScene().getWindow();

        window.setScene(new Scene(root) );
        window.centerOnScreen();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
