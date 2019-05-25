package com.gratus.userapp.activity.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;



import com.gratus.userapp.Fragment.FourFragment;
import com.gratus.userapp.Fragment.Customer_Profile_Fragment;
import com.gratus.userapp.Fragment.ThreeFragment;
import com.gratus.userapp.Fragment.TwoFragment;
import com.gratus.userapp.R;

public class MainPageActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().hide();
        }
        context = MainPageActivity.this;
        Fragment fragment;
        fragment = new FourFragment();
        loadFragment(fragment);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nearby:
                    fragment = new FourFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.dashboard:
                    fragment = new TwoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.cart:
                    fragment = new ThreeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.account:
                    Customer_Profile_Fragment customer_profile_fragment = Customer_Profile_Fragment.newInstance(context);
                    loadFragment(customer_profile_fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
