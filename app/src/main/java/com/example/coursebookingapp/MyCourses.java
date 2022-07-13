package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCourses extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference dbRef;
    private FirebaseUser user;
    private String userID;
    private MyAdapter adapter;


    private ArrayList<Course> myCourses;

    private Button back;
    private TextView noCourseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        noCourseTextView = (TextView) findViewById(R.id.noCourseTV);

        back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(v -> {
            startActivity(new Intent(this, InstructorApp.class));
        });

        // getting current user ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        recyclerView = findViewById(R.id.myCoursesRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myCourses = new ArrayList<Course>();
        adapter = new MyAdapter(this, myCourses);
        recyclerView.setAdapter(adapter);

        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                //myCourses = userProfile.getMyCourses();
                if(userProfile.getMyCourses()!=null){
                    for(Course c : userProfile.getMyCourses()){
                        myCourses.add(c);
                    }
                }
                if(myCourses==null || myCourses.isEmpty()){
                    noCourseTextView.setVisibility(View.VISIBLE);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}