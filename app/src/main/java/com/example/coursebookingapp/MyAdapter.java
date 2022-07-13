package com.example.coursebookingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Course> courseResults;
    private Context context;

    public MyAdapter(Context context, ArrayList<Course> courseResults) {
        this.context = context;
        this.courseResults = courseResults;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.course_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.course = courseResults.get(position);

        String code = courseResults.get(position).getCoursecode();
        holder.coursecodeTV.setText(code);

        String name = courseResults.get(position).getCoursename();
        holder.coursenameTV.setText(name);

        String description = courseResults.get(position).getDescription();
        holder.descriptionTV.setText("Description: " + description);

        String instructor = courseResults.get(position).getInstructor();
        holder.instructorTV.setText("Instructor: " + instructor);

        Boolean status = courseResults.get(position).isTeacherAssigned();
        if(status){holder.statusIV.setImageResource(R.drawable.redcross);}

        int capacity = courseResults.get(position).getCapacity();
        holder.capacityTV.setText("Capacity: " + String.valueOf(capacity));

        ArrayList<String> schedule = courseResults.get(position).getSchedule();
        String scheduleStr = "Schedule: ";
        for(String e : schedule){
            scheduleStr = scheduleStr + " " + e;
        }
        holder.scheduleTV.setText(scheduleStr); // IMPORTANT: later change to schedule.format to have the right schedule format


    }

    @Override
    public int getItemCount() {
        return courseResults.size();
    }


    // Nested Class
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView coursecodeTV, coursenameTV, descriptionTV, capacityTV, scheduleTV, instructorTV;
        ImageView statusIV;
        Button selectButton;
        Course course;

        public MyViewHolder(@NonNull View view){
            super(view);

            coursecodeTV = view.findViewById(R.id.coursecodeTextView);
            coursenameTV = view.findViewById(R.id.coursenameTextView);
            descriptionTV = view.findViewById(R.id.descriptionTextView);
            instructorTV = view.findViewById(R.id.instructorNameTextView);
            statusIV = view.findViewById(R.id.statusiconImageView);
            capacityTV = view.findViewById(R.id.capacityTextView);
            scheduleTV = view.findViewById(R.id.scheduleTextView);
            selectButton = view.findViewById(R.id.selectCourseBtn);

            selectButton.setOnClickListener(v -> {
                Intent i = new Intent(context, ViewCourse.class);
                i.putExtra("course",course);
                context.startActivity(i);
            });
        }
    }

}
