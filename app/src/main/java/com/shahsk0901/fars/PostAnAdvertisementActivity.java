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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostAnAdvertisementActivity extends AppCompatActivity {

    private DatabaseReference db;
    private String adID;
    private Integer advertisementID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisements);
        String netID = setActionBar();
        db = FirebaseDatabase.getInstance().getReference("Advertisement");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference pushAd = db.push();
                adID = pushAd.getKey();
                Advertisement ad = new Advertisement(adID);
                db.child(adID).setValue(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);

        SharedPreferences getSharedData = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
        String loginID = getSharedData.getString("loginID",null);
        return loginID;
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
                Intent StudentHome = new Intent(getApplicationContext(),StudentHome.class);
                startActivity(StudentHome);
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
        Toast.makeText(getApplicationContext(),"Please Logout",Toast.LENGTH_SHORT).show();
    }
}
