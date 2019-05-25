package com.gratus.userapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerAddress implements Parcelable {
    private String address_type;
    private String address_location;
    private String address_house_flat_no;
    private String address_landmark;
    private String lat;
    private String lon;

    public CustomerAddress(String address_type, String address_location, String address_house_flat_no, String address_landmark, String lat, String lon) {
        this.address_type = address_type;
        this.address_location = address_location;
        this.address_house_flat_no = address_house_flat_no;
        this.address_landmark = address_landmark;
        this.lat = lat;
        this.lon = lon;
    }

    protected CustomerAddress(Parcel in) {
        address_type = in.readString();
        address_location = in.readString();
        address_house_flat_no = in.readString();
        address_landmark = in.readString();
        lat = in.readString();
        lon = in.readString();
    }

    public static final Creator<CustomerAddress> CREATOR = new Creator<CustomerAddress>() {
        @Override
        public CustomerAddress createFromParcel(Parcel in) {
            return new CustomerAddress(in);
        }

        @Override
        public CustomerAddress[] newArray(int size) {
            return new CustomerAddress[size];
        }
    };

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public String getAddress_location() {
        return address_location;
    }

    public void setAddress_location(String address_location) {
        this.address_location = address_location;
    }

    public String getAddress_house_flat_no() {
        return address_house_flat_no;
    }

    public void setAddress_house_flat_no(String address_house_flat_no) {
        this.address_house_flat_no = address_house_flat_no;
    }

    public String getAddress_landmark() {
        return address_landmark;
    }

    public void setAddress_landmark(String address_landmark) {
        this.address_landmark = address_landmark;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public CustomerAddress() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address_type);
        dest.writeString(address_location);
        dest.writeString(address_house_flat_no);
        dest.writeString(address_landmark);
        dest.writeString(lat);
        dest.writeString(lon);
    }
}
