package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.MyListener;
import model.Dog;

public class CartItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML Label quantityLabel;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(dog);
    }

    private Dog dog;
    private MyListener myListener;

    public void setData(Dog dog, MyListener myListener) {

        this.dog = dog;
        this.myListener = myListener;
        nameLabel.setText(dog.getName());
        priceLable.setText(Main.CURRENCY + dog.getPrice());
        quantityLabel.setText( String.valueOf( dog.getChosenQuantity() ));


        Image image = new Image( getClass().getResourceAsStream(dog.getImgSrc()));


        img.setImage(image);


    }
}
