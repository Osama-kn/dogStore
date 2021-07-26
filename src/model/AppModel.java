package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AppModel {
    private final IntegerProperty clientId = new SimpleIntegerProperty();


    public  IntegerProperty idProperty(){
        return clientId;
    }

    public final Integer getId(){
        return idProperty().get();
    }

    public final void setClientId(Integer clientId){
        idProperty().set(clientId);
    }
}
