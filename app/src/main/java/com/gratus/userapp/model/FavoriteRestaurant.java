package com.gratus.userapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteRestaurant implements Parcelable {
    private String RID;
    private String restaurant_name;

    public FavoriteRestaurant(String RID, String restaurant_name) {
        this.RID = RID;
        this.restaurant_name = restaurant_name;
    }

    public FavoriteRestaurant() {
    }

    protected FavoriteRestaurant(Parcel in) {
        RID = in.readString();
        restaurant_name = in.readString();
    }

    public static final Creator<FavoriteRestaurant> CREATOR = new Creator<FavoriteRestaurant>() {
        @Override
        public FavoriteRestaurant createFromParcel(Parcel in) {
            return new FavoriteRestaurant(in);
        }

        @Override
        public FavoriteRestaurant[] newArray(int size) {
            return new FavoriteRestaurant[size];
        }
    };

    public String getRID() {
        return RID;
    }

    public void setRID(String RID) {
        this.RID = RID;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RID);
        dest.writeString(restaurant_name);
    }
}
