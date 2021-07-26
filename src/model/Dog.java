package model;

import java.text.DateFormat;

public class Dog {
    private Integer id;
    private String name;
    private String birthdate;
    private double price;
    private Integer quantity;
    private String imgSrc;
    private Integer chosenQuantity;

    public Integer getId(){
        return id;
    }
    public void setId(int id){ this.id = id;}

    public String getName(){
        return name;
    }
    public void setName(String name){ this.name = name;}

    public String getBirthdate(){return birthdate;}
    public void setBirthdate(String birthdate){this.birthdate = birthdate;}

    public Double getPrice(){
        return price;
    }
    public void setPrice(Double price){this.price = price;}



    public Integer getQuantity(){return quantity;    }
    public void setQuantity(int quantity){this.quantity = quantity;}



    public String getImgSrc(){return imgSrc;}
    public void setImgSrc(String imgSrc){this.imgSrc = imgSrc;}


    public void setChosenQuantity(int chosenQuantity){ this.chosenQuantity = chosenQuantity;}
    public int getChosenQuantity(){return chosenQuantity;}
}
