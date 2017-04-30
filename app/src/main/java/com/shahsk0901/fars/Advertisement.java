package com.shahsk0901.fars;

import java.util.ArrayList;
import java.util.List;

public class Advertisement {
    public String advertisementID;
    public String netID;
    public String dateCreated;
    public String adType;
    public String aptType;
    public String aptLoc;
    public String rentShare;
    public List<String> utilities = new ArrayList<>();
    public String availability;
    public String dateAvailable;

    Advertisement() {

    }
    Advertisement(String advertisementID,String netID,String dateCreated, String adType, String aptType,String aptLoc,String rentShare,List<String> utilities, String availability, String dateAvailable) {
        this.advertisementID = advertisementID;
        this.netID = netID;
        this.dateCreated = dateCreated;
        this.adType = adType;
        this.aptType = aptType;
        this.aptLoc = aptLoc;
        this.rentShare = rentShare;
        this.utilities = utilities;
        this.availability = availability;
        this.dateAvailable = dateAvailable;
    }
}
