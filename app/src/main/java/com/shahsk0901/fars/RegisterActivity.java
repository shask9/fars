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

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText netID;
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
                netID = (EditText) findViewById(R.id.netID);
                if(TextUtils.isEmpty(netID.getText().toString()))
                    netID.setError("Can't be empty");

                Student student = new Student(netID.getText().toString());
                db.child(netID.getText().toString()).setValue(student);
            }
        };

        register.setOnClickListener(registerListener);
    }
}
