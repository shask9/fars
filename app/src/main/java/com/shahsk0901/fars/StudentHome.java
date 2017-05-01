package com.shahsk0901.fars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StudentHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        setActionBar();

        final Button postAdvertisement = (Button) findViewById(R.id.postAdvertisement);
        final Button updatePassword = (Button) findViewById(R.id.updatePassword);
        final Button myAdvertisements = (Button) findViewById(R.id.myAdvertisements);

        postAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PostAnAdvertisementActivity = new Intent(getApplicationContext(), PostAnAdvertisementActivity.class);
                startActivity(PostAnAdvertisementActivity);
            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent UpdatePassswordActivity = new Intent(getApplicationContext(), UpdatePasswordActivity.class);
                startActivity(UpdatePassswordActivity);
            }
        });

        myAdvertisements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAdvertisementsActivity = new Intent(getApplicationContext(), MyAdvertisementsActivity.class);
                startActivity(MyAdvertisementsActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    public String setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);

        SharedPreferences getSharedData = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
        String loginID = getSharedData.getString("loginID",null);
        actionBar.setTitle(loginID + "'s Home");
        return loginID;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Please Logout",Toast.LENGTH_SHORT).show();
    }
}
