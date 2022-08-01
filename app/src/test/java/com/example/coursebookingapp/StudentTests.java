package com.example.coursebookingapp;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class StudentTests {


    @Test
    public void testGetSchedule(){
        ArrayList<Lecture> schedule = new ArrayList<Lecture>();
        schedule.add(new Lecture("Monday",930,1045));
        Course math = new Course("MAT101","Maths","Intro to mathematics","Professor J.",45,true,schedule);
        assertEquals("Monday",math.getSchedule().get(0));
    }

    @Test
    public void TestLectureFormat(){
        Lecture l = new Lecture();
        l.setDay("Friday");
        l.setStartTime(830);
        l.setEndTime(1000);
        String lecture = l.toString();
        assertEquals("Monday 8:30 - 10:00", lecture);
    }
    @Test
    public void testTimeConflict1(){
        ArrayList<Lecture> schedule1 = new ArrayList<Lecture>();
        schedule1.add(new Lecture("Monday",930,1045));
        Course math = new Course("MAT101","Maths","Intro to mathematics","Professor J.",45,true,schedule1);

        ArrayList<Lecture> schedule2 = new ArrayList<Lecture>();
        schedule2.add(new Lecture("Monday",1000,1130));
        Course seg = new Course("SEG101","Software engineering","Intro to software engineering","Professor O.",35,true,schedule2);

        Boolean conflict = math.checkTimeConflict(seg);
        assertEquals(true, conflict);
    }
    @Test
    public void testTimeConflict2(){
        ArrayList<Lecture> schedule1 = new ArrayList<Lecture>();
        schedule1.add(new Lecture("Monday",930,1045));
        Course math = new Course("MAT101","Maths","Intro to mathematics","Professor J.",45,true,schedule1);

        ArrayList<Lecture> schedule2 = new ArrayList<Lecture>();
        schedule2.add(new Lecture("Friday",1000,1130));
        Course seg = new Course("SEG101","Software engineering","Intro to software engineering","Professor O.",35,true,schedule2);

        Boolean conflict = math.checkTimeConflict(seg);
        assertEquals(false, conflict);
    }
    @Test
    public void testTimeConflict3(){
        ArrayList<Lecture> schedule1 = new ArrayList<Lecture>();
        schedule1.add(new Lecture("Monday",930,1045));
        Course math = new Course("MAT101","Maths","Intro to mathematics","Professor J.",45,true,schedule1);

        ArrayList<Lecture> schedule2 = new ArrayList<Lecture>();
        schedule2.add(new Lecture("Wednesday",830,1130));
        Course seg = new Course("SEG101","Software engineering","Intro to software engineering","Professor O.",35,true,schedule2);

        Boolean conflict = math.checkTimeConflict(seg);
        assertEquals(false, conflict);
    }
}