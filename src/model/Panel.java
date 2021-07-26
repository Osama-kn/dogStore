package model;

public class Panel {
    private int id;
    private int clientId;
    private int dogId;
    private int quantity;
    private String date;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){return id;}

    public void setClientId(int clientId){ this.clientId = clientId;}
    public int getClientId(){return clientId;}

    public void setDogId(int dogId){ this.dogId = dogId;}
    public int getDogId(){return dogId;}

    public void setQuantity(int quantity){ this.quantity = quantity;}
    public int getQuantity(){return quantity;}

    public void setDate( String date ){ this.date = date; }
    public String  getDate(){return date;}
}
