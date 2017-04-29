package com.shahsk0901.fars;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
            Bundle extras = getIntent().getExtras();
            String adminID = extras.getString("adminID");
            getSupportActionBar().setTitle(adminID);

        final Button students = (Button) findViewById(R.id.viewStudents);
        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewStudentsActivity = new Intent(getApplicationContext(), ViewStudentsActivity.class);
                startActivity(ViewStudentsActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
