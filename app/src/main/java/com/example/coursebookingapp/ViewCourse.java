package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewCourse extends AppCompatActivity {

    private Button logout, backProfile, myApps, functionBtn, dropBtn;
    private Course course;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    private User userProfile;

    private ArrayList<Course> myCourses;

    private String courseID;
    private DatabaseReference courseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);

        // getting user ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        course = (Course) getIntent().getSerializableExtra("course");

        logout = (Button) findViewById(R.id.logoutBtn);
        backProfile = (Button) findViewById(R.id.profile);
        myApps = (Button) findViewById(R.id.myAppsBtn);
        functionBtn = (Button) findViewById(R.id.functionBtn);
        dropBtn = (Button) findViewById(R.id.dropTeachCourse);

        // no one teaches the course yet
        if(!course.isTeacherAssigned()){
            functionBtn.setText("Add course");
        }


        // Buttons
        logout.setOnClickListener(v -> {     //when "log out" button is clicked
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ViewCourse.this, MainActivity.class));
        });

        backProfile.setOnClickListener(v -> {
            startActivity(new Intent(ViewCourse.this, UserProfile.class));
        });
        myApps.setOnClickListener(v -> {
            startActivity(new Intent(ViewCourse.this, InstructorApp.class));
        });
        functionBtn.setOnClickListener(v -> { // to add a course
            Intent i = new Intent(ViewCourse.this, teachCourse.class);
            i.putExtra("course",course);
            startActivity(i);
        });
        dropBtn.setOnClickListener(v -> {
            dropCourse();
            Toast.makeText(this, "Course dropped", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ViewCourse.this, CoursesList.class));
        });
        // end of Buttons


        // Setting Course Details
        final TextView codeTextView = findViewById(R.id.coursecodeTextView);
        final TextView nameTextView = findViewById(R.id.coursenameTextView);
        final TextView descriptionTV = findViewById(R.id.descriptionTextView);
        final ImageView greenStatus = findViewById(R.id.statusgreenImageView);
        final ImageView redStatus = findViewById(R.id.statusredImageView);
        final TextView capacityTV = findViewById(R.id.singlepageCapacityTextView);
        final TextView scheduleTV = findViewById(R.id.scheduleTextView);


        codeTextView.setText(course.getCoursecode());
        nameTextView.setText(course.getCoursename());
        descriptionTV.setText(course.getDescription());

        if(course.isTeacherAssigned()){
            greenStatus.setVisibility(View.INVISIBLE);
            redStatus.setVisibility(View.VISIBLE);
        }
        else{
            redStatus.setVisibility(View.INVISIBLE);
            greenStatus.setVisibility(View.VISIBLE);
        }

        capacityTV.setText(String.valueOf(course.getCapacity()));

        ArrayList<String> schedule = course.getSchedule();

        String scheduleStr = "Schedule: ";
        for(String e : schedule){
            scheduleStr = scheduleStr + " " + e;
        }
        scheduleTV.setText(scheduleStr);
        // end of setting Course Details

        // getting instructor information
        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(User.class);
                if(userProfile!=null) {
                    String role = userProfile.role;
                    String name = userProfile.name;
                    myCourses = userProfile.getMyCourses();
                    if(myCourses==null){
                        myCourses = new ArrayList<Course>();
                        userProfile.setMyCourses(myCourses);
                    }

                    // instructor already teaches this course
                    Boolean isInMyCourses = false;
                    for(Course c : myCourses){
                        if(c.coursecode.equals(course.coursecode)){
                            functionBtn.setText("edit");
                            dropBtn.setVisibility(View.VISIBLE);
                            isInMyCourses = true;
                        }
                    }
                    if(course.isTeacherAssigned() && !isInMyCourses ){
                        functionBtn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void dropCourse() {
        course.setInstructor("No instructor");
        course.setCapacity(0);
        course.setDescription("No description yet");
        course.setSchedule(new ArrayList<String>());
        course.setTeacherAssigned(false);

        updateMyCourses();
        saveCourseinDB();

    }

    private void updateMyCourses() {
        // getting current user ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                for(Course c : userProfile.getMyCourses()){
                    if(c.coursecode.equals(course.coursecode)){
                        userProfile.getMyCourses().remove(c);
                        break;
                    }
                }
                // send to db
                dbRef.child(userID).setValue(userProfile);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void saveCourseinDB() {
        courseRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dbSnapshot : snapshot.getChildren()){
                    Course dbCourse = dbSnapshot.getValue(Course.class);
                    if(dbCourse.coursecode.equals(course.coursecode)){
                        courseID = dbSnapshot.getKey();
                        break;
                    }
                }
                courseRef.child(courseID).setValue(course);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
/*
    private Boolean isInMyCourses(){
        if(instructor.teachingCourses!=null){
            for(Course c : instructor.teachingCourses){
                if(c.equals(course)){
                    return true;
                }
            }
        }
        return false;
    }

 */


}