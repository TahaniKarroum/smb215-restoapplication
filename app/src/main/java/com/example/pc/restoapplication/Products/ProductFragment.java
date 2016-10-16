package com.example.pc.restoapplication.Products;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pc.restoapplication.MainActivity;
import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.CommunicationAsyn;
import com.example.pc.restoapplication.helper.Constant;
import com.example.pc.restoapplication.helper.OnItemClickListener;
import com.example.pc.restoapplication.helper.OnLongClickListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProductFragment extends Fragment implements OnItemClickListener<Product>, OnLongClickListener<Product> {

    RelativeLayout ll;
    ListView list;
    private ArrayList<Product> products;
    private ProductsListViewAdapter mAdapter;
    private ProgressDialog nDialog;
    MainActivity mainActivity;
    Context context;

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        mAdapter = new ProductsListViewAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLongClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        list = (ListView) view.findViewById(R.id.list);
        Log.i("iii", "onCreateView: ");
        products = new ArrayList<Product>();
        try {
            prepareProducts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void prepareProducts() throws JSONException {
        RequestParams params = new RequestParams();
        nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        params.put("categoryid", Constant.CATEGORY_ID);
        CommunicationAsyn.get("getAllProducts", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("productttt ", " " + response.get(0));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject c = response.getJSONObject(i);
                        String subtitle = c.getString("name");
                        String id = c.getString("ID");
                        String image = Constant.IP + "public/template/images/" + c.getString("imagePath");
                        String price = c.getString("price");
                        Product a = new Product(id, subtitle, image, price);
                        products.add(a);
                    }
                    list.setAdapter(mAdapter);
                    mAdapter.setData(products);
                    Log.i("setdata", "  " + products.size());
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
    public void onItemClick(View v, final int position, final Product category) {
        /*Constant.CATEGORY_ID = category.getId();
        Constant.CATEGORYNAME = category.getName();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("tag");
        transaction.replace(R.id.container, SubCategoryFragment.newInstance()).commit();
        getFragmentManager().executePendingTransactions();
        ((MainActivity) getActivity()).runFragment(Constant.SUBCATEGORYFRAGMENT);*/
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

    @Override
    public void onLongClick(View v, int position, final Product product) {
        Log.i("Product fragment", " dhdhe" + product.getName());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final EditText et = new EditText(context);
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText qtyedittext = (EditText) alertLayout.findViewById(R.id.qty);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Set Quantity");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String qty = qtyedittext.getText().toString();
                addOrder(product.getId(), Integer.parseInt(qty));
                Toast.makeText(context, "Quantity: " + qty, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void addOrder(String productid, int qty) {
        RequestParams params = new RequestParams();
        nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        params.put("deviceid", Constant.CLIENTID);
        params.put("productid", productid);
        params.put("qty", qty);
        params.put("orderid", Constant.ORDERID);
        String functionName = "addtocart?deviceid=" + mainActivity.android_id + "&productid=" + productid + "&orderid=" + Constant.ORDERID + "&qty=" + qty;
        CommunicationAsyn.getWithoutParams(functionName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    Log.i("Add order ", " " + response.get(0));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject c = response.getJSONObject(i);
                        String order_ID = c.getString("order_ID");
                        Constant.ORDERID = order_ID;
                    }
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
}