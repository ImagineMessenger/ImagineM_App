package com.imaginemessenger;

/**
 * Created by Raffy on 04/11/2017.
 */

public class User {
    private int id_user;
    private String username;
    private String password;
    private String message;

    public User(){}

    public int getId_user(){
        return id_user;
    }

    public void setId_user(int id){
        this.id_user=id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String user){
        this.username=user;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String pass){
        this.password=pass;
    }

    public String getMessage(){
        return username;
    }

    public void setMessage(String mess){
        this.message=mess;
    }
}
