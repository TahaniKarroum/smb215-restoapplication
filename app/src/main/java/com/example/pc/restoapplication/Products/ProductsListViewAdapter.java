package com.example.pc.restoapplication.Products;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProductsListViewAdapter extends BaseAdapter {

    private List<Product> products;

    private OnItemClickListener mListener;

    public ProductsListViewAdapter() {
        this.products = new ArrayList<Product>();
    }

    public void setData(List<Product> profuctInfos) {
        this.products.clear();
        this.products.addAll(profuctInfos);
        Log.i("setdata","  "+profuctInfos.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundResource(R.color.white);

        final Product categoryInfo = products.get(position);
        holder.tvFName.setText(categoryInfo.getName() + " " );
        // holder.tvMName.setText(patientInfo.getMname());
        // holder.tvLName.setText(patientInfo.getLname());
        // holder.tvMotherName.setText(patientInfo.getMothername());
        Log.i("setdata","  "+categoryInfo.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, categoryInfo);
                }
            }
        });

        /*if (categoryInfo.getThumbnail() != null && categoryInfo.getThumbnail().toString().length() > 1) {
            Picasso.with(parent.getContext()).load(categoryInfo.getThumbnail()).error(R.drawable.banks).placeholder(R.drawable.banks).into(holder.ivIcon);
        } else {
            holder.ivIcon.setImageResource(R.drawable.banks);

        }*/


        return convertView;
    }

    public final static class ViewHolder {

        @Bind(R.id.ivIcon)
        public ImageView ivIcon;

        @Bind(R.id.tvFName)
        public TextView tvFName;



       /* @Bind(R.id.tvMName)
        public TextView tvMName;


        @Bind(R.id.tvMotherName)
        public TextView tvMotherName;*/


        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
