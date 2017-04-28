package com.shahsk0901.fars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference db, checkExistingStudent;
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
        final EditText ph = (EditText) findViewById(R.id.phone);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText sID = (EditText) findViewById(R.id.studentID);
        final EditText answer = (EditText) findViewById(R.id.securityAnswer);
        securityQuestions.setPrompt("Security Question");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.securityQuestions, R.layout.activity_register_spinner);
        securityQuestions.setAdapter(adapter);
        View.OnClickListener registerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String netID = nID.getText().toString();
                Boolean flag1 = valid(1, netID, nID);

                final String password = pass.getText().toString();
                Boolean flag2 = valid(2, password, pass);

                final String fullName = name.getText().toString();
                Boolean flag3 = valid(3, fullName, name);

                final String mobile = ph.getText().toString();
                Boolean flag4 = valid(4, mobile, ph);

                final String studentEmail = email.getText().toString();
                Boolean flag5 = valid(5, studentEmail, email);

                final String studentID = sID.getText().toString();
                Boolean flag6 = valid(6, studentID, sID);

                final String securityQuestion = securityQuestions.getSelectedItem().toString();

                final String securityAnswer = answer.getText().toString();
                Boolean flag7 = valid(7, securityAnswer, answer);

                final String accountStatus = "Active";

                if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7) {
                    checkExistingStudent = FirebaseDatabase.getInstance().getReference("Student/" + netID);
                    checkExistingStudent.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() == null) {
                                student = new Student(netID, password, fullName, mobile, studentEmail, studentID, securityQuestion, securityAnswer, accountStatus);
                                db.child(netID).setValue(student);
                                Toast.makeText(getApplicationContext(), "Student successfully registered in system", Toast.LENGTH_LONG).show();
                                Intent LoginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(LoginActivity);
                            } else {
                                student = dataSnapshot.getValue(Student.class);
                                if (student.netID.equals(netID)) {
                                    Toast.makeText(getApplicationContext(), "Student already registered", Toast.LENGTH_SHORT).show();
                                }
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

    public boolean valid(Integer option, String input, EditText editText) {
        Boolean completeData = true;
        if (option == 1) {
            if (TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            } else {
                boolean valid = Pattern.matches("^[a-z]{3}[0-9]{4}$", input);
                if (!valid) {
                    editText.setError("Invalid NetID");
                    completeData = false;
                }
            }
        }

        if (option == 2) {
            if (TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            }
        }

        if (option == 3) {
            if (TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            }
        }

        if (option == 4) {
            if (TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            } else {
                boolean valid = Pattern.matches("^[0-9]{10}$", input);
                if (!valid) {
                    editText.setError("Invalid mobile");
                    completeData = false;
                }
            }
        }

        if (option == 5) {
            if (TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            } else {
                boolean valid = Pattern.matches("^\\w+\\.?\\w+\\@mavs\\.uta\\.edu$", input);
                if (!valid) {
                    editText.setError("Invalid student email");
                    completeData = false;
                }
            }
        }

        if (option == 6) {
            if (TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            } else {
                boolean valid = Pattern.matches("^100[0-9]{7}$", input);
                if (!valid) {
                    editText.setError("Invalid student ID");
                    completeData = false;
                }
            }
        }

        if (option == 7) {
            if (TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            }
        }
        return completeData;
    }
}
