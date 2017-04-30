package com.shahsk0901.fars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostAnAdvertisementActivity extends AppCompatActivity {

    private DatabaseReference db;
    private String adID;
    private Integer rent;
    private List<String> utilities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_an_advertisement);
        final String netID = setActionBar();
        final Button post = (Button) findViewById(R.id.postAd);

        //Ad Type Spinner
        final Spinner ad =  (Spinner) findViewById(R.id.adType);
        final List<String> adTypeList = new ArrayList<String>();
        adTypeList.add("Apartment");
        adTypeList.add("Roommate");
        ArrayAdapter<String> adAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,adTypeList);
        ad.setAdapter(adAdapter);

        final Spinner apt =  (Spinner) findViewById(R.id.aptType);
        final List<String> aptTypeList = new ArrayList<String>();
        aptTypeList.add("Studio Apartment");
        aptTypeList.add("1 BHK - Unfurnished");
        aptTypeList.add("1 BHK - Furnished");
        aptTypeList.add("2 BHK - Furnished");
        aptTypeList.add("4 BHK - Furnished");
        ArrayAdapter<String> aptAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,aptTypeList);
        apt.setAdapter(aptAdapter);

        final RadioGroup location = (RadioGroup) findViewById(R.id.location);

        final TextView showRent = (TextView) findViewById(R.id.showProgress);

        final SeekBar rentShare = (SeekBar) findViewById(R.id.rentShare);
        rent = rentShare.getProgress();
        showRent.setText("$ " + rent.toString());
        rentShare.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rent = rentShare.getProgress();
                showRent.setText("$ "+ rent.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rent = rentShare.getProgress();
                showRent.setText("$ "+ rent.toString());
            }
        });

        final CheckBox wifi = (CheckBox) findViewById(R.id.utilitiesWifi);
        final CheckBox electricity = (CheckBox) findViewById(R.id.utilitiesElectricityBill);
        final CheckBox laundry = (CheckBox) findViewById(R.id.utilitiesLaundry);

        final RadioGroup available = (RadioGroup) findViewById(R.id.availibility);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        final RadioGroup gender = (RadioGroup) findViewById(R.id.gender);

        final EditText mobile = (EditText) findViewById(R.id.contactDetails);

        final EditText description = (EditText) findViewById(R.id.otherDetails);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilities.clear();
                final String dateCreated = DateFormat.getDateTimeInstance().format(new Date());
                db = FirebaseDatabase.getInstance().getReference("Advertisement");
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        DatabaseReference pushAd = db.push();
                        adID = pushAd.getKey();

                        final String adType = ad.getSelectedItem().toString();

                        final String aptType = apt.getSelectedItem().toString();

                        Integer locationID = location.getCheckedRadioButtonId();
                        RadioButton setLocation = (RadioButton) findViewById(locationID);
                        final String location = setLocation.getText().toString();

                        final String rentShare = rent.toString();

                        if(wifi.isChecked())
                            utilities.add(wifi.getText().toString());
                        if(electricity.isChecked())
                            utilities.add(electricity.getText().toString());
                        if(laundry.isChecked())
                            utilities.add(laundry.getText().toString());

                        Integer availableID = available.getCheckedRadioButtonId();
                        RadioButton setAvailable = (RadioButton) findViewById(availableID);
                        final String availableStatus = setLocation.getText().toString();

                        Integer year = datePicker.getYear();
                        Integer month = datePicker.getMonth();
                        Integer day = datePicker.getDayOfMonth();

                        final String date = month.toString() + " " + day.toString() + " " + year.toString();

                        Integer genderID = gender.getCheckedRadioButtonId();
                        RadioButton setGender = (RadioButton) findViewById(genderID);
                        final String genderStatus = setGender.getText().toString();

                        final String contactDetails = mobile.getText().toString();

                        final String additionalDetails = description.getText().toString();

                        Advertisement ad = new Advertisement(adID,netID,dateCreated,adType,aptType,location,rentShare,utilities,availableStatus,date,genderStatus,contactDetails,additionalDetails);
                        db.child(adID).setValue(ad);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        Intent StudentHome = new Intent(getApplicationContext(), StudentHome.class);
        startActivity(StudentHome);
    }
}
