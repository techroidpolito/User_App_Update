package com.gratus.userapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gratus.userapp.R;

public class TwoFragment extends Fragment {

    public TwoFragment() {
    }
    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mainpage, container, false);
        return view;
    }
}
