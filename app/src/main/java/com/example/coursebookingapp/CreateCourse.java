package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCourse extends AppCompatActivity {

    private Button logout, backProfile,createCourse;
    private EditText editTextCourseCode, editTextCourseName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        logout = (Button) findViewById(R.id.logoutBtn);
        backProfile = (Button) findViewById(R.id.profile);
        createCourse = (Button) findViewById(R.id.createAccountBtn);

        editTextCourseCode = findViewById(R.id.createCourseCodeInput);
        editTextCourseName = findViewById(R.id.createCourseNameInput);


        logout.setOnClickListener(v -> {     //when "log out" button is clicked
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(CreateCourse.this, MainActivity.class));
        });

        backProfile.setOnClickListener(v -> {
            startActivity(new Intent(CreateCourse.this, UserProfile.class));
        });

        createCourse.setOnClickListener(v -> {
            createCourse();
        });
    }

    private void createCourse() {
        String coursecode = editTextCourseCode.getText().toString().trim();
        String coursename = editTextCourseName.getText().toString().trim();

        if(coursecode.isEmpty()){  // not validating the format yet
            editTextCourseCode.setError("Course code is required");
            editTextCourseCode.requestFocus();
            return;
        }
        if(coursename.isEmpty()) {
            editTextCourseName.setError("Course name is required");
            editTextCourseName.requestFocus();
            return;
        }

        //final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference ref = database.getReference("Courses");


        Course course = new Course(coursecode, coursename);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Courses").push();
        dbRef.setValue(course);

        startActivity(new Intent(this, CoursesList.class));

        /*
        String courseID = dbRef.getKey();
        //FirebaseDatabase.getInstance().getReference().child("Courses").push().setValue(course);


        // redirect
        Intent i=new Intent(CreateCourse.this,ViewCourse.class);
        i.putExtra("courseID",courseID);

        startActivity(i);

         */
    }


}