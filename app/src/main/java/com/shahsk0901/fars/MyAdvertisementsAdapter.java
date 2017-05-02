package com.shahsk0901.fars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shahsk0901.fars.Advertisement;
import com.shahsk0901.fars.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdvertisementsAdapter  extends ArrayAdapter<Advertisement>  {
    private final Context context;
    private ArrayList<Advertisement> advertisements;
    public MyAdvertisementsAdapter(Context context, int textViewResourceId, ArrayList<Advertisement> advertisements) {
        super(context, textViewResourceId, advertisements);
        this.context = context;
        this.advertisements = advertisements;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //Student student = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_my_advertisements_list_template,null);
        }
        Advertisement advertisement = advertisements.get(position);
        if(advertisement != null) {
            // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.)
            TextView adType = (TextView) convertView.findViewById(R.id.adType);
            TextView apType = (TextView) convertView.findViewById(R.id.apType);
            TextView location = (TextView) convertView.findViewById(R.id.location);
            TextView gender = (TextView) convertView.findViewById(R.id.gender);
            TextView utilities = (TextView) convertView.findViewById(R.id.utilities);
            TextView availability = (TextView) convertView.findViewById(R.id.availability);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            final Button edit = (Button) convertView.findViewById(R.id.edit);
            final Button delete = (Button) convertView.findViewById(R.id.delete);
            edit.setTag(position);
            delete.setTag(position);
            // Populate the data into the template view using the data object

            adType.setText("AdType: "+advertisement.adType);
            apType.setText("ApType: "+advertisement.aptType);
            location.setText("Location: "+advertisement.aptLoc);
            gender.setText("Gender: "+advertisement.gender);
            utilities.setText("Utilities: "+advertisement.utilities);
            availability.setText("Availability: "+advertisement.availability);
            description.setText("Description: "+advertisement.additionalDetails);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"edit",Toast.LENGTH_SHORT).show();
                    int position = (Integer) v.getTag();
                    Advertisement advertisement = getItem(position);
                    DatabaseReference updateAdvertisementStatus = FirebaseDatabase.getInstance().getReference("Advertisement/"+advertisement.advertisementID);
                    Map<String, Object> updateStatus = new HashMap<String, Object>();
                    /*updateStatus.put("reportedStatus","Blocked");
                    updateAdvertisementStatus.updateChildren(updateStatus);*/
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    Advertisement advertisement = getItem(position);
                    DatabaseReference updateAdvertisementStatus = FirebaseDatabase.getInstance().getReference("Advertisement/"+advertisement.advertisementID);
                    Map<String, Object> reportedStatus = new HashMap<String, Object>();
                    reportedStatus.put("reportedStatus","Blocked");
                    updateAdvertisementStatus.updateChildren(reportedStatus);
                    Toast.makeText(getContext(),"Advertisement Deleted",Toast.LENGTH_SHORT).show();
                }
            });
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
