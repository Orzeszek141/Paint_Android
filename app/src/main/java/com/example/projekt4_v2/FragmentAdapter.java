package com.example.projekt4_v2;

import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.io.File;

public class FragmentAdapter extends FragmentStateAdapter {
    File sd = Environment.getExternalStorageDirectory();
    File directory = new File(sd.toString()+"/Pictures/Paint");
    File[] images = directory.listFiles();
    //String[] images = {"https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png","https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png"};

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("image", images[position].getAbsolutePath());
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}
