package com.example.pc.restoapplication.Categories;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.pc.restoapplication.MainActivity;
import com.example.pc.restoapplication.Products.ProductFragment;
import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.CommunicationAsyn;
import com.example.pc.restoapplication.helper.Constant;
import com.example.pc.restoapplication.helper.OnItemClickListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;



public class CategoryFragment extends Fragment implements OnItemClickListener<Category> {

    RelativeLayout ll;
    ListView list;
    private ArrayList<Category> categories;
    private CategoriesListViewAdapter mAdapter;
    private ProgressDialog nDialog;
    MainActivity mainActivity;
    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CategoriesListViewAdapter();
        ((MainActivity) getActivity()).setActionBarTitle("Menu");
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        list = (ListView) view.findViewById(R.id.list);
        Constant.CATEGORY_ID = "";
        Log.i("iii", "onCreateView: ");
        categories = new ArrayList<Category>();
        try {
            prepareCategories();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void prepareCategories() throws JSONException {
        nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        CommunicationAsyn.getWithoutParams("getAllCategories", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("categoryyyy ", " " + response.get(0));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject c = response.getJSONObject(i);
                        String subtitle = c.getString("name");
                        String id = c.getString("ID");
                        Category a = new Category(id, subtitle);
                        categories.add(a);
                    }
                    list.setAdapter(mAdapter);
                    mAdapter.setData(categories);
                    Log.i("setdata", "  " + categories.size());
                    nDialog.dismiss();
                } catch (JSONException e) {
                    nDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("failureeeee", " two");
                nDialog.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(View v, final int position, final Category category) {
        Constant.CATEGORY_ID = category.getId();
        Constant.CATEGORYNAME = category.getName();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("tag");
        getFragmentManager().beginTransaction().replace(R.id.container, ProductFragment.newInstance()).commit();
        getFragmentManager().executePendingTransactions();
        return;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //save the activity to a member of this fragment
        mainActivity = (MainActivity) activity;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }
}