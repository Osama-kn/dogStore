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

public class ProductController implements Initializable {
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
    private Button showDogsBnt;

    private List<Product> products = new ArrayList<>( );

    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private ProductListener myListener;



    private List<Product> getAllProducts() throws SQLException, ClassNotFoundException{
        List<Product> allProducts = new ArrayList<>( );
        try{
            Class.forName( "com.mysql.jdbc.Driver" );
            con = DriverManager.getConnection( "jdbc:mysql://localhost/ch_db","root","" );
            pst = con.prepareStatement( "select * from products" );
            rs = pst.executeQuery();

            while (rs.next()){
                Product product = new Product();

                product.setId( rs.getInt( "id" ));
                product.setTitle(rs.getString( "title" ));
                product.setQuantity(rs.getInt( "quantity" ));
                product.setPrice(rs.getDouble( "price" ));
                product.setImgSrc(rs.getString( "imgsrc" ));
                allProducts.add(product);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allProducts;
    }


    private void setChosenProduct(Product product) {

        clientIdLabel.setText( LoginController.getClientID()+"" );
        fruitNameLable.setText(product.getTitle());
        fruitPriceLabel.setText(Main.CURRENCY + product.getPrice());
        image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #291D36;\n" +"-fx-background-radius: 30;");
        dogIdLable.setText(product.getId().toString());
        ObservableList<Integer> quantity_arr= null;


        if(product.getQuantity()!= null) {
            Integer[] qt = new Integer[product.getQuantity()];
            for (int i = 1; i <= product.getQuantity(); i++) {
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
            products.addAll(getAllProducts());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if ( products.size() > 0) {
            setChosenProduct( products.get(0));
            myListener = new ProductListener() {
                @Override
                public void onClickListener(Product product) {
                    setChosenProduct(product);
                }
            };

        }

        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/productItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductItemController productItemController = fxmlLoader.getController();
                productItemController.setData( products.get(i), myListener );

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


                       JOptionPane.showMessageDialog( null,"Le produit a été ajouté au panier" );

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
        Stage window = (Stage) myCartBtn.getScene().getWindow();


        window.setScene(new Scene(root) );
        window.centerOnScreen();


    }


    public void showDogs() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../views/market.fxml"));
        Stage window = (Stage) showDogsBnt.getScene().getWindow();


        window.setScene(new Scene(root) );
        window.centerOnScreen();
    }

}
