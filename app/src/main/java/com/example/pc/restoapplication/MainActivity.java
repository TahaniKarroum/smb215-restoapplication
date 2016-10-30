package com.example.pc.restoapplication;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.pc.restoapplication.Cart.CartFragment;
import com.example.pc.restoapplication.Categories.CategoryFragment;
import com.example.pc.restoapplication.helper.Constant;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabClickListener;

import me.tabak.fragmentswitcher.FragmentStateArrayPagerAdapter;
import me.tabak.fragmentswitcher.FragmentSwitcher;

public class MainActivity extends AppCompatActivity {
    public static FragmentSwitcher mFragmentSwitcher;
    private ViewPager viewPager;
    private FrameLayout frameLayout;
    private FragmentStateArrayPagerAdapter mFragmentAdapter;
    private TabLayout tabLayout;
    public String android_id;
    private ProgressDialog nDialog;
    private BottomBar mBottomBar;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        getDeviceid();
        bar = getSupportActionBar();
        bar.setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);
        mBottomBar = BottomBar.attach(this, savedInstanceState,
                Color.parseColor("#621284"), // Background Color
                ContextCompat.getColor(this, R.color.white), // Tab Item Color
                0.25f); // Tab Item Alpha
        mBottomBar.setItems(R.menu.bottombar_menu);

        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                // The user selected a tab at the specified position
                switch (position) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, AboutFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;

                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, CategoryFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, CartFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;

                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, CartFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;

                }
            }

            @Override
            public void onTabReSelected(int position) {
                switch (position) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, AboutFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;

                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, CategoryFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, CartFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;

                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, CartFragment.newInstance()).commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return;

                }
            }
        });
        mBottomBar.selectTabAtPosition(2, false);
    }

    public void getDeviceid() {
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Constant.DEVICEID = android_id;
    }

    @Override
    public void onBackPressed() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {

            int index = fm.getBackStackEntryCount();
            Fragment fragment = fm.getFragments().get(index);
            if (fragment instanceof CategoryFragment) {
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            } else {
                viewPager.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }

            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    public void setActionBarTitle(String title) {
        bar.setTitle(title);
    }
}
