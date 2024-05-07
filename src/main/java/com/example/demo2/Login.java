package com.example.demo2;

public class Login {
    private String username;
    private String password;
    private int Agent_ID;
    Login(String username,String password,int Agent_ID) {
        this.username = username;
        this.password = password;
        this.Agent_ID=Agent_ID;
    }
    Login(String username,String password) {
        this.username = username;
        this.password = password;

    }
    void setAgent_ID(int Agent_ID){
        this.Agent_ID=Agent_ID;
    }
    int getAgent_ID(){
        return this.Agent_ID;
    }

}
