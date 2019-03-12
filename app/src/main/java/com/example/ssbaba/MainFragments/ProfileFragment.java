package com.example.ssbaba.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssbaba.Login.ActivityLogin;
import com.example.ssbaba.MainActivity;
import com.example.ssbaba.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    //vars
    FirebaseAuth mAuth;
    String currentUser;
    Button logOut;
    TextView userNameTv;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String firstName,lastName;
    CircleImageView profilePic;
    private static final int GALLERY_PICK=1;
    private StorageReference userProfileImageRef;
    private String link;
    public static  View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseApp.initializeApp(view.getContext());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("Users");
        userProfileImageRef = FirebaseStorage.getInstance().getReference();
        userNameTv = view.findViewById(R.id.user_name_tv);
        profilePic=view.findViewById(R.id.profile_picture);
        logOut=view.findViewById(R.id.logOut);
        getUserData();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sinOut();


            }
        });

    profilePic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent galleryIntent=new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            Log.e("test","108");

            startActivityForResult(galleryIntent,GALLERY_PICK);
            Log.e("test","110");


        }
    });


        return view;

    }


    private void sinOut() {
        mAuth.signOut();
        Intent intent = new Intent(getContext(), ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void getUserData() {

        userRef.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().isEmpty()) {
                    firstName = "no name found";
                    lastName = " ";


                } else {
                    firstName = dataSnapshot.child("first_name").getValue().toString();
                    lastName = dataSnapshot.child("last_name").getValue().toString();

                    //Log.e("hello", );
                }
                if (dataSnapshot.hasChild("image")){
                    String pp = dataSnapshot.child("image").getValue().toString();
                    Picasso.with(getContext()).load(pp).placeholder(R.drawable.ic_launcher_foreground).into(profilePic);
                }

                userNameTv.setText(firstName + " " +lastName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Fragment uploadType = getChildFragmentManager().findFragmentById(R.id.fragment_container);

        if (uploadType != null) {
            uploadType.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_PICK && resultCode==RESULT_OK && data!=null){

            Uri ImageUri=data.getData();
            Log.e("activity",getActivity()+" ");
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(getActivity());
            Log.e("test ","159");


        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.e("test ","172");

            if (resultCode == RESULT_OK)
            {
                final Uri resultUri = result.getUri();
                Log.e("test ","170");

                final StorageReference filePath= userProfileImageRef.child(currentUser + ".jpg");
                filePath.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        link=uri.toString();
                                        userRef.child(currentUser).child("image").setValue(link);

                                        Log.e("test ","180");

                                    }
                                });
                            }
                        })

                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(),"your picture has been updated successfully",Toast.LENGTH_LONG).show();
                                    Log.e("test ","191");



                                }
                                else
                                {
                                    String errorMessage = task.getException().toString();
                                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

                                }

                            }

                        })
                ;



            }
        }
    }
}
