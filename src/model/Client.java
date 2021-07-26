package model;

public class Client {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String address;


    public void setId(int id){this.id = id; }
    public int getId(){return id;}

    public void setName(String name){ this.name = name;}
    public String getName(){return name;}

    public void setUsername(String username){ this.username = username;}
    public String getUsername(){ return username;}

    public void setPassword(String password){ this.password = password;}
    public String getPassword(){ return password;}

    public void setAddress(String address){ this.address = address;}
    public String getAddress(){ return address;}
}
