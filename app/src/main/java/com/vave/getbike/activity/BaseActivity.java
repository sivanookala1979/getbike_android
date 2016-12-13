package com.vave.getbike.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.iid.InstanceID;
import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikePreferences;

/**
 * Created by adarsht on 30/11/16.
 */

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ImageView userProfileImage;
    public TextView occupation;
    public TextView friendsCount;
    public TextView userAddress;

    public void addToolbarView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getCurrencySymbol() {
        return getApplicationContext().getResources().getString(R.string.Rs);
    }

    public void addNavigationMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        userProfileImage = (ImageView) headerView.findViewById(R.id.profileImage);
        occupation = (TextView) headerView.findViewById(R.id.occupation);
        friendsCount = (TextView) headerView.findViewById(R.id.friendsCount);
        userAddress = (TextView) headerView.findViewById(R.id.userAddress);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.profilAndSettings:
                startActivity(new Intent(this, ProfileAndSettingsActivity.class));
                break;
            case R.id.ridesAndHistory:
                startActivity(new Intent(this,ScheduledRidesAndHistoryActivity.class));
                break;
            case R.id.earnFreeRides:
                startActivity(new Intent(this, ShareActivity.class));
                break;
            case R.id.support:
                startActivity(new Intent(this, FAQActivity.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, LogoScreenActivity.class));
                try {
                    InstanceID.getInstance(getApplicationContext()).deleteInstanceID();
                } catch (Exception ex) {
                    Log.e("InstanceID", ex.getMessage(), ex);
                }
                GetBikePreferences.reset();
                finish();
                break;
            case R.id.wallet:
                startActivity(new Intent(this, GetBikeWalletHome.class));
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
