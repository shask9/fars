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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvertisementAdapter  extends ArrayAdapter<Advertisement>  {
    private final Context context;
    private ArrayList<Advertisement> advertisements;
    public AdvertisementAdapter(Context context, int textViewResourceId, ArrayList<Advertisement> advertisements) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_view_advertisements_list_template,null);
        }
        Advertisement advertisement = advertisements.get(position);
        if(advertisement != null) {
            // Lookup view for data population
            final TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView adType = (TextView) convertView.findViewById(R.id.adType);
            TextView apType = (TextView) convertView.findViewById(R.id.apType);
            TextView location = (TextView) convertView.findViewById(R.id.location);
            TextView gender = (TextView) convertView.findViewById(R.id.gender);
            TextView utilities = (TextView) convertView.findViewById(R.id.utilities);
            TextView availability = (TextView) convertView.findViewById(R.id.availability);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            final Button block = (Button) convertView.findViewById(R.id.block);
            final Button unBlock = (Button) convertView.findViewById(R.id.unBlock);
            block.setTag(position);
            unBlock.setTag(position);
            // Populate the data into the template view using the data object
            name.setText("Name: "+advertisement.studentName);
            adType.setText("AdType: "+advertisement.adType);
            apType.setText("ApType: "+advertisement.aptType);
            location.setText("Location: "+advertisement.aptLoc);
            gender.setText("Gender: "+advertisement.gender);
            utilities.setText("Utilities: "+advertisement.utilities);
            availability.setText("Availability: "+advertisement.availability);
            description.setText("Description: "+advertisement.additionalDetails);


            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    Advertisement advertisement = getItem(position);
                    DatabaseReference updateAdvertisementStatus = FirebaseDatabase.getInstance().getReference("Advertisements/"+advertisement.advertisementID);
                    Map<String, Object> updateStatus = new HashMap<String, Object>();
                    updateStatus.put("reportedStatus","Blocked");
                    updateAdvertisementStatus.updateChildren(updateStatus);
                }
            });

            unBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    Advertisement advertisement = getItem(position);
                    DatabaseReference updateAdvertisementStatus = FirebaseDatabase.getInstance().getReference("Advertisements/"+advertisement.advertisementID);
                    Map<String, Object> updateStatus = new HashMap<String, Object>();
                    updateStatus.put("reportedStatus","Active");
                    updateAdvertisementStatus.updateChildren(updateStatus);
                }
            });
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
