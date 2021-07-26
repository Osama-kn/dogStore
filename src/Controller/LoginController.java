package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static int clientID;


  public static int getClientID(){ return clientID;}
  public void setClientID( int clientID){ this.clientID = clientID;}

    @FXML
    private TextField txtuname;

    @FXML
    private Button btnok;

    @FXML
    private PasswordField txtpass;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;




    @Override
    public void initialize(URL url , ResourceBundle resourceBundle) {

    }

    public void login(javafx.event.ActionEvent actionEvent) throws IOException, ClassNotFoundException {
//        JOptionPane.showMessageDialog( null,"Hi" );

        String uname = txtuname.getText();
        String pass = txtpass.getText();

        if(uname.equals( "" ) && pass.equals( "" )){
            JOptionPane.showMessageDialog( null,"Les champs ne devraient pas être vides" );
        }else{
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
                con = DriverManager.getConnection( "jdbc:mysql://localhost/ch_db","root","" );

                pst = con.prepareStatement( "select * from client where username=? and password=?" );
                pst.setString( 1,uname );
                pst.setString( 2,pass );
                rs = pst.executeQuery();

                if(rs.next()){

                    setClientID( rs.getInt( "id" ) );
//                   JOptionPane.showMessageDialog( null,"Connected" );
                    Parent root = FXMLLoader.load(getClass().getResource("../views/market.fxml"));
                    Stage window = (Stage) btnok.getScene().getWindow();
                    window.setScene(new Scene(root) );
                    window.centerOnScreen();
                    return;


                }else{
                    JOptionPane.showMessageDialog( null,"Nom d'utilisateur ou mot de passe incorrect" );
                    txtuname.setText( "" );
                    txtpass.setText( "" );
                    txtuname.requestFocus();
                }
            }catch (SQLException e) {
                e.printStackTrace();

            }

        }


    }
}
