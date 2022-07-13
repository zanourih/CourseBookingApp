package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SearchCourse extends AppCompatActivity {

    private Button logout, backProfile, search;
    private EditText editTextCourseCode, editTextCourseName;
    private String coursecode, coursename;


    //private ArrayList<Course> searchResults;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);


        //searchResults = new ArrayList<Course>();

        // Buttons
        logout = (Button) findViewById(R.id.logoutBtn);
        backProfile = (Button) findViewById(R.id.profile);
        search = (Button) findViewById(R.id.searchCourseBtn);

        // Search Input
        editTextCourseCode = findViewById(R.id.searchCourseCodeInput);
        editTextCourseName = findViewById(R.id.searchCourseNameInput);





        logout.setOnClickListener(v -> {     //when "log out" button is clicked
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SearchCourse.this, MainActivity.class));
        });

        backProfile.setOnClickListener(v -> {
            startActivity(new Intent(SearchCourse.this, UserProfile.class));
        });

        search.setOnClickListener(v -> {
            getSearchInput();

            Intent i=new Intent(SearchCourse.this, CoursesList.class);

            i.putExtra("coursecode",coursecode);
            i.putExtra("coursename",coursename);

            startActivity(i);
        });

    }


    private void getSearchInput() {
        coursecode = editTextCourseCode.getText().toString().trim();
        coursename = editTextCourseName.getText().toString().trim();
        if(coursecode.isEmpty() && coursename.isEmpty()){
            editTextCourseCode.setError("At least one input is required");
            editTextCourseCode.requestFocus();
            return;
        }
    }

}