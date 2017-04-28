package com.shahsk0901.fars;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference db,checkExistingStudent;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseDatabase.getInstance().getReference("Student");
        final Button register = (Button) findViewById(R.id.register);
        final Spinner securityQuestions = (Spinner) findViewById(R.id.securityQuestions);
        final EditText nID = (EditText) findViewById(R.id.netID);
        final EditText pass = (EditText) findViewById(R.id.password);
        final EditText name = (EditText) findViewById(R.id.name);
        securityQuestions.setPrompt("Security Question");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this,R.array.securityQuestions,R.layout.activity_register_spinner);
        securityQuestions.setAdapter(adapter);
        View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String netID = nID.getText().toString();
                Boolean flag1 = valid(1,netID,nID);

                final String password = pass.getText().toString();
                Boolean flag2 = valid(2,password,pass);

                final String fullName = name.getText().toString();
                Boolean flag3 = valid(3,fullName,pass);

                if(flag1 && flag2 && flag3) {
                    checkExistingStudent = FirebaseDatabase.getInstance().getReference("/Student/"+netID);
                    checkExistingStudent.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            student = dataSnapshot.getValue(Student.class);
                            if(student.netID.equals(netID)) {
                                Toast.makeText(getApplicationContext(),"Student already registered",Toast.LENGTH_SHORT).show();
                            } else {
                                Student createStudentAccount = new Student(netID,password,fullName);
                                db.child(netID).setValue(createStudentAccount);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };

        register.setOnClickListener(registerListener);
    }

    public boolean valid(Integer option,String input,EditText editText) {
        Boolean completeData = true;
        if(option == 1) {
            if(TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            } else {
                boolean valid = Pattern.matches("^[a-z]{3}[0-9]{4}$",input);
                if(!valid) {
                    editText.setError("Invalid NetID");
                    completeData = false;
                }
            }
        }

        if(option == 2) {
            if(TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            }
        }

        if(option == 3) {
            if(TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            }
        }

        return completeData;
    }
}
