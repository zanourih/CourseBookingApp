package com.example.coursebookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class teachCourse extends AppCompatActivity {

    private Button saveBtn;
    private Course course;
    private int capacity;
    private String description;
    private ArrayList<String> schedule = new ArrayList<String>();

    private TextView codeTextView,nameTextView;
    private EditText descriptionTV,capacityTV,scheduleTV;

    private String courseID;

    private FirebaseUser user;
    private String userID;
    private DatabaseReference userRef;

    private String profName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_course);

        course = (Course) getIntent().getSerializableExtra("course");

        getProfName();

        // displaying info
        codeTextView = findViewById(R.id.coursecodeTextView);
        nameTextView = findViewById(R.id.coursenameTextView);
        descriptionTV = findViewById(R.id.enterDescriptionTextView);
        capacityTV = findViewById(R.id.capacityTextNumber);
        scheduleTV = findViewById(R.id.enterScheduleTextView);

        codeTextView.setText(course.getCoursecode());
        nameTextView.setText(course.getCoursename());


        // save edits
        saveBtn = (Button) findViewById(R.id.saveTeachCourse);
        saveBtn.setOnClickListener(v -> {
            if(!saveEdits()){
                return;
            }
            updateMyCourses();
            startActivity(new Intent(this, InstructorApp.class));
        });
    }

    private void getProfName() {
        // getting current user ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                profName = userProfile.name;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void updateMyCourses() {
        // getting current user ID
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile.getMyCourses()==null){
                    userProfile.setMyCourses(new ArrayList<Course>());
                }
                // change name
                course.setInstructor(userProfile.name);
                Boolean courseExists = false;
                // add to my courses
                for(Course c : userProfile.getMyCourses()){
                    if(c.coursecode.equals(course.coursecode)){
                        c = course;
                        courseExists = true;
                    }
                }
                if(!courseExists){
                    userProfile.getMyCourses().add(course);
                }
                // send to db
                userRef.child(userID).setValue(userProfile);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public Boolean saveEdits(){
        // retrieving input
        String capacityStr = capacityTV.getText().toString().trim();
        capacity = Integer.valueOf(capacityTV.getText().toString().trim()) ;
        description = descriptionTV.getText().toString().trim();
        String scheduleStr = scheduleTV.getText().toString().trim();
        schedule = getScheduleInput();

        // validating
        if(capacity<0 || capacity>500){
            capacityTV.setError("Number not valid");
            capacityTV.requestFocus();
            return false;
        }
        if(capacityStr.isEmpty()){
            capacityTV.setError("Field is required");
            capacityTV.requestFocus();
            return false;
        }
        if(description.isEmpty()){
            descriptionTV.setError("Field is required");
            descriptionTV.requestFocus();
            return false;
        }
        if(scheduleStr.isEmpty()){
            scheduleTV.setError("Field is required");
            scheduleTV.requestFocus();
            return false;
        }
        if(scheduleStr.indexOf(";")==-1){
            scheduleTV.setError("Format incorrect");
            scheduleTV.requestFocus();
            return false;
        }

        // editing course
        course.setCapacity(capacity);
        course.setDescription(description);
        course.setSchedule(schedule);
        course.setTeacherAssigned(true);
        course.setInstructor(profName);

        saveCourseinDB();
        return true;

    }

    private void saveCourseinDB() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dbSnapshot : snapshot.getChildren()){
                    Course dbCourse = dbSnapshot.getValue(Course.class);
                    if(dbCourse.coursecode.equals(course.coursecode)){
                        courseID = dbSnapshot.getKey();
                        break;
                    }
                }
                dbRef.child(courseID).setValue(course);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public ArrayList<String> getScheduleInput(){
        String str = scheduleTV.getText().toString().trim();
        String [] scheduleArray = str.split(";");
        for(String e : scheduleArray){
            if(!e.isEmpty()){
                schedule.add(e);
            }
        }
        return schedule;
    }
}