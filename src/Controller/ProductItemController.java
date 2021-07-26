package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.Main;
import main.MyListener;
import main.ProductListener;
import model.Dog;
import model.Product;

public class ProductItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML
    private HBox quantityHBox;

    @FXML
    private Label quantityLabel;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(product);
    }


    private Product product;
    private ProductListener myListener;


    public void setData(Product product, ProductListener myListener) {
//

        this.product = product;
        this.myListener = myListener;
        nameLabel.setText(product.getTitle());
        priceLable.setText(Main.CURRENCY + product.getPrice());


        Image image = new Image( getClass().getResourceAsStream(product.getImgSrc()));


        img.setImage(image);


    }
    public void cartSetData(Product product, ProductListener myListener) {
//
        quantityHBox.setVisible( true );
        this.product = product;
        this.myListener = myListener;
        nameLabel.setText(product.getTitle());
        priceLable.setText(Main.CURRENCY + product.getPrice());
        quantityLabel.setText( String.valueOf( product.getChosenQuantity() ));

        Image image = new Image( getClass().getResourceAsStream(product.getImgSrc()));


        img.setImage(image);


    }
}
