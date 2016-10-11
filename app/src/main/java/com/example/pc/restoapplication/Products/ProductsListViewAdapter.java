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
import com.example.pc.restoapplication.helper.OnLongClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProductsListViewAdapter extends BaseAdapter {

    private List<Product> products;

    private OnItemClickListener mListener;
    private OnLongClickListener mLongListener;

    public ProductsListViewAdapter() {
        this.products = new ArrayList<Product>();
    }

    public void setData(List<Product> profuctInfos) {
        this.products.clear();
        this.products.addAll(profuctInfos);
        Log.i("setdata", "  " + profuctInfos.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnLongClickListener(OnLongClickListener longlistener) {
        this.mLongListener = longlistener;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundResource(R.color.white);

        final Product productInfo = products.get(position);
        holder.tvFName.setText(productInfo.getName() + " ");
        holder.price.setText(productInfo.getPrice() + " $");
        // holder.tvMName.setText(patientInfo.getMname());
        // holder.tvLName.setText(patientInfo.getLname());
        // holder.tvMotherName.setText(patientInfo.getMothername());
        Log.i("setdata", "  " + productInfo.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, productInfo);
                }
            }
        });
  convertView.setOnLongClickListener(new View.OnLongClickListener(){
      @Override
      public boolean onLongClick(View view) {
          if (mLongListener != null) {
              mLongListener.onLongClick(view, position, productInfo);
          }
          return true;
      }
  });

        if (productInfo.getThumbnail() != null && productInfo.getThumbnail().toString().length() > 1) {
            Picasso.with(parent.getContext()).load(productInfo.getThumbnail()).error(R.drawable.cat2).placeholder(R.drawable.cat2).into(holder.ivIcon);
        } else {
            holder.ivIcon.setImageResource(R.drawable.cat2);

        }


        return convertView;
    }

    public final static class ViewHolder {

        @Bind(R.id.ivIcon)
        public ImageView ivIcon;

        @Bind(R.id.tvFName)
        public TextView tvFName;


        @Bind(R.id.price)
        public TextView price;



       /* @Bind(R.id.tvMName)
        public TextView tvMName;


        @Bind(R.id.tvMotherName)
        public TextView tvMotherName;*/


        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
