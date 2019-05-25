package com.gratus.userapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gratus.userapp.R;
import com.gratus.userapp.activity.Intro.SplashScreenActivity;
import com.gratus.userapp.activity.Profile.Customer_Profile_Edit_Activity;
import com.gratus.userapp.adapter.CustomerProfileAdapter;
import com.gratus.userapp.model.CustomerAddress;
import com.gratus.userapp.model.CustomerRegAdapterModel;
import com.gratus.userapp.model.CustomerRegModel;
import com.gratus.userapp.model.FavoriteRestaurant;

import java.util.ArrayList;

import static com.gratus.userapp.activity.Intro.SplashScreenActivity.MyPREFERENCES;

public class Customer_Profile_Fragment extends Fragment {
    private RelativeLayout exceptionRl;
    private RecyclerView custRecyclerView;
    private TextView signUp;
    private CustomerProfileAdapter mAdapter;
    private SharedPreferences tokenpref;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference;
    private FirebaseUser firebaseUser;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomerRegModel customerRegModel = new CustomerRegModel();
    String email, password, cid;
    private static Context context_main;

    private int PROFILE = 1;
    private int ADDRESS = 2;
    private int LISTITEM = 3;
    private int LOGOUT = 4;
    private int HEADER = 5;
    private int ITEMSEPARATOR = 6;
    private int SEPARATORLINE = 7;

    public Customer_Profile_Fragment() {
    }
    public static Customer_Profile_Fragment newInstance(Context context) {
        Customer_Profile_Fragment fragment = new Customer_Profile_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        context_main = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_profile_no_edit, container, false);
        exceptionRl = view.findViewById(R.id.exceptionRl);
        custRecyclerView = view.findViewById(R.id.custRecyclerView);
        signUp = view.findViewById(R.id.signUpTv);
        tokenpref = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        email = tokenpref.getString(getString(R.string.email_id_pref), null);
        password = tokenpref.getString(getString(R.string.password_id_pref), null);
        cid = tokenpref.getString(getString(R.string.cid_id_pref), null);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerRegModel.setCID(cid);
                customerRegModel.setEmail(email);
                Intent i = new Intent(context_main, Customer_Profile_Edit_Activity.class);
                i.putExtra(getString(R.string.cust_profile_data), customerRegModel);
                startActivity(i);
                ((Activity)context_main).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        getCustomerProfile();
        return view;
    }

    private void getCustomerProfile() {
        customerRegModel = new CustomerRegModel();
        dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.customer_details)).child(cid);
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Name").getValue()!=null) {
                    customerRegModel.setName(dataSnapshot.child("Name").getValue().toString());
                }
                if(dataSnapshot.child("Phone").getValue()!=null) {
                    customerRegModel.setPhone_number(dataSnapshot.child("Phone").getValue().toString());
                }
                if(dataSnapshot.child("eMail").getValue()!=null) {
                    customerRegModel.setEmail(dataSnapshot.child("eMail").getValue().toString());
                }
                if(dataSnapshot.child("profileImageAddress").getValue()!=null) {
                    customerRegModel.setProfile_photo(dataSnapshot.child("profileImageAddress").getValue().toString());
                }
                if(dataSnapshot.child("CID").getValue()!=null) {
                    customerRegModel.setCID(dataSnapshot.child("CID").getValue().toString());
                }
                if(dataSnapshot.child("cust_completed").getValue()!=null) {
                    customerRegModel.setCust_completed((Boolean) dataSnapshot.child("cust_completed").getValue());
                }
                if(dataSnapshot.child("favorite_restaurant").getValue()!=null) {
                    customerRegModel.setFavorite_restaurant((ArrayList<FavoriteRestaurant>) dataSnapshot.child("favorite_restaurant").getValue());
                }
                if(dataSnapshot.child("customerAddresses").getValue()!=null) {
                    customerRegModel.setCustomerAddresses((ArrayList<CustomerAddress>) dataSnapshot.child("customerAddresses").getValue());
                }

                if(customerRegModel==null){
                    exceptionRl.setVisibility(View.VISIBLE);
                    custRecyclerView.setVisibility(View.GONE);
                }
                else{
                    setAdapterList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context_main, getString(R.string.internal_error), Toast.LENGTH_LONG).show();
                getCustomerProfile();
            }
        });
    }

    private void setAdapterList() {
        exceptionRl.setVisibility(View.GONE);
        custRecyclerView.setVisibility(View.VISIBLE);
        ArrayList<CustomerRegAdapterModel> customerRegAdapterModels = new ArrayList<>();
        if(customerRegModel!=null){
            customerRegAdapterModels.add(new CustomerRegAdapterModel(PROFILE,
                    customerRegModel.getName(),customerRegModel.getPhone_number(),
                    customerRegModel.getEmail(),customerRegModel.getProfile_photo()));
            if(customerRegModel.getCustomerAddresses()!=null && customerRegModel.getCustomerAddresses().size()>0) {
                customerRegAdapterModels.add(new CustomerRegAdapterModel(ITEMSEPARATOR));
                customerRegAdapterModels.add(new CustomerRegAdapterModel(HEADER, "Address",
                        customerRegModel.getCustomerAddresses().size()));
                for(int i=0 ;i<customerRegAdapterModels.size();i++) {
                    customerRegAdapterModels.add(new CustomerRegAdapterModel(ADDRESS,
                            customerRegModel.getCustomerAddresses().get(i).getAddress_type(),
                            customerRegModel.getCustomerAddresses().get(i)));
                    customerRegAdapterModels.add(new CustomerRegAdapterModel(SEPARATORLINE));
                }
            }
            if(customerRegModel.getCustomerAddresses()!=null && customerRegModel.getFavorite_restaurant().size()>0) {
                customerRegAdapterModels.add(new CustomerRegAdapterModel(ITEMSEPARATOR));
                customerRegAdapterModels.add(new CustomerRegAdapterModel(LISTITEM, "Favorite",
                        customerRegModel.getFavorite_restaurant()));
            }
            customerRegAdapterModels.add(new CustomerRegAdapterModel(ITEMSEPARATOR));
            customerRegAdapterModels.add(new CustomerRegAdapterModel(LOGOUT));
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new CustomerProfileAdapter(customerRegAdapterModels,context_main);
        custRecyclerView.setLayoutManager(mLayoutManager);
        custRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCustomerProfile();
    }
}
