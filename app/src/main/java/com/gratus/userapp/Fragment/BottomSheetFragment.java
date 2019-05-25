package com.gratus.userapp.Fragment;

import android.os.Bundle;

import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gratus.userapp.R;
import com.gratus.userapp.util.CameraInterface;
import com.gratus.userapp.util.PhotoInterface;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    LinearLayout cameraLL,photoLL;
    CameraInterface cameraInterface;
    PhotoInterface photoInterface;
    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public void setPhotoInterface(CameraInterface cameraInterface) {
        this.cameraInterface = cameraInterface;
    }
    public void setCameraInterfaceC(PhotoInterface photoInterface) {
        this.photoInterface = photoInterface;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        cameraLL = v.findViewById(R.id.cameraLL);
        photoLL = v.findViewById(R.id.photoLL);

        cameraLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraInterface.cameraClicked();
            }
        });
        photoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoInterface.photoClicked();
            }
        });
        return v;
    }
}
