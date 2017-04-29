package com.shahsk0901.fars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentAdapter extends ArrayAdapter<Student> {
    private final Context context;
    private ArrayList<Student> students;
    public StudentAdapter(Context context, int textViewResourceId, ArrayList<Student> students) {
        super(context, textViewResourceId, students);
        this.context = context;
        this.students = students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //Student student = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_view_students_list_template,null);
        }
        Student student = students.get(position);
        if(student != null) {
            // Lookup view for data population
            final TextView tvNetID = (TextView) convertView.findViewById(R.id.tvNetID);
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
            TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            final Button btSetStatus = (Button) convertView.findViewById(R.id.btSetStatus);
            btSetStatus.setTag(position);
            // Populate the data into the template view using the data object
            tvNetID.setText("Net ID: "+student.netID);
            tvName.setText("Name: "+student.fullName);
            tvEmail.setText("Email: "+student.email);
            tvStatus.setText("Account Status: "+student.accountStatus);
            if(student.accountStatus.equals("Active")) {
                btSetStatus.setText("Suspend Account");
            } else {
                btSetStatus.setText("Activate Account");
            }

            btSetStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    Student student = getItem(position);
                    DatabaseReference updateStudentStatus = FirebaseDatabase.getInstance().getReference("Student/"+student.netID);
                    Map<String, Object> updateStatus = new HashMap<String, Object>();
                    if(student.accountStatus.equals("Active")) {
                        updateStatus.put("accountStatus","Suspended");
                        updateStudentStatus.updateChildren(updateStatus);
                        Toast.makeText(getContext(),student.netID+" successfully suspended",Toast.LENGTH_SHORT).show();
                    } else {
                        updateStatus.put("accountStatus","Active");
                        updateStudentStatus.updateChildren(updateStatus);
                        Toast.makeText(getContext(),student.netID+" successfully activated",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
