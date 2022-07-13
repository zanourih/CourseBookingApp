package com.example.coursebookingapp;

import static com.example.coursebookingapp.R.color.darkred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.signupbutton);
        Button btn2 = findViewById(R.id.loginbutton);

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupPage.class);
            startActivity(intent);
        });

        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
        });



    }


}