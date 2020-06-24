package com.example.androidmediaplayer;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.androidmediaplayer.audio.AudioListFragment;
import com.example.androidmediaplayer.video.VideoListFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private  int mTotalTabs;


    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context, int tab) {
        super(fm);
        mContext = context;
        mTotalTabs = tab;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:

                return new VideoListFragment();

            case 1:

                return new AudioListFragment();


            default:
                return  null;


        }



    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }


}