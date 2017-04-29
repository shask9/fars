package com.shahsk0901.fars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private DatabaseReference db;
    private ViewFlipper flipViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText nID = (EditText) findViewById(R.id.netID);
        final Button verifyIdentity = (Button) findViewById(R.id.verifyNetID);
        final TextView securityQueston = (TextView) findViewById(R.id.securityQuestion);
        final EditText securityAnswer = (EditText) findViewById(R.id.securityAnswer);
        final Button verifyAnswer = (Button) findViewById(R.id.verifyAnswer);
        final EditText newPass = (EditText) findViewById(R.id.newPassword);
        final EditText confirmPass = (EditText) findViewById(R.id.confirmPassword);
        final Button reset = (Button) findViewById(R.id.reset);

        flipViews = (ViewFlipper) findViewById(R.id.flipViews);
        verifyIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String netID = nID.getText().toString();
                Boolean flag1 = valid(1, netID, nID);
                if(!flag1) {
                    //nID.setError("Cannot be empty!");
                } else {
                    db = FirebaseDatabase.getInstance().getReference("Student/"+netID);
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() == null) {
                                Toast.makeText(getApplicationContext(),"Student not found",Toast.LENGTH_SHORT).show();
                            } else {
                                final Student student = dataSnapshot.getValue(Student.class);
                                securityQueston.setText("Question:\n"+student.securityQuestion);
                                flipScreen(1,1);
                                verifyAnswer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String answer = securityAnswer.getText().toString();
                                        if(!TextUtils.isEmpty(answer)) {
                                            if(answer.equals(student.securityAnswer)) {
                                                flipScreen(2,1);
                                                reset.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String password = newPass.getText().toString();
                                                        String confirm = confirmPass.getText().toString();
                                                        if(!TextUtils.isEmpty(password)) {
                                                            if(confirm.equals(password)) {
                                                                db.child("password").setValue(password);
                                                                Toast.makeText(getApplicationContext(),"Password reset",Toast.LENGTH_SHORT).show();
                                                                Intent LoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                                                                startActivity(LoginActivity);
                                                            } else {
                                                                confirmPass.setError("Passwords don't match");
                                                            }
                                                        } else {
                                                            newPass.setError("Cannot be empty!");
                                                        }
                                                    }
                                                });
                                            } else {
                                                securityAnswer.setError("Incorrect Answer!");
                                            }
                                        } else {
                                            securityAnswer.setError("Cannot be empty!");
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
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
        return completeData;
    }

    public void flipScreen(int i,int option) {
        if(option == 1) {
            flipViews.setFlipInterval(1);
            flipViews.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.layout.activity_forgot_password_transition_flip_in));
            flipViews.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.layout.activity_forgot_password_transition_flip_out));
            flipViews.setDisplayedChild(i);
        }

        if(option == 2) {
            flipViews.setFlipInterval(1);
            flipViews.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.layout.activity_forgot_password_transition_flip_in));
            flipViews.setDisplayedChild(i);
        }
    }
    @Override
    public void onBackPressed() {
        if(flipViews.getDisplayedChild() == 0) {
            Intent LoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(LoginActivity);
        }

        if(flipViews.getDisplayedChild() == 1) {
            flipScreen(0,2);
        }
    }
}












