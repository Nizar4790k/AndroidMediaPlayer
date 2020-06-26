package com.example.androidmediaplayer;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private  String mSelectedTabName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        final int tabCount = mTabLayout.getTabCount();

        final SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(
                getSupportFragmentManager(),this,tabCount);


        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        String [] tabNames = {
                getString(R.string.audio),
                getString(R.string.video)
        };


        TabListener tabListener = new TabListener(mViewPager,tabNames,this);
        mTabLayout.addOnTabSelectedListener(tabListener);




    }


    private class TabListener implements TabLayout.OnTabSelectedListener {

        private ViewPager mViewPager;
        private String [] mTabNames;
        private Context mContext;


        public TabListener(ViewPager viewPager, String [] tabNames, Context context){
            mViewPager = viewPager;
            mTabNames = tabNames;
            mContext = context;

        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            int position = tab.getPosition();

            mViewPager.setCurrentItem(position);




        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {



        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

}