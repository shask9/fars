package com.shahsk0901.fars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shahsk0901.fars.R;

/*public class Report_Ad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__ad);
        final Spinner Reason_report = (Spinner) findViewById(R.id.Report_reason);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Report_reason,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Reason_report.setAdapter(adapter);
        final EditText comments = (EditText) findViewById(R.id.editText6);
        final Button submit = (Button) findViewById(R.id.button17);
        final Button cancel = (Button) findViewById(R.id.button18);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updateAdvertisementStatus = FirebaseDatabase.getInstance().getReference("Advertisements/"+advertisement.AdvertisementID);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
*/