package com.example.androidmediaplayer.video;

import androidx.fragment.app.Fragment;

import com.example.androidmediaplayer.SingleFragmentActivity;

public class VideoActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new VideoFragment();
    }
}
