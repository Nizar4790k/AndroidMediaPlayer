package com.example.androidmediaplayer.audio;

import androidx.fragment.app.Fragment;

import com.example.androidmediaplayer.SingleFragmentActivity;

public class AudioActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new AudioFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
