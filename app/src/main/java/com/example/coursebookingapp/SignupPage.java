package com.example.coursebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SignupPage extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    public int numAdmin=0;
    public boolean adminExist = false;
    public int maxNumAdmin = 1;  // this max value is set to 1 but can be modified to allow more than one admin



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        mAuth = FirebaseAuth.getInstance();

        Button createBtn = findViewById(R.id.createAccountBtn); // create account button


        editTextName = findViewById(R.id.nameInput);
        editTextEmail = findViewById(R.id.emailinput);
        editTextPassword = findViewById(R.id.userNamePassword);

        progressBar = findViewById(R.id.progressBarSignup);



        createBtn.setOnClickListener(v -> {     //when "create account" button is clicked
            registerUser();
        });

    }

    private void registerUser(){
        ProgressBar progressBar = findViewById(R.id.progressBarSignup);

        RadioButton studentAccBtn = findViewById(R.id.radioBtnStudent);


        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String role = getAccountType();
        ArrayList<Course> myCourses = new ArrayList<Course>();




        //validateRegister();
        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email address is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Password must be at least 6 characters long");
            editTextPassword.requestFocus();
            return;
        }
        if(role.equals("not selected")){
            Toast.makeText(getApplicationContext(), "Select Account type", Toast.LENGTH_LONG).show();
            studentAccBtn.requestFocus();
            return;
        }
        if(role.equals("Admin")){ // check in the DB if admin exists !!
            if(numAdmin<maxNumAdmin){ // maxNumAdmin = 1
                numAdmin++;
                adminExist=true;
                return;
            }else{
                Toast.makeText(getApplicationContext(), "Cannot create account. Admin already exists", Toast.LENGTH_LONG).show();
                return;
            }

        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            User user = new User(name, email,password,role);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                             /*

                            //registerUserInDB
                            if(role.equals("Instructor")){
                                Instructor instructor = new Instructor(name,email,password,role,myCourses);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(instructor);

                            }
                            if(role.equals("Student")){
                                Student student = new Student(name,email,password,role,myCourses);
                                //FirebaseDatabase.getInstance().getReference("Users").push().setValue(user);
                            }
                            if(role.equals("Admin")){
                                Admin admin = new Admin(name,email,password,role);
                                //FirebaseDatabase.getInstance().getReference("Users").push().setValue(user);
                            }

                              */

                            // redirect to profile
                            startActivity(new Intent(SignupPage.this, UserProfile.class));

                        }else{
                            Toast.makeText(SignupPage.this, "Failed to register. Try again.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }


    public String getAccountType(){
        RadioButton studentAccBtn = findViewById(R.id.radioBtnStudent);
        RadioButton instrAccBtn = findViewById(R.id.radioBtnInstructor);
        RadioButton adminAccBtn = findViewById(R.id.radioBtnAdmin);




        if(instrAccBtn.isChecked()){
            return "Instructor";
        }
        if(studentAccBtn.isChecked()){
            return "Student";
        }
        if(adminAccBtn.isChecked()){
            return "Admin";
        }
        return "not selected";
    }


}