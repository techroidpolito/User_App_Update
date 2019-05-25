package com.gratus.userapp.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gratus.userapp.GlideApp;
import com.gratus.userapp.R;
import com.gratus.userapp.model.CustomerRegAdapterModel;

import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;


public class CustomerProfileAdapter extends RecyclerView.Adapter {

    private int PROFILE = 1;
    private int ADDRESS = 2;
    private int LISTITEM = 3;
    private int LOGOUT = 4;
    private int HEADER = 5;
    private int ITEMSEPARATOR = 6;
    private int SEPARATORLINE = 7;

    private ArrayList<CustomerRegAdapterModel> customerRegAdapterModels;

    private AddressItemViewHolder addressItemViewHolder;
    private ProfileItemViewHolder profileItemViewHolder;
    private ListItemViewHolder listItemViewHolder;
    private LogOutItemViewHolder logOutItemViewHolder;
    private SeparatorItemViewHolder separatorItemViewHolder;
    private SeparatorLineItemViewHolder separatorLineItemViewHolder;
    private HeaderItemViewHolder headerItemViewHolder;

    private Context context;

    public CustomerProfileAdapter(ArrayList<CustomerRegAdapterModel> customerRegAdapterModels, Context context_main) {
        this.customerRegAdapterModels = customerRegAdapterModels;
        this.context = context_main;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == PROFILE){
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.customer_profile_name_img_item, null);
            return new ProfileItemViewHolder(itemView);
        }
        if (viewType == ADDRESS){
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.address_with_edit_delete_item, null);
            return new AddressItemViewHolder(itemView);
        }
        else if (viewType == LISTITEM){
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.text_item, null);
            return new ListItemViewHolder(itemView);
        }
        else if (viewType == LOGOUT){
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.logout_item, null);
            return new LogOutItemViewHolder(itemView);
        }
        else if (viewType == HEADER){
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.header_more_item, null);
            return new HeaderItemViewHolder(itemView);
        }
        else if (viewType == ITEMSEPARATOR){
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.separator_view_item, null);
            return new SeparatorItemViewHolder(itemView);
        }
        else if (viewType == SEPARATORLINE){
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.separator_line_item, null);
            return new SeparatorLineItemViewHolder(itemView);
        }
        else{
            itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.separator_line_item, null);
            return new SeparatorLineItemViewHolder(itemView);
        }
    }

    private class ProfileItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,phoneTv,emailTv;
        CircleImageView profileImg;
        ProfileItemViewHolder(View v) {
            super(v);
            nameTv = v.findViewById(R.id.nameTv);
            phoneTv = v.findViewById(R.id.phoneTv);
            emailTv = v.findViewById(R.id.emailTv);
            profileImg = v.findViewById(R.id.profileImg);
        }
    }

    private class AddressItemViewHolder extends RecyclerView.ViewHolder {
        ImageView addressImg;
        TextView addressTypeTv,addressFullTv;
        AddressItemViewHolder(View v) {
            super(v);
            addressImg = v.findViewById(R.id.addressImg);
            addressTypeTv = v.findViewById(R.id.addressTypeTv);
            addressFullTv = v.findViewById(R.id.addressFullTv);
        }
    }

    private class ListItemViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemRl;
        TextView itemTv,arrowTv;
        ListItemViewHolder(View v) {
            super(v);
            itemTv = v.findViewById(R.id.itemTv);
            arrowTv = v.findViewById(R.id.arrowTv);
            itemRl = v.findViewById(R.id.itemRl);
        }
    }

    private class LogOutItemViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout logoutRl;
        LogOutItemViewHolder(View v) {
            super(v);
            logoutRl = v.findViewById(R.id.logoutRl);
        }
    }

    private class HeaderItemViewHolder extends RecyclerView.ViewHolder {
        TextView headerTv,moreTv;
        HeaderItemViewHolder(View v) {
            super(v);
            headerTv = v.findViewById(R.id.headerTv);
            moreTv = v.findViewById(R.id.moreTv);
        }
    }

    private class SeparatorItemViewHolder extends RecyclerView.ViewHolder {
        SeparatorItemViewHolder(View v) {
            super(v);

        }
    }

    private class SeparatorLineItemViewHolder extends RecyclerView.ViewHolder {
        SeparatorLineItemViewHolder(View v) {
            super(v);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CustomerRegAdapterModel customerRegAdapterModel = customerRegAdapterModels.get(position);
        Integer layoutType = customerRegAdapterModel.getLayoutType();
        if(layoutType== PROFILE){
            profileItemViewHolder = (ProfileItemViewHolder) holder;
            profileItemViewHolder.nameTv.setText(customerRegAdapterModel.getName());
            profileItemViewHolder.emailTv.setText(customerRegAdapterModel.getEmail());
            profileItemViewHolder.phoneTv.setText(customerRegAdapterModel.getPhone());
            if(customerRegAdapterModel.getPhotourl()!=null){
                StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl("gs://userapp-295ff.appspot.com/customer_profile/"+customerRegAdapterModel.getPhotourl());
                    GlideApp.with(context).load(ref)//"https://firebasestorage.googleapis.com/v0/b/userapp-295ff.appspot.com/o/customer_profile%2FIMG_20190518_222939.jpg?alt=media&token=1a792fc4-07c3-42e9-8bda-4ef582cf5a26")
                            .into(profileItemViewHolder.profileImg);
                    profileItemViewHolder.profileImg.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));
                }
        }
        if(layoutType== ADDRESS){
            addressItemViewHolder = (AddressItemViewHolder) holder;
            if(customerRegAdapterModel.getAddressType().equals("HOME")){
                addressItemViewHolder.addressImg.setBackgroundResource(R.drawable.home_icon);
            }
            else if(customerRegAdapterModel.getAddressType().equals("WORK")){
                addressItemViewHolder.addressImg.setBackgroundResource(R.drawable.work_icon);
            }
            else{
                addressItemViewHolder.addressImg.setBackgroundResource(R.drawable.other_icon);
            }
            addressItemViewHolder.addressTypeTv.setText(customerRegAdapterModel.getAddressType());
           // addressItemViewHolder.addressFullTv.setText();
        }
        if(layoutType== LISTITEM){
            listItemViewHolder = (ListItemViewHolder) holder;
            listItemViewHolder.itemTv.setText(customerRegAdapterModel.getText());
        }
        if(layoutType== LOGOUT){
            logOutItemViewHolder = (LogOutItemViewHolder) holder;
        }
        if(layoutType== HEADER){
            headerItemViewHolder = (HeaderItemViewHolder) holder;
            headerItemViewHolder.headerTv.setText(customerRegAdapterModel.getHeader());
            if(customerRegAdapterModel.getSize()>2){
                headerItemViewHolder.moreTv.setVisibility(View.VISIBLE);
            }
            else{
                headerItemViewHolder.moreTv.setVisibility(View.GONE);
            }
        }
        if(layoutType== SEPARATORLINE){
            addressItemViewHolder = (AddressItemViewHolder) holder;
        }
        if(layoutType== SEPARATORLINE){
            addressItemViewHolder = (AddressItemViewHolder) holder;
        }

    }

    @Override
    public int getItemCount() {
        return customerRegAdapterModels.size();
    }
    @Override
    public int getItemViewType(int position) {
        return customerRegAdapterModels.get(position).getLayoutType();
    }
}
