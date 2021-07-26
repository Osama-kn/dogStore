package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.MyListener;
import model.Dog;
import model.Fruit;
import model.Product;

import java.net.URISyntaxException;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;


    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(dog);
    }



    private Dog dog;
    private Product product;
    private MyListener myListener;

    public void setData(Dog dog, MyListener myListener) {
//        this.fruit = fruit;
//        this.myListener = myListener;
//        nameLabel.setText(fruit.getName());
//        priceLable.setText(Main.CURRENCY + fruit.getPrice());
//        Image image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
//        img.setImage(image);

        this.dog = dog;
        this.myListener = myListener;
        nameLabel.setText(dog.getName());
        priceLable.setText(Main.CURRENCY + dog.getPrice());


        Image image = new Image( getClass().getResourceAsStream(dog.getImgSrc()));


        img.setImage(image);


    }

    public void setData(Product product, MyListener myListener) {
//        this.fruit = fruit;
//        this.myListener = myListener;
//        nameLabel.setText(fruit.getName());
//        priceLable.setText(Main.CURRENCY + fruit.getPrice());
//        Image image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
//        img.setImage(image);

        this.product = product;
        this.myListener = myListener;
        nameLabel.setText(product.getTitle());
        priceLable.setText(Main.CURRENCY + product.getPrice());


        Image image = new Image( getClass().getResourceAsStream(product.getImgSrc()));


        img.setImage(image);


    }
}
