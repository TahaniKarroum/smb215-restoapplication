package com.example.pc.restoapplication.History;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.restoapplication.Cart.Order_Product;
import com.example.pc.restoapplication.MainActivity;
import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.CommunicationAsyn;
import com.example.pc.restoapplication.helper.Constant;
import com.example.pc.restoapplication.helper.OnItemClickListener;
import com.example.pc.restoapplication.helper.OnLongClickListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HistoryFragment extends Fragment implements OnItemClickListener<Order_Product>, OnLongClickListener<Order_Product> {

    RelativeLayout ll;
    ListView list;
    Button confirm;
    private ArrayList<Order> orders;
    private HistoryListViewAdapter mAdapter;
    private ProgressDialog nDialog;
    MainActivity mainActivity;
    Context context;
    double total;
    TextView totalTextView;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        mAdapter = new HistoryListViewAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLongClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        list = (ListView) view.findViewById(R.id.list);
        confirm = (Button) view.findViewById(R.id.confirm);
        totalTextView = (TextView) view.findViewById(R.id.textView);
        if(total==0)
            totalTextView.setVisibility(View.GONE);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = mainActivity.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.information, null);
                final EditText nameedittext = (EditText) alertLayout.findViewById(R.id.name);
                final EditText phoneedittext = (EditText) alertLayout.findViewById(R.id.phone);
                final EditText addressedittext = (EditText) alertLayout.findViewById(R.id.address);
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
                        String namestr = nameedittext.getText().toString();
                        String phonestr = phoneedittext.getText().toString();
                        String addressstr = addressedittext.getText().toString();
                        if (namestr.length() == 0 || phonestr.length() == 0 || addressstr.length() == 0) {
                            Toast.makeText(context, "You should enter name , address and phone !", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(context, "Name: " + namestr, Toast.LENGTH_SHORT).show();
                            fillClientInformation(namestr + "", phonestr + "", addressstr + "");
                            Constant.ORDERID = "";
                            refresh();
                            //Toast.makeText(context, "Phone: " + phonestr, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        Log.i("iii", "onCreateView: ");
        orders = new ArrayList<Order>();
        try {
            prepareHistory();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void prepareHistory() throws JSONException {
        String orderid = Constant.ORDERID;
        total=0;
        if (orderid.length() > 0) {
            nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
            String functionname = "getHistory?deviceid=" + Constant.DEVICEID;
            CommunicationAsyn.getWithoutParams(functionname, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    try {
                        Log.i("productttt ", " " + response.get(0));
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject c = response.getJSONObject(i);
                            String subtitle = c.getString("productname");
                            String productid = c.getString("product_ID");
                            String id = c.getString("ID");
                            int qty = c.getInt("quantity");
                            String image = Constant.IP + "public/template/images/" + c.getString("image");
                            String price = c.getString("unitPrice");
                            Order a = new Order(id, subtitle, image, price, qty);
                            total += qty * Double.parseDouble(price);
                            orders.add(a);
                        }
                        if (orders.size() > 0) {
                            confirm.setVisibility(View.VISIBLE);
                            totalTextView.setVisibility(View.VISIBLE);
                            totalTextView.setText("Total : "+total+" $");
                        }
                        else {
                            confirm.setVisibility(View.GONE);
                            totalTextView.setVisibility(View.GONE);
                        }
                        list.setAdapter(mAdapter);
                        mAdapter.setData(orders);
                        Log.i("setdata", "  " + orders.size());
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

    @Override
    public void onItemClick(View v, final int position, final Order_Product category) {
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

    public void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("tag");
        transaction.replace(R.id.container, HistoryFragment.newInstance()).commit();
        getFragmentManager().executePendingTransactions();
        ((MainActivity) getActivity()).runFragment(Constant.CARTFRAGMENT);
    }

    @Override
    public void onLongClick(View v, int position, final Order_Product order_product) {
        Log.i("Product fragment", " dhdhe" + order_product.getName());
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText qtyedittext = (EditText) alertLayout.findViewById(R.id.qty);
        qtyedittext.setText(order_product.getQty() + "");
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
                Toast.makeText(context, "Quantity: " + qty, Toast.LENGTH_SHORT).show();
                addOrder(order_product.getProduct_ID(), Integer.parseInt(qty));

                Toast.makeText(context, "Quantity: " + qty, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void addOrder(String productid, int qty) {
        nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
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
                    refresh();
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

    public void fillClientInformation(String name, String phone, String address) {
        String n = name.replace(" ", "%20");
        String p = phone.replace(" ", "%20");
        String a = address.replace(" ", "%20");
        Log.i("fffff", "ffff " + name + " " + phone + " " + address);
        nDialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        String functionName = "fillClientInformation?deviceid=" + mainActivity.android_id + "&name=" + n + "&address=" + a + "&phone=" + p;
        CommunicationAsyn.getWithoutParams("fillClientInformation?deviceid=" + mainActivity.android_id + "&name=" + n + "&address=" + a + "&phone=" + p + "&orderid=" + Constant.ORDERID, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                nDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("failureeeee", " two");
                nDialog.dismiss();
            }
        });
    }
}