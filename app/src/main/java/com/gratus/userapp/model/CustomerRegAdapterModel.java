package com.gratus.userapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerRegAdapterModel implements Serializable {
private Integer layoutType;
private String name;
private String phone;
private String email;
private String photourl;
private String header;
private Integer size;
private String addressType;
private CustomerAddress address;
private ArrayList<FavoriteRestaurant> favoriteRestaurant =  new ArrayList<>();
private String text;

    public CustomerRegAdapterModel() {
    }

    public CustomerRegAdapterModel(Integer layoutType, String name, String phone, String email, String photourl) {
        this.layoutType = layoutType;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.photourl = photourl;
    }

    public CustomerRegAdapterModel(Integer layoutType, String header, Integer size) {
        this.layoutType = layoutType;
        this.header = header;
        this.size = size;
    }

    public CustomerRegAdapterModel(Integer layoutType, String addressType, CustomerAddress address) {
        this.layoutType = layoutType;
        this.addressType = addressType;
        this.address = address;
    }

    public CustomerRegAdapterModel(Integer layoutType,String text) {
        this.layoutType = layoutType;
        this.text = text;
    }
    public CustomerRegAdapterModel(Integer layoutType,String text,ArrayList<FavoriteRestaurant> favoriteRestaurant) {
        this.layoutType = layoutType;
        this.text = text;
        this.favoriteRestaurant = favoriteRestaurant;
    }

    public CustomerRegAdapterModel(Integer layoutType) {
        this.layoutType = layoutType;
    }

    public Integer getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(Integer layoutType) {
        this.layoutType = layoutType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public CustomerAddress getAddress() {
        return address;
    }

    public void setAddress(CustomerAddress address) {
        this.address = address;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<FavoriteRestaurant> getFavoriteRestaurant() {
        return favoriteRestaurant;
    }

    public void setFavoriteRestaurant(ArrayList<FavoriteRestaurant> favoriteRestaurant) {
        this.favoriteRestaurant = favoriteRestaurant;
    }
}
