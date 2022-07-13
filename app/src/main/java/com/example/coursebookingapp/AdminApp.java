package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminApp extends AppCompatActivity {

    private Button logout,backProfile,createCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_app);

        logout = (Button) findViewById(R.id.logoutBtn);
        backProfile = (Button) findViewById(R.id.profile);
        createCourse = (Button) findViewById(R.id.appCreateCourseBtn);


        logout.setOnClickListener(v -> {     //when "log out" button is clicked
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminApp.this, MainActivity.class));
        });

        backProfile.setOnClickListener(v -> {
            startActivity(new Intent(AdminApp.this, UserProfile.class));
        });

        createCourse.setOnClickListener(v -> {
            startActivity(new Intent(AdminApp.this, CreateCourse.class));
        });
    }
}