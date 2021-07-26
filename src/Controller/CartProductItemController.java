package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.ProductListener;
import model.Product;

public class CartProductItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML Label quantityLabel;

    @FXML
    private void click(MouseEvent mouseEvent) {
        productListener.onClickListener(product);
    }

    private Product product;
    private ProductListener productListener;

    public void setData(Product product, ProductListener productListener) {

        this.product = product;
        this.productListener = productListener;
        nameLabel.setText(product.getTitle());
        priceLable.setText(Main.CURRENCY + product.getPrice());
        quantityLabel.setText( String.valueOf( product.getChosenQuantity() ));


        Image image = new Image( getClass().getResourceAsStream(product.getImgSrc()));


        img.setImage(image);


    }
}
