package com.example.pc.restoapplication.FeedBacks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pc.restoapplication.MainActivity;
import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.CommunicationAsyn;
import com.example.pc.restoapplication.helper.Constant;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class FeedBackFragment extends Fragment {

    RelativeLayout ll;
    ListView list;
    ImageView imageView;
    private ArrayList<FeedBack> feedBacks;
    private FeedbacksListViewAdapter mAdapter;
    private ProgressDialog nDialog;
    MainActivity mainActivity;

    public static FeedBackFragment newInstance() {
        FeedBackFragment fragment = new FeedBackFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FeedbacksListViewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        list = (ListView) view.findViewById(R.id.list);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = mainActivity.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.feedback_dialog, null);
                final EditText text = (EditText) alertLayout.findViewById(R.id.text);
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Set Feedback");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String textsTR = text.getText().toString();
                        if (textsTR.length() > 0)
                            addFeedback(textsTR);
                        else
                            Toast.makeText(getContext(), "Ypu should enter text! " + textsTR, Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        Log.i("iii", "onCreateView: ");
        feedBacks = new ArrayList<FeedBack>();
        try {
            prepareFeedBacks();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;

    }

    public void addFeedback(String text) {
        nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        String functionName = "addFeed?deviceid=" + mainActivity.android_id + "&text=" + text;
        CommunicationAsyn.getWithoutParams(functionName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("Add order ", " " + response.get(0));
                    refresh();
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

    public void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("tag");
        getFragmentManager().beginTransaction().replace(R.id.container, FeedBackFragment.newInstance()).commit();
        getFragmentManager().executePendingTransactions();
        return;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void prepareFeedBacks() throws JSONException {
        nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        CommunicationAsyn.getWithoutParams("listfeedbacks?deviceid=" + Constant.DEVICEID, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("feedbacks ", " " + response.get(0));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject c = response.getJSONObject(i);
                        String subtitle = c.getString("description");
                        String id = c.getString("ID");
                        FeedBack a = new FeedBack(id, subtitle);
                        feedBacks.add(a);
                    }
                    list.setAdapter(mAdapter);
                    mAdapter.setData(feedBacks);
                    Log.i("setdata", "  " + feedBacks.size());
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