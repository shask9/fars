package com.shahsk0901.fars;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class UpdatePasswordActivity extends AppCompatActivity {

    private DatabaseReference db;
    private ViewFlipper flipViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);


        SharedPreferences getSharedData = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
        final String netID = getSharedData.getString("loginID",null);

        final EditText oldPass = (EditText) findViewById(R.id.oldPasswordUpdate);
        final EditText newPass = (EditText) findViewById(R.id.newPasswordUpdate);
        final EditText confirmPass = (EditText) findViewById(R.id.confirmPasswordUpdate);
        final Button reset = (Button) findViewById(R.id.update);


                    db = FirebaseDatabase.getInstance().getReference("Student/"+netID);
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() == null) {
                                Toast.makeText(getApplicationContext(),"Error in fetching details",Toast.LENGTH_SHORT).show();
                            } else {
                                final Student student = dataSnapshot.getValue(Student.class);
                                reset.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String oldPassword = oldPass.getText().toString();
                                        if(!TextUtils.isEmpty(oldPassword)) {
                                            if(oldPassword.equals(student.password)) {
                                                reset.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String password = newPass.getText().toString();
                                                        String confirm = confirmPass.getText().toString();
                                                        if(!TextUtils.isEmpty(password)) {
                                                            if(confirm.equals(password)) {
                                                                db.child("password").setValue(password);
                                                                Toast.makeText(getApplicationContext(),"Password reset",Toast.LENGTH_SHORT).show();
                                                                Intent StudentHome = new Intent(getApplicationContext(), StudentHome.class);
                                                                startActivity(StudentHome);
                                                            } else {
                                                                confirmPass.setError("Passwords don't match");
                                                            }
                                                        } else {
                                                            newPass.setError("Cannot be empty!");
                                                        }
                                                    }
                                                });
                                            } else {
                                                oldPass.setError("Old password did not match");
                                            }
                                        } else {
                                            oldPass.setError("Old Password Cannot be empty!");
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

    @Override
    public void onBackPressed() {
            Intent StudentHome = new Intent(getApplicationContext(), StudentHome.class);
            startActivity(StudentHome);
    }

}
