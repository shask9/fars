package com.shahsk0901.fars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdvertisementsActivity extends AppCompatActivity {

    private DatabaseReference db;
    private ListView lv;
    ArrayList<Advertisement> myAdvertisements = new ArrayList<Advertisement>();
    MyAdvertisementsAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisements);

        final String loginID = setActionBar();

        lv = (ListView) findViewById(R.id.list);
        adapter = new MyAdvertisementsAdapter(this,R.layout.activity_my_advertisements_list_template,myAdvertisements);
        lv.setAdapter(adapter);

        db = FirebaseDatabase.getInstance().getReference("Advertisement");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Advertisement advertisement = dataSnapshot.getValue(Advertisement.class);
                if(loginID.equals(advertisement.netID) && advertisement.reportedStatus.equals("Active")) {
                    adapter.add(advertisement);
                } else if(!loginID.equals(advertisement.netID)) {
                    
                } else {
                    Toast.makeText(getApplicationContext(),"No advertisements posted",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public String setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);

        SharedPreferences getSharedData = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
        String loginID = getSharedData.getString("loginID",null);
        return loginID;
    }

    @Override
    public void onBackPressed() {
        Intent StudentHome = new Intent(getApplicationContext(), StudentHome.class);
        startActivity(StudentHome);
    }
}










/*private int i1=0;
        String[] studentName = new String[1000];
        String[] advertisementID = new String[1000];
        String[] dateCreated = new String[1000];
        String[] adType = new String[1000];
        String[] aptType = new String[1000];
        String[] aptLoc = new String[1000];
        String[] gender = new String[1000];
        List<String>[] utilities = new List[1000];
        String[] availability = new String[1000];
        String[] rentShare = new String[1000];
        String[] dateAvailable = new String[1000];
        String[] reportedStatus = new String[1000];
        String[] description = new String[1000];

        Advertisement ads;
        DatabaseReference dbr;
        Bundle bun;
        LinearLayout ll;
        RelativeLayout.LayoutParams lp,lp1;
        String ad;
        Button[] delete = new Button[1000];
        Button[] edit = new Button[1000];
        TextView[] tx = new TextView[1000];
        int i=0;
        int ind=0;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_advertisements);
final String netID = setActionBar();
        SharedPreferences getSharedData = getSharedPreferences("LOGIN_ID",MODE_PRIVATE);
final String name = getSharedData.getString("studentName",null);
final String mobile = getSharedData.getString("mobile",null);
        for(int j=0;j<1000;j++){
        edit[j] = new Button(this);
        tx[j] = new TextView(this);
        delete[j] = new Button(this);}
        bun = getIntent().getExtras();
        lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        ll = (LinearLayout) findViewById(R.id.activity_my_advertisements);
        dbr = FirebaseDatabase.getInstance().getReference("Advertisement");
        dbr.addValueEventListener(new ValueEventListener(){

@Override
public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot dss1:dataSnapshot.getChildren()) {
        for (DataSnapshot dss : dss1.getChildren()) {
        ads = dss.getValue(Advertisement.class);
        Log.d("tag", dss.getKey());
        if (ads.studentName.equals(name) && (ads.reportedStatus).equals("Active")) {
        if (ads.adType.equals("Apartment")) {
        ad = "Posted on " + ads.dateCreated + System.getProperty("line.separator");
        ad += "House for Rent ";
        ad += ads.aptType + " type ";
        ad += ads.aptLoc + " " + ads.gender + " only" + " " + ads.utilities + " included" + " " + ads.availability + " availability" + " Available from " + ads.dateAvailable;
        ad += " Rent Share: $" + ads.rentShare + " Phone No:" + " " + mobile + " " + "Extra Description:" + ads.additionalDetails + System.getProperty("line.separator");
        tx[i1].setId(i1);
        tx[i1].setText(ad);
        Log.d("textview" + i, Integer.toString(tx[i1].getId()));
        tx[i1].setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));

        if (tx[i1].getParent() != null) {
        ((ViewGroup) tx[i1].getParent()).removeView(tx[i1]);
        }
        studentName[i1] = ads.studentName;
        advertisementID[i1] = ads.advertisementID;
        dateCreated[i1] = ads.dateCreated;
        adType[i1] = ads.adType;
        aptType[i1] = ads.aptType;
        aptLoc[i1] = ads.aptLoc;
        gender[i1] = ads.gender;
        utilities[i1] = ads.utilities;
        availability[i1] = ads.availability;
        rentShare[i1] = ads.rentShare;
        reportedStatus[i1] = ads.reportedStatus;
        dateAvailable[i1] = ads.dateAvailable;
        description[i1] = ads.additionalDetails;
        ll.addView(tx[i1]);
        edit[i1].setId(i1);
        edit[i1].setText("Edit");
        edit[i1].setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        edit[i1].setTag(i1);

        Log.d("button" + i1, Integer.toString(edit[i1].getId()));
        if (edit[i1].getParent() != null) {
        ((ViewGroup) edit[i1].getParent()).removeView(edit[i1]);
        }
        edit[i1].setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Toast.makeText(getApplicationContext(),""+i1,Toast.LENGTH_SHORT).show();
        }
        });
        ll.addView(edit[i1]);
        if (delete[i1].getParent() != null) {
        ((ViewGroup) delete[i1].getParent()).removeView(delete[i1]);
        }
        delete[i1].setId(i1);
        delete[i1].setText("Delete");
        delete[i1].setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        ll.addView(delete[i1]);


        } else if (((ads.adType).equals("Roommate")) && ((ads.reportedStatus).equals("Active"))) {
        ad = "Posted on " + ads.dateCreated + System.getProperty("line.separator");
        ad += "Roommate Required";
        ad += ads.aptType + " type";
        ad += ads.aptLoc + " " + ads.gender + " only" + " " + ads.utilities + " included" + " " + ads.availability + " availability" + " Available from " + ads.dateAvailable;
        ad += " Rent Share: $" + ads.rentShare + " Phone No:" + " " + bun.getLong("Phone No")+" Extra Description:"+ads.additionalDetails + System.getProperty("line.separator");
        tx[i1].setId(i1);
        tx[i1].setText(ad);
        tx[i1].setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));

        if (tx[i1].getParent() != null) {
        ((ViewGroup) tx[i1].getParent()).removeView(tx[i1]);
        }


        ll.addView(tx[i1]);
        edit[i1].setId(i1);
        edit[i1].setText("Edit");
        edit[i1].setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        if (edit[i1].getParent() != null) {
        ((ViewGroup) edit[i1].getParent()).removeView(edit[i1]);
        }
        edit[i1].setTag(i1);
        edit[i1].setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Toast.makeText(getApplicationContext(),""+i1,Toast.LENGTH_SHORT).show();
        }
        });
        ll.addView(edit[i1]);
        if (delete[i1].getParent() != null) {
        ((ViewGroup) delete[i1].getParent()).removeView(delete[i1]);
        }
        delete[i1].setId(i1);
        delete[i1].setText("Delete");
        delete[i1].setLayoutParams(new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        ll.addView(delete[i1]);
        studentName[i1] = ads.studentName;
        advertisementID[i1] = ads.advertisementID;
        dateCreated[i1] = ads.dateCreated;
        adType[i1] = ads.adType;
        aptType[i1] = ads.aptType;
        aptLoc[i1] = ads.aptLoc;
        gender[i1] = ads.gender;
        utilities[i1] = ads.utilities;
        availability[i1] = ads.availability;
        rentShare[i1] = ads.rentShare;
        reportedStatus[i1] = ads.reportedStatus;
        dateAvailable[i1] = ads.dateAvailable;
        description[i1] = ads.additionalDetails;

        Log.d("textview" + i, Integer.toString(tx[i1].getId()));
        Log.d("button" + i, Integer.toString(edit[i1].getId()));
        }
        i1++;
        }
        }
        }
        i=i1;
        }

@Override
public void onCancelled(DatabaseError databaseError) { }
        });
        /*edit[0].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("studentName",studentName[0]);
                bun1.putString("advertisementID",advertisementID[0]);
                bun1.putString("dateCreated",Date[0]);
                bun1.putString("adType",AdType[0]);
                bun1.putString("aptType",ApType[0]);
                bun1.putString("aptLoc",Location[0]);
                bun1.putString("gender",Gender[0]);
                bun1.putString("utilities",Utilities[0]);
                bun1.putString("availability",Availability[0]);
                bun1.putInt("rentShare",Rent_Share[0]);
                bun1.putString("dateAvailable",Date_av[0]);
                bun1.putString("reportedStatus",reportedStatus[0]);
                bun1.putString("additionalDetails",description[0]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        /*edit[1].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[1]);
                bun1.putString("AdvertisementID",AdvertisementID[1]);
                bun1.putString("Date",Date[1]);
                bun1.putString("AdType",AdType[1]);
                bun1.putString("ApType",ApType[1]);
                bun1.putString("Location",Location[1]);
                bun1.putString("Gender",Gender[1]);
                bun1.putString("Utilities",Utilities[1]);
                bun1.putString("Availability",Availability[1]);
                bun1.putInt("Rent Share",Rent_Share[1]);
                bun1.putString("Date_av",Date_av[1]);
                bun1.putString("status",status[1]);
                bun1.putString("Description",Description[1]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[2].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[2]);
                bun1.putString("AdvertisementID",AdvertisementID[2]);
                bun1.putString("Date",Date[2]);
                bun1.putString("AdType",AdType[2]);
                bun1.putString("ApType",ApType[2]);
                bun1.putString("Location",Location[2]);
                bun1.putString("Gender",Gender[2]);
                bun1.putString("Utilities",Utilities[2]);
                bun1.putString("Availability",Availability[2]);
                bun1.putInt("Rent Share",Rent_Share[2]);
                bun1.putString("Date_av",Date_av[2]);
                bun1.putString("status",status[2]);
                bun1.putString("Description",Description[2]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[3].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[3]);
                bun1.putString("AdvertisementID",AdvertisementID[3]);
                bun1.putString("Date",Date[3]);
                bun1.putString("AdType",AdType[3]);
                bun1.putString("ApType",ApType[3]);
                bun1.putString("Location",Location[3]);
                bun1.putString("Gender",Gender[3]);
                bun1.putString("Utilities",Utilities[3]);
                bun1.putString("Availability",Availability[3]);
                bun1.putInt("Rent Share",Rent_Share[3]);
                bun1.putString("Date_av",Date_av[3]);
                bun1.putString("status",status[3]);
                bun1.putString("Description",Description[3]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[4].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[4]);
                bun1.putString("AdvertisementID",AdvertisementID[4]);
                bun1.putString("Date",Date[4]);
                bun1.putString("AdType",AdType[4]);
                bun1.putString("ApType",ApType[4]);
                bun1.putString("Location",Location[4]);
                bun1.putString("Gender",Gender[4]);
                bun1.putString("Utilities",Utilities[4]);
                bun1.putString("Availability",Availability[4]);
                bun1.putInt("Rent Share",Rent_Share[4]);
                bun1.putString("Date_av",Date_av[4]);
                bun1.putString("status",status[4]);
                bun1.putString("Description",Description[4]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[5].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[5]);
                bun1.putString("AdvertisementID",AdvertisementID[5]);
                bun1.putString("Date",Date[5]);
                bun1.putString("AdType",AdType[5]);
                bun1.putString("ApType",ApType[5]);
                bun1.putString("Location",Location[5]);
                bun1.putString("Gender",Gender[5]);
                bun1.putString("Utilities",Utilities[5]);
                bun1.putString("Availability",Availability[5]);
                bun1.putInt("Rent Share",Rent_Share[5]);
                bun1.putString("Date_av",Date_av[5]);
                bun1.putString("status",status[5]);
                bun1.putString("Description",Description[5]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[6].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[6]);
                bun1.putString("AdvertisementID",AdvertisementID[6]);
                bun1.putString("Date",Date[6]);
                bun1.putString("AdType",AdType[6]);
                bun1.putString("ApType",ApType[6]);
                bun1.putString("Location",Location[6]);
                bun1.putString("Gender",Gender[6]);
                bun1.putString("Utilities",Utilities[6]);
                bun1.putString("Availability",Availability[6]);
                bun1.putInt("Rent Share",Rent_Share[6]);
                bun1.putString("Date_av",Date_av[6]);
                bun1.putString("status",status[6]);
                bun1.putString("Description",Description[6]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[7].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[7]);
                bun1.putString("AdvertisementID",AdvertisementID[7]);
                bun1.putString("Date",Date[7]);
                bun1.putString("AdType",AdType[7]);
                bun1.putString("ApType",ApType[7]);
                bun1.putString("Location",Location[7]);
                bun1.putString("Gender",Gender[7]);
                bun1.putString("Utilities",Utilities[7]);
                bun1.putString("Availability",Availability[7]);
                bun1.putInt("Rent Share",Rent_Share[7]);
                bun1.putString("Date_av",Date_av[7]);
                bun1.putString("status",status[7]);
                bun1.putString("Description",Description[7]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[8].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[8]);
                bun1.putString("AdvertisementID",AdvertisementID[8]);
                bun1.putString("Date",Date[8]);
                bun1.putString("AdType",AdType[8]);
                bun1.putString("ApType",ApType[8]);
                bun1.putString("Location",Location[8]);
                bun1.putString("Gender",Gender[8]);
                bun1.putString("Utilities",Utilities[8]);
                bun1.putString("Availability",Availability[8]);
                bun1.putInt("Rent Share",Rent_Share[8]);
                bun1.putString("Date_av",Date_av[8]);
                bun1.putString("status",status[8]);
                bun1.putString("Description",Description[8]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        edit[9].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Bundle bun1 = new Bundle();
                bun1.putString("Name",Name[9]);
                bun1.putString("AdvertisementID",AdvertisementID[9]);
                bun1.putString("Date",Date[9]);
                bun1.putString("AdType",AdType[9]);
                bun1.putString("ApType",ApType[9]);
                bun1.putString("Location",Location[9]);
                bun1.putString("Gender",Gender[9]);
                bun1.putString("Utilities",Utilities[9]);
                bun1.putString("Availability",Availability[9]);
                bun1.putInt("Rent Share",Rent_Share[9]);
                bun1.putString("Date_av",Date_av[9]);
                bun1.putString("status",status[9]);
                bun1.putString("Description",Description[9]);
                Intent i = new Intent(getApplicationContext(),Edit_ad.class);
                i.putExtra("BunUser",bun);
                i.putExtra("Bunad",bun1);
                startActivity(i);
            }
        });
        delete[0].setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View view){
        FirebaseDatabase fbd = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fbd.getReference("/Advertisements/"+dateCreated[0]+"/"+advertisementID[0]+"/status");
        dbr.setValue("Blocked");
        Intent i = new Intent(getApplicationContext(), MyAdvertisementsActivity.class);
        i.putExtras(bun);
        startActivity(i);

        }
        });
        /*delete[1].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[1]+"/"+AdvertisementID[1]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[2].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[2]+"/"+AdvertisementID[2]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[3].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[3]+"/"+AdvertisementID[3]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[4].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[4]+"/"+AdvertisementID[4]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[5].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[5]+"/"+AdvertisementID[5]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[6].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[6]+"/"+AdvertisementID[6]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[7].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[7]+"/"+AdvertisementID[7]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[8].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[8]+"/"+AdvertisementID[8]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });
        delete[9].setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                FirebaseDatabase fbd = FirebaseDatabase.getInstance();
                DatabaseReference dbr = fbd.getReference("/Advertisements/"+Date[9]+"/"+AdvertisementID[9]+"/status");
                dbr.setValue("Blocked");
                Intent i = new Intent(getApplicationContext(), My_ad.class);
                i.putExtras(bun);
                startActivity(i);

            }
        });*/

