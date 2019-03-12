package com.example.ssbaba;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.ssbaba.Login.ActivityLogin;
import com.example.ssbaba.MainFragments.HomeFragment;
import com.example.ssbaba.MainFragments.ProfileFragment;
import com.example.ssbaba.MainFragments.ShoppingCartFragment;
import com.example.ssbaba.MainFragments.wishListFragment;
import com.example.ssbaba.getUserInfo.getUserInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ssbaba.Regestration.ActivityRegestration.getContext;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int GALLERY_PICK = 1;
    BottomNavigationView bottomNav;
    FirebaseAuth mAuth;
    DatabaseReference userRef;
    String uId;
    ImageView toolbarLogo;
    CircleImageView navuseRImage;
    TextView navUserName, navUserMail;
    View navigationHeader;
    private FirebaseUser user;
    private StorageReference userProfileImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userProfileImageRef = FirebaseStorage.getInstance().getReference();


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer.addDrawerListener(toggle);

        toggle.syncState();
        toolbarLogo = findViewById(R.id.logoXmarks);
        toolbarLogo.setVisibility(View.VISIBLE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        navigationHeader = navigationView.getHeaderView(0);
        navuseRImage = navigationHeader.findViewById(R.id.userImageView_nav);
        navUserName = navigationHeader.findViewById(R.id.userName_nav);
        navUserMail = navigationHeader.findViewById(R.id.userMail_nav);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment selectedFragment = new HomeFragment();

        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;

            case R.id.nav_wish_list:
                selectedFragment = new wishListFragment();
                break;
            case R.id.nav_cart:
                selectedFragment = new ShoppingCartFragment();
                break;
            case R.id.nav_profile:
                selectedFragment = new ProfileFragment();
                break;

            case R.id.nav_Log_out:
                mAuth.signOut();

                Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                selectedFragment = new HomeFragment();
                break;

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (user == null) {
            Intent i = new Intent(MainActivity.this, ActivityLogin.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        } else {
            uId = mAuth.getCurrentUser().getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("first_name").exists()) {
                        String firstName = dataSnapshot.child("first_name").getValue().toString();
                        String lastName = dataSnapshot.child("last_name").getValue().toString();

                        Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        showUserDataInNav(firstName, lastName);

                        if (dataSnapshot.hasChild("image")) {

                            String pp = dataSnapshot.child("image").getValue().toString();
                            Picasso.with(getBaseContext()).load(pp).placeholder(R.drawable.ic_launcher_foreground).into(navuseRImage);

                        }


                    } else {
                        Intent intent = new Intent(MainActivity.this, getUserInfoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void showUserDataInNav(String firstName, String lastName) {

        navUserName.setText(firstName + " " + lastName);
        navUserMail.setText(mAuth.getCurrentUser().getEmail() + "");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.e("test ", "172");

            if (resultCode == RESULT_OK) {
                final Uri resultUri = result.getUri();
                Log.e("test ", "170");

                final StorageReference filePath = userProfileImageRef.child(mAuth.getCurrentUser().getUid() + ".jpg");
                filePath.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String link = uri.toString();
                                        userRef.child("image").setValue(link);

                                        Log.e("test ", "180");

                                    }
                                });
                            }
                        })

                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if (task.isSuccessful()) {
                                    //  Toast.makeText(getContext(),"your picture has been updated successfully",Toast.LENGTH_LONG).show();
                                    Log.e("test ", "191");


                                } else {
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




