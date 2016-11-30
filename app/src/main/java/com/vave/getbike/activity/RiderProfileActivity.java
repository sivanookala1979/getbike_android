package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.vave.getbike.R;
import com.vave.getbike.adapter.ProfilePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adarsht on 30/11/16.
 */

public class RiderProfileActivity extends BaseActivity {
    TabLayout mPagerSlidingTabStrip;
    ViewPager mViewPager;
    List<String> statusList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_profile);
        addToolbarView();
        mPagerSlidingTabStrip = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        statusList.add("YOUE VEHICLE");
        statusList.add("YOUR DRIVING LICENCE");
        createTabView();
    }
    private void createTabView() {
        ProfilePagerAdapter mPagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager(), statusList);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerSlidingTabStrip.setupWithViewPager(mViewPager);
        for (int i = 0; i < mPagerSlidingTabStrip.getTabCount(); i++) {
            mPagerSlidingTabStrip.getTabAt(i).setText(statusList.get(i));
        }
    }
}
