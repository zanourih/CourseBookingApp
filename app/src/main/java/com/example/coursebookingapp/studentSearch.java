package com.example.coursebookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.DateFormatSymbols;

public class studentSearch extends AppCompatActivity {
    private Button search;
    private Spinner daysSpinner;
    private String[] days;
    private EditText editTextCourseCode, editTextCourseName;
    private String coursecode, coursename, inputDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);

        // Search Input
        editTextCourseCode = findViewById(R.id.searchCourseCodeInput);
        editTextCourseName = findViewById(R.id.searchCourseNameInput);
        daysSpinner = (Spinner) findViewById(R.id.daySpinner);
        populateSpinner();

        search = (Button) findViewById(R.id.studentSearchCourseBtn);


        search.setOnClickListener(v -> {
            getSearchInput();

            Intent i=new Intent(this, CoursesList.class);

            i.putExtra("coursecode",coursecode);
            i.putExtra("coursename",coursename);
            i.putExtra("day", inputDay);

            startActivity(i);
        });


    }

    private void getSearchInput() {
        inputDay = daysSpinner.getSelectedItem().toString();
        coursecode = editTextCourseCode.getText().toString().trim();
        coursename = editTextCourseName.getText().toString().trim();

        int selectedItemOfMySpinner = daysSpinner.getSelectedItemPosition();
        String actualPositionOfMySpinner = (String) daysSpinner.getItemAtPosition(selectedItemOfMySpinner);

        if(actualPositionOfMySpinner.isEmpty()){
            inputDay = "All days";
        }


        if(coursecode.isEmpty() && coursename.isEmpty() && inputDay.equals("All days")){
            editTextCourseCode.setError("At least one input is required");
            editTextCourseCode.requestFocus();
            return;
        }
    }

    private void populateSpinner() {
        days = new String[] {"All days","Monday","Tuesday","Wednesday","Thursday","Friday"};
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(dayAdapter);

    }

}