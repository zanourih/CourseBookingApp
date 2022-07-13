package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class InstructorApp extends AppCompatActivity {

    private Button logout, backProfile, searchCourse, viewAllCourses, myCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_app);

        logout = (Button) findViewById(R.id.logoutBtn);
        backProfile = (Button) findViewById(R.id.profile);
        searchCourse = (Button) findViewById(R.id.appSearchCourseBtn);
        viewAllCourses = (Button) findViewById(R.id.viewAllCoursesBtn);
        myCourses = (Button) findViewById(R.id.myCoursesBtn);

        logout.setOnClickListener(v -> {     //when "log out" button is clicked
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(InstructorApp.this, MainActivity.class));
        });

        backProfile.setOnClickListener(v -> {
            startActivity(new Intent(InstructorApp.this, UserProfile.class));
        });

        searchCourse.setOnClickListener(v -> {
            startActivity(new Intent(InstructorApp.this, SearchCourse.class));
        });

        viewAllCourses.setOnClickListener(v -> {
            startActivity(new Intent(InstructorApp.this, CoursesList.class));
        });

        myCourses.setOnClickListener(v -> {
            startActivity(new Intent(InstructorApp.this, MyCourses.class));
        });


    }

}