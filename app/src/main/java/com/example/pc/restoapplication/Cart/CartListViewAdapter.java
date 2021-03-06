package com.example.pc.restoapplication.Cart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.CommunicationAsyn;
import com.example.pc.restoapplication.helper.Constant;
import com.example.pc.restoapplication.helper.OnItemClickListener;
import com.example.pc.restoapplication.helper.OnLongClickListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class CartListViewAdapter extends BaseAdapter {

    private List<Order_Product> order_products;

    private OnItemClickListener mListener;
    private OnLongClickListener mLongListener;

    public CartListViewAdapter() {
        this.order_products = new ArrayList<Order_Product>();
    }

    public void setData(List<Order_Product> order_products) {
        this.order_products.clear();
        this.order_products.addAll(order_products);
        Log.i("setdata", "  " + order_products.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLongClickListener(OnLongClickListener longlistener) {
        this.mLongListener = longlistener;
    }

    @Override
    public int getCount() {
        return order_products.size();
    }

    @Override
    public Order_Product getItem(int position) {
        return order_products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundResource(R.color.white);

        final Order_Product order_product = order_products.get(position);
        holder.tvFName.setText(order_product.getName() + " ");
        holder.price.setText(order_product.getPrice() + " $");
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder(order_product.getProduct_ID(), 0);
            }
        });
        // holder.tvMName.setText(patientInfo.getMname());
        // holder.tvLName.setText(patientInfo.getLname());
        // holder.tvMotherName.setText(patientInfo.getMothername());
        Log.i("setdata", "  " + order_product.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, order_product);
                }
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mLongListener != null) {
                    mLongListener.onLongClick(view, position, order_product);
                }
                return true;
            }
        });

        if (order_product.getThumbnail() != null && order_product.getThumbnail().toString().length() > 1) {
            Picasso.with(parent.getContext()).load(order_product.getThumbnail()).error(R.drawable.cat2).placeholder(R.drawable.cat2).into(holder.ivIcon);
        } else {
            holder.ivIcon.setImageResource(R.drawable.cat2);

        }
        return convertView;
    }

    public void addOrder(String productid, int qty) {
        RequestParams params = new RequestParams();
        params.put("deviceid", Constant.CLIENTID);
        params.put("productid", productid);
        params.put("qty", qty);
        params.put("orderid", Constant.ORDERID);
        String functionName = "addtocart?deviceid=" + Constant.DEVICEID + "&productid=" + productid + "&orderid=" + Constant.ORDERID + "&qty=" + qty;
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
                    notifyDataSetChanged();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("failureeeee", " two");
            }
        });
    }

    public final static class ViewHolder {

        @Bind(R.id.ivIcon)
        public ImageView ivIcon;

        @Bind(R.id.tvFName)
        public TextView tvFName;

        @Bind(R.id.price)
        public TextView price;

        @Bind(R.id.remove)
        public ImageView remove;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
