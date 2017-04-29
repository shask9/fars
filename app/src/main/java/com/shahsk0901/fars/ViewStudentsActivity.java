package com.shahsk0901.fars;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewStudentsActivity extends AppCompatActivity {

    private DatabaseReference db;
    private ListView lv;
    ArrayList<Student> students = new ArrayList<Student>();
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        String loginID = setActionBar();
        if(loginID != null)
            getSupportActionBar().setTitle(getSupportActionBar().getTitle() + " - " + loginID);

        lv = (ListView) findViewById(R.id.list);
        adapter = new StudentAdapter(this,R.layout.activity_view_students_list_template,students);
        lv.setAdapter(adapter);
        db = FirebaseDatabase.getInstance().getReference("Student");
        db.orderByChild("fullName").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                adapter.add(student);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adapter.clear();
                db = FirebaseDatabase.getInstance().getReference("Student/");
                db.orderByChild("fullName").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Student student = dataSnapshot.getValue(Student.class);
                        adapter.add(student);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference.goOffline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(),AdminHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.ic_logout:
                SharedPreferences prefs = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
                prefs.edit().clear().apply();
                Intent LoginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(LoginActivity);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent AdminHome = new Intent(getApplicationContext(), AdminHome.class);
        startActivity(AdminHome);
    }

    public String setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);

        SharedPreferences getSharedData = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
        String loginID = getSharedData.getString("loginID",null);
        return loginID;
    }
}