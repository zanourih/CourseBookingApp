package com.example.coursebookingapp;

import java.util.ArrayList;

public class Admin extends User {

    public String name, email, password, role;

    public Admin(){};

    public Admin(String name, String email, String password, String role){
        super(name, email, password, role);
    }

}
