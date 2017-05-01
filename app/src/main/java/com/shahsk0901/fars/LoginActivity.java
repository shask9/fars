package com.shahsk0901.fars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
        checkLogin();
        setContentView(R.layout.activity_login);

        final EditText log = (EditText) findViewById(R.id.loginID);
        final EditText pass = (EditText) findViewById(R.id.password);
        final TextView register = (TextView) findViewById(R.id.register);
        final TextView forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        final Button login = (Button) findViewById(R.id.login);
        forgotPassword.setText(Html.fromHtml("<a href=\"#\"><b style=\"background-color:black\">Forgot Password? Click Here</b></a>"));
        register.setText(Html.fromHtml("<a href='#'><b>New student? Register here</b></a>"));
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
                                               SharedPreferences.Editor sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_ID",MODE_PRIVATE).edit();
                                               sharedPreferences.putString("loginID",student.netID);
                                               sharedPreferences.apply();
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
                                   SharedPreferences.Editor sharedPreferences = getApplicationContext().getSharedPreferences("ADMIN_LOGIN_ID",MODE_PRIVATE).edit();
                                   sharedPreferences.putString("loginID",admin.adminID);
                                   sharedPreferences.apply();
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

    public void checkLogin() {
        SharedPreferences getSharedData = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
        String loginID = getSharedData.getString("loginID",null);
        if(loginID!=null) {
            Intent StudentHome = new Intent(getApplicationContext(),StudentHome.class);
            startActivity(StudentHome);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}