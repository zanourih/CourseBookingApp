package com.example.coursebookingapp;

import java.util.ArrayList;

public class Student extends User {

    ArrayList<Course> myCourses;

    public Student(String name, String email, String password, String role, ArrayList<Course> myCourses){
        super(name, email, password, role);
        this.myCourses = myCourses;
    }

}
