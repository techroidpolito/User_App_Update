package com.gratus.userapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CustomerRegModel implements Parcelable {

    private String CID;
    private String name;
    private String phone_number;
    private String email;
    private String profile_photo;
    private ArrayList<FavoriteRestaurant> favorite_restaurant;
    private ArrayList<CustomerAddress> customerAddresses;
    private Boolean cust_completed = false;

    public CustomerRegModel(String CID, String name, String phone_number, String email, String profile_photo, ArrayList<FavoriteRestaurant> favorite_restaurant, ArrayList<CustomerAddress> customerAddresses, Boolean cust_completed) {
        this.CID = CID;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.profile_photo = profile_photo;
        this.favorite_restaurant = favorite_restaurant;
        this.customerAddresses = customerAddresses;
        this.cust_completed = cust_completed;
    }

    public CustomerRegModel() {
    }

    public CustomerRegModel(String CID, String email, Boolean cust_completed) {
        this.CID = CID;
        this.email = email;
        this.cust_completed = cust_completed;
    }


    protected CustomerRegModel(Parcel in) {
        CID = in.readString();
        name = in.readString();
        phone_number = in.readString();
        email = in.readString();
        profile_photo = in.readString();
        favorite_restaurant = in.createTypedArrayList(FavoriteRestaurant.CREATOR);
        customerAddresses = in.createTypedArrayList(CustomerAddress.CREATOR);
        byte tmpCust_completed = in.readByte();
        cust_completed = tmpCust_completed == 0 ? null : tmpCust_completed == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CID);
        dest.writeString(name);
        dest.writeString(phone_number);
        dest.writeString(email);
        dest.writeString(profile_photo);
        dest.writeTypedList(favorite_restaurant);
        dest.writeTypedList(customerAddresses);
        dest.writeByte((byte) (cust_completed == null ? 0 : cust_completed ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerRegModel> CREATOR = new Creator<CustomerRegModel>() {
        @Override
        public CustomerRegModel createFromParcel(Parcel in) {
            return new CustomerRegModel(in);
        }

        @Override
        public CustomerRegModel[] newArray(int size) {
            return new CustomerRegModel[size];
        }
    };

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public ArrayList<FavoriteRestaurant> getFavorite_restaurant() {
        return favorite_restaurant;
    }

    public void setFavorite_restaurant(ArrayList<FavoriteRestaurant> favorite_restaurant) {
        this.favorite_restaurant = favorite_restaurant;
    }

    public ArrayList<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public void setCustomerAddresses(ArrayList<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    public Boolean getCust_completed() {
        return cust_completed;
    }

    public void setCust_completed(Boolean cust_completed) {
        this.cust_completed = cust_completed;
    }
}
