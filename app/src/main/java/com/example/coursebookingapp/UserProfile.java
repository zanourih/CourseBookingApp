package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button logout, myapps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        logout = (Button) findViewById(R.id.logoutBtn);
        myapps = (Button) findViewById(R.id.myAppsBtn);


        logout.setOnClickListener(v -> {     //when "log out" button is clicked
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(UserProfile.this, MainActivity.class));
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final TextView nameTextView = (TextView) findViewById(R.id.nameInput);
        final TextView welcomeTextView = (TextView) findViewById(R.id.welcome);
        final ImageView welcomeImageView = (ImageView) findViewById(R.id.welcomeImage);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);


                if(userProfile!=null){
                    String name = userProfile.name;
                    String role = userProfile.role;

                    if (role.equals("Student")){
                        welcomeImageView.setBackgroundResource(R.drawable.uottawastudents);
                            myapps.setOnClickListener(v -> {
                            startActivity(new Intent(UserProfile.this, StudentApp.class));
                        });

                    }
                    if (role.equals("Instructor")){
                        welcomeImageView.setBackgroundResource(R.drawable.uottawaprofessor);
                        myapps.setOnClickListener(v -> {
                            startActivity(new Intent(UserProfile.this, InstructorApp.class));
                        });

                    }
                    if (role.equals("Admin")){
                        welcomeImageView.setBackgroundResource(R.drawable.adminicon);

                        myapps.setOnClickListener(v -> {
                            startActivity(new Intent(UserProfile.this, AdminApp.class));
                        });

                    }

                    welcomeTextView.setText("Welcome "+ name + ", \n you are logged in as " + role);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something wrong happened. Try again!", Toast. LENGTH_LONG).show();
            }
        });
    }
}