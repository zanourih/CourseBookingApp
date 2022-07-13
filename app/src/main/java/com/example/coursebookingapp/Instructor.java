package com.example.coursebookingapp;

import java.util.ArrayList;

public class Instructor extends User {
    ArrayList<Course> teachingCourses;

    public Instructor(){}

    public Instructor(String name, String email, String password, String role, ArrayList<Course> teachingCourses){
        super(name, email, password, role);
        this.teachingCourses = teachingCourses;
    }

/*
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

 */

    public ArrayList<Course> getTeachingCourses() {
        return teachingCourses;
    }

    public void setTeachingCourses(ArrayList<Course> teachingCourses) {
        this.teachingCourses = teachingCourses;
    }


}
