package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CoursesList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference dbRef;
    private MyAdapter adapter;

    private String coursecode, coursename;
    private ArrayList<Course> allCourses;

    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);

        back = (Button) findViewById(R.id.backBtn);


        back.setOnClickListener(v -> {
            startActivity(new Intent(this, InstructorApp.class));
        });

        recyclerView = findViewById(R.id.allcoursesRV);
        dbRef = FirebaseDatabase.getInstance().getReference("Courses");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        coursecode = (String) getIntent().getSerializableExtra("coursecode");
        coursename = (String) getIntent().getSerializableExtra("coursename");

        allCourses = new ArrayList<Course>();
        adapter = new MyAdapter(this, allCourses);
        recyclerView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dbSnapshot : snapshot.getChildren()){
                    Course course = dbSnapshot.getValue(Course.class);
                    if(coursecode!=null || coursename!=null){
                        if (coursecode!=null && coursename.equals("")){
                            searchByCode(coursecode,course);
                        }
                        if (coursecode.equals("") && coursename!=null){
                            searchByName(coursename,course);
                        }
                        if (coursecode!=null && coursename!=null){
                            search(coursecode,coursename,course);
                        }
                    }
                    else{
                        allCourses.add(course);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void searchByCode(String coursecode, Course course){
        if(coursecode.equals(course.coursecode)){
            allCourses.add(course);
        }
    }
    private void searchByName(String coursename, Course course){
        if(coursename.equals(course.coursename)){
            allCourses.add(course);
        }
    }
    private void search(String coursecode, String coursename, Course course){
        if(course.coursecode.equals(coursecode) && course.coursename.equals(coursename)){
            allCourses.add(course);
        }
    }
}