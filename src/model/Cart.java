package model;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private int id;
    private int clientid;
    private List<int[]> dogsList = new ArrayList<int[]>();
    private LocalDate createDate = LocalDate.now();




    public int getId(){return id;}
    public void setId(int id ){this.id = id;}

    public int getClientid(){return clientid;}
    public void setClientid(int clientid){ this.clientid = clientid;}


    public boolean addToCart(int dogid, int quantity){
       return dogsList.add( new int[]{dogid,quantity} );
    }

    public List<int[]> getDogs() {return dogsList; }

    public void clearCart(){ dogsList.clear();}


}
