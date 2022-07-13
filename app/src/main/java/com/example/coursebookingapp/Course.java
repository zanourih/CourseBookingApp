package com.example.coursebookingapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {

    String coursecode, coursename;
    String description = "No description yet";
    String instructor = "No instructor"; // for later: will be Instructor.getName
    ArrayList<String> schedule = new ArrayList<String>(); // format = {"Monday 10:00 - 11:20" , "Friday 2:00 - 3:20"}
    boolean teacherAssigned = false;
    int capacity=0;

    public Course(){}


    public Course(String coursecode, String coursename){
        this.coursecode = coursecode;
        this.coursename = coursename;
    }

    public Course(String coursecode, String coursename, String description, String instructor,int capacity,
                  boolean teacherAssigned, ArrayList<String> schedule){
        this.coursecode = coursecode;
        this.coursename = coursename;
        this.description = description;
        this.capacity = capacity;
        this.teacherAssigned = teacherAssigned;
        this.schedule = schedule;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructor() { return instructor; }

    public ArrayList<String> getSchedule() {
        return schedule;
    }

    public boolean isTeacherAssigned() {
        return teacherAssigned;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructor(String instructor) { this.instructor = instructor; }

    public void setSchedule(ArrayList<String> schedule) {
        this.schedule = schedule;
    }

    public void setTeacherAssigned(boolean teacherAssigned) {
        this.teacherAssigned = teacherAssigned;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
