package com.gratus.userapp.activity.Profile;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gratus.userapp.Fragment.BottomSheetFragment;
import com.gratus.userapp.R;
import com.gratus.userapp.activity.ImagePickerActivity;
import com.gratus.userapp.model.CustomerRegModel;
import com.gratus.userapp.util.CameraInterface;
import com.gratus.userapp.util.PhotoInterface;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gratus.userapp.activity.Intro.SplashScreenActivity.MyPREFERENCES;

public class Customer_Profile_Edit_Activity extends AppCompatActivity implements CameraInterface, PhotoInterface {
    private CircleImageView userImg;
    private ImageView photo_userImg;
    private EditText nameEt,mailEt,phoneEt;
    private TextView saveTv;
    private View contextView;
    private CustomerRegModel customerRegModels;
    private Map<String,Object> cust_entries = new HashMap<>();
    private Map<String,Object> cust_dataPlaceholder = new HashMap<>();
    private String profileImg,profileLocation;
    boolean pressed =false;
    private SharedPreferences tokenpref;
    public static final int REQUEST_PROFILE_IMAGE = 100;
    BottomSheetFragment bottomSheetFragment;
    StorageReference storageReference;
    private DatabaseReference dbReference;
    private  FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile_edit);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().hide();
        }
        userImg = findViewById(R.id.userImg);
        photo_userImg = findViewById(R.id.photo_userImg);
        nameEt = findViewById(R.id.nameEt);
        mailEt = findViewById(R.id.mailEt);
        phoneEt = findViewById(R.id.phoneEt);
        saveTv = findViewById(R.id.saveTv);
        storageReference = FirebaseStorage.getInstance().getReference();
        tokenpref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(getIntent().getParcelableExtra(getString(R.string.cust_profile_data))!=null){
            customerRegModels = (CustomerRegModel) getIntent().getParcelableExtra(getString(R.string.cust_profile_data));
            if(customerRegModels.getEmail()!=null && !customerRegModels.getEmail().isEmpty()) {
                mailEt.setText(customerRegModels.getEmail());
            }
            if(customerRegModels.getProfile_photo()!=null && !customerRegModels.getProfile_photo().isEmpty()) {
                loadProfile(customerRegModels.getProfile_photo(),userImg);
                profileImg = customerRegModels.getProfile_photo();
            }
            if(customerRegModels.getName()!=null && !customerRegModels.getName().isEmpty()) {
                nameEt.setText(customerRegModels.getName());
            }
            if(customerRegModels.getPhone_number()!=null && !customerRegModels.getPhone_number().isEmpty()) {
                phoneEt.setText(customerRegModels.getPhone_number());
            }
        }

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSave();
            }
        });

        photo_userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed = true;
                contextView = v;
                showProfileBottomSheetDialogFragment();

            }
        });
        if (savedInstanceState != null) {
            savedInstanceState.getString(getString(R.string.profile), null);
        }
    }

    private void setSave() {
        if(setValidation()){
            if(profileLocation!=null && !profileLocation.isEmpty()) {
                cust_entries.put(getString(R.string.profileImageAddress), profileLocation);
            }
            String email = tokenpref.getString(getString(R.string.email_id_pref), null);
            String password = tokenpref.getString(getString(R.string.password_id_pref), null);
            String cid = tokenpref.getString(getString(R.string.cid_id_pref), null);
            if(!mailEt.getText().toString().trim().equals(email)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.reset_email_title_dialog));
                builder.setMessage(getString(R.string.reset_email_message_dialog));
                String positiveText = getString(R.string.reset_email_positive);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss alert dialog, update preferences with game score and restart play fragment
                                resetauthemail(email,password);
                                dialog.dismiss();
                            }
                        });

                String negativeText = getString(android.R.string.cancel);
                builder.setNegativeButton(negativeText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss dialog, start counter again
                                mailEt.setText(email);
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
// display dialog
                dialog.show();

            }
            else{
                cust_entries.put(getString(R.string.email),email);
                updateCustomerProfile();
            }
        }
    }

    private void updateCustomerProfile() {
        cust_dataPlaceholder.put(customerRegModels.getCID(), getString(R.string.empty_cust_value));
        cust_entries.put(getString(R.string.CID),customerRegModels.getCID()+"");
        cust_entries.put(getString(R.string.name),nameEt.getText().toString().trim());
        cust_entries.put(getString(R.string.phone),phoneEt.getText().toString().trim());
        cust_entries.put(getString(R.string.cust_completed),true);

        dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.customer_details));
        dbReference.updateChildren(cust_dataPlaceholder, (databaseError, databaseReference) -> {

            if(databaseError == null) {
                dbReference.child(customerRegModels.getCID()).updateChildren(cust_entries);
            }else {
                Toast.makeText(getApplicationContext(), getString(R.string.internal_error), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void resetauthemail(String email, String password) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password); // Current Login Credentials \\
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Now change your email address \\
                        //----------------Code for Changing Email Address----------\\
                        user.updateEmail(mailEt.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user = FirebaseAuth.getInstance().getCurrentUser();
                                            tokenpref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = tokenpref.edit();
                                            editor.putString(getString(R.string.email_id_pref), user.getEmail());
                                            editor.commit();
                                            cust_entries.put(getString(R.string.email),user.getEmail());
                                            updateCustomerProfile();
                                        }
                                        else{
                                            Toast.makeText(Customer_Profile_Edit_Activity.this, getString(R.string.email_reset_failed) + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        //----------------------------------------------------------\\
                    }
                });
    }

    private boolean setValidation() {
        String emailPattern = getString(R.string.email_validation);
        if((nameEt.getText().toString().isEmpty() || nameEt.getText().length()<=2)){
            nameEt.setError(getString(R.string.enter_name));
            return false;
        }
        if((mailEt.getText().toString().isEmpty() || ! mailEt.getText().toString().matches(emailPattern))){
            mailEt.setError(getString(R.string.enter_email));
            return false;
        }
        if((phoneEt.getText().toString().isEmpty()|| phoneEt.getText().length()<10)){
            phoneEt.setError(getString(R.string.enter_phone));
            return false;
        }
        else{
            return true;
        }
    }

    public void showProfileBottomSheetDialogFragment() {
        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        bottomSheetFragment.setCameraInterfaceC(Customer_Profile_Edit_Activity.this);
        bottomSheetFragment.setPhotoInterface(Customer_Profile_Edit_Activity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PROFILE_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra(getString(R.string.path_image_uri));
                    profileImg = uri.toString();
                    profileLocation = uri.getLastPathSegment();
                    StorageReference storageReference2 = storageReference.child(getString(R.string.customer_profile_image_folder)+profileLocation);
                    storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Snackbar snackbar = Snackbar
                                    .make(contextView, getString(R.string.upload_success), Snackbar.LENGTH_LONG);

                            View sbView = snackbar.getView();
                        /*    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setTextColor(Color.YELLOW);
                            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/
                            snackbar.show();
                            saveTv.setAlpha(1.0f);
                            saveTv.setEnabled(true);
                       //     getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            loadProfile(profileImg,userImg);
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar snackbar = Snackbar
                                            .make(contextView, getString(R.string.upload_failed), Snackbar.LENGTH_LONG);

                                    View sbView = snackbar.getView();
                                   /* TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                                    textView.setTextColor(Color.YELLOW);
                                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/
                                    snackbar.show();
                                    saveTv.setAlpha(1.0f);
                                    saveTv.setEnabled(true);
                                   // getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            Snackbar snackbar = Snackbar
                                    .make(contextView, getString(R.string.Uploading), Snackbar.LENGTH_LONG);

                            View sbView = snackbar.getView();
                           /* TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setTextColor(Color.YELLOW);
                            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/
                            snackbar.show();
                            saveTv.setAlpha(0.3f);
                            saveTv.setEnabled(false);
                       //     getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        //            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });
            }
        }
    }

    @Override
    public void cameraClicked() {
        launchCameraIntent();
    }

    @Override
    public void photoClicked() {
        launchGalleryIntent();
    }

    private void launchCameraIntent() {

        Intent intent = new Intent(Customer_Profile_Edit_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        if(pressed==true) {
            startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
        }
        bottomSheetFragment.dismiss();
    }


    private void launchGalleryIntent() {

        Intent intent = new Intent(Customer_Profile_Edit_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        if(pressed==true){
        startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
        }
        bottomSheetFragment.dismiss();
    }

    private void loadProfile(String url,CircleImageView circleImageView) {
        Glide.with(this).load(url)
                .into(circleImageView);
        circleImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.profile), profileImg);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        profileImg = savedInstanceState.getString(getString(R.string.profile), profileImg);
        if(profileImg!=null) {
            loadProfile(profileImg, userImg);
        }
    }
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
