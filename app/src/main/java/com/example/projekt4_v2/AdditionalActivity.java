package com.example.projekt4_v2;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class AdditionalActivity extends AppCompatActivity {
    ViewPager2 mPager;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);
        mPager = findViewById(R.id.viewPager);
        adapter = new FragmentAdapter(this);
        mPager.setAdapter(adapter);
    }

}
