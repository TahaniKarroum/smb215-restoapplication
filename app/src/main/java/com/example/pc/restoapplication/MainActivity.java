package com.example.pc.restoapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.pc.restoapplication.Categories.CategoryFragment;
import com.example.pc.restoapplication.helper.CommunicationAsyn;
import com.example.pc.restoapplication.helper.Constant;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.tabak.fragmentswitcher.FragmentStateArrayPagerAdapter;
import me.tabak.fragmentswitcher.FragmentSwitcher;

public class MainActivity extends AppCompatActivity {
    public static FragmentSwitcher mFragmentSwitcher;
    private ViewPager viewPager;
    private FrameLayout frameLayout;
    private FragmentStateArrayPagerAdapter mFragmentAdapter;
    private TabLayout tabLayout;
    String android_id;
    private ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        setupViewPager(viewPager);
        getDeviceid();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        mFragmentSwitcher.setVisibility(View.GONE);
                        frameLayout.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                    }
                });
        initializeFragmentSwitcher();
        fillAdapters();
    }
    public void getDeviceid() {
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        RequestParams params = new RequestParams();
        params.put("deviceid", android_id);
        CommunicationAsyn.get("ping", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        try {
                            Log.i("ping ", " " + response.get(0));
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject c = response.getJSONObject(i);
                                String clientid = c.getString("ID");
                                String name = c.getString("name");
                                Constant.CLIENTID = clientid;
                                Constant.CLIENTNAME = name;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable
                            throwable, JSONObject errorResponse) {
                        Log.i("failureeeee", " two");

                    }
                }

        );
    }
    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CategoryFragment(), "Menu");
        adapter.addFrag(new CategoryFragment(), "Cart");
        adapter.addFrag(new CategoryFragment(), "Settings");
        viewPager.setAdapter(adapter);
    }

    protected class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("position  ", " pppp  " + position);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void initializeFragmentSwitcher() {
        mFragmentSwitcher = (FragmentSwitcher) findViewById(R.id.fragment_switcher);
        mFragmentAdapter = new FragmentStateArrayPagerAdapter(getSupportFragmentManager());
        mFragmentSwitcher.setAdapter(mFragmentAdapter);
    }

    HashMap<String, Integer> fragmentPosition;

    private void fillAdapters() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragmentPosition = new HashMap<String, Integer>();
        int position = 0;

        fragments.add(CategoryFragment.newInstance());
        fragmentPosition.put(Constant.CATEGORYFRAGMENT, position++);

        mFragmentAdapter.addAll(fragments);

    }


    public void switchFragment(int x) {
        mFragmentSwitcher.setCurrentItem(x);
        viewPager.setCurrentItem(x);
    }

    public void runFragment(String key) {
        if (fragmentPosition != null && fragmentPosition.size() == 0) {
            mFragmentSwitcher.setVisibility(View.GONE);
        } else {
            if (key.equals("categoryfragment")) {
                viewPager.setCurrentItem(1);
                viewPager.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);

            }
            else
                viewPager.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);

        }
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
}
