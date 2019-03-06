package com.example.ssbaba.MainFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.ssbaba.CategoryAdapter;
import com.example.ssbaba.GridItemDecoration;
import com.example.ssbaba.Item;
import com.example.ssbaba.MainActivity;
import com.example.ssbaba.R;
import com.example.ssbaba.SpecificCategoryActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ViewFlipper viewFlipper;
    RecyclerView categroiesRecyclerView;
    private static final int PICK_IMG_REQUEST =1 ;
    private static final int SPACING =1 ;
    private static int SPAN_COUNT=4;
    CategoryAdapter adapter;
    ArrayList<Item> arrayList;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_, container, false);
         viewFlipper=view.findViewById(R.id.view_flipper);

        int img[]={R.drawable.slide3,R.drawable.slide2,R.drawable.slide1};

        for(int image:img){

            ImageView imageView=new ImageView(view.getContext());
            imageView.setBackgroundResource(image);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);

        }

        arrayList = new ArrayList<>();
        arrayList.add(new Item(R.drawable.phone,"phones"));
        arrayList.add(new Item(R.drawable.ipad,"tablets"));
        arrayList.add(new Item(R.drawable.watch,"Smart Watches"));
        arrayList.add(new Item(R.drawable.acessories,"Accessories"));

        categroiesRecyclerView = view.findViewById(R.id.categories);
        final Intent intent = new Intent(getActivity(), SpecificCategoryActivity.class);

        adapter = new CategoryAdapter(arrayList, getActivity(), new CategoryAdapter.OnClickListener() {
            @Override
            public void onClick(int i) {
                intent.putExtra("i",i);
                Log.e("Main",i+"");

                startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        categroiesRecyclerView.setLayoutManager(gridLayoutManager);
        categroiesRecyclerView.addItemDecoration(new GridItemDecoration(SPAN_COUNT,SPACING,true));
        categroiesRecyclerView.setAdapter(adapter);


        return view;




    }
}
