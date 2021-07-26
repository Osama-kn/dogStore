package model;

public class Product {
    private Integer id;
    private String title;
    private double price;
    private Integer quantity;
    private String imgSrc;
    private Integer chosenQuantity;

    public Integer getId(){
        return id;
    }
    public void setId(int id){ this.id = id;}

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){ this.title = title;}



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
