package com.shahsk0901.fars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.view.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference db;
    private Student student;
    private Administrator admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText log = (EditText) findViewById(R.id.loginID);
        final EditText pass = (EditText) findViewById(R.id.password);
        final TextView register = (TextView) findViewById(R.id.register);
        final TextView forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        final Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {

               final String loginID = log.getText().toString();
               Boolean flag1 = valid(1,loginID,log);

               final String password = pass.getText().toString();
               Boolean flag2 = valid(2,password,pass);

               if(flag1 && flag2) {
                   db = FirebaseDatabase.getInstance().getReference("Administrator/"+loginID);
                   db.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           if(dataSnapshot.getValue() == null) {
                               db = FirebaseDatabase.getInstance().getReference("Student/"+loginID);
                               db.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       if(dataSnapshot.getValue() == null) {
                                           log.setError("Student not found");
                                       } else {
                                           student = dataSnapshot.getValue(Student.class);
                                           if(student.accountStatus.equals("Suspended")) {
                                               Toast.makeText(getApplicationContext(),"Account Suspended",Toast.LENGTH_SHORT).show();
                                           } else if(!student.password.equals(password)) {
                                               Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                                           } else {
                                               Intent StudentHome = new Intent(getApplicationContext(),StudentHome.class);
                                               startActivity(StudentHome);
                                           }
                                       }
                                   }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {

                                   }
                               });
                           } else {
                               admin = dataSnapshot.getValue(Administrator.class);
                               if(!password.equals(admin.adminPass)) {
                                   log.setError("Invalid Credentials");
                               } else {
                                   Intent AdminHome = new Intent(getApplicationContext(), AdminHome.class);
                                   startActivity(AdminHome);
                               }
                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });
               }
           }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgotPasswordActivity = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(ForgotPasswordActivity);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterActivity = new Intent(getApplicationContext(), com.shahsk0901.fars.RegisterActivity.class);
                startActivity(RegisterActivity);
            }
        });
    }

    public Boolean valid(Integer option, String input, EditText editText){
        Boolean completeData = true;
        if(option == 1 || option == 2) {
            if(TextUtils.isEmpty(input)) {
                editText.setError("Cannot be empty");
                completeData = false;
            }
        }
        return completeData;
    }
}
