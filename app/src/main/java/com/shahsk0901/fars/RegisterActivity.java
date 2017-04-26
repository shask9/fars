package com.shahsk0901.fars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText nID;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseDatabase.getInstance().getReference("Student");
        register = (Button) findViewById(R.id.register);

        View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean completeData = true;
                nID = (EditText) findViewById(R.id.netID);
                String netID = nID.getText().toString();
                completeData = checkNetId(netID,nID,completeData);
                if(completeData) {
                    Student student = new Student(netID);
                    db.child(netID).setValue(student);
                }
            }
        };

        register.setOnClickListener(registerListener);
    }

    public boolean checkNetId(String netID,EditText nID,Boolean completeData) {
        if(TextUtils.isEmpty(netID)) {
            nID.setError("Cannot be empty");
            completeData = false;
        } else {
            boolean valid = Pattern.matches("^[a-z]{3}[0-9]{4}$",netID);
            if(!valid) {
                nID.setError("Invalid NetID");
                completeData = false;
            }
        }
        return completeData;
    }
}
