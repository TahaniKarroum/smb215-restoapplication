package com.example.pc.restoapplication.Categories;

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


public class CategoriesListViewAdapter extends BaseAdapter {

    private List<Category> mCategories;

    private OnItemClickListener mListener;

    public CategoriesListViewAdapter() {
        this.mCategories = new ArrayList<Category>();
    }

    public void setData(List<Category> categoryInfos) {
        this.mCategories.clear();
        this.mCategories.addAll(categoryInfos);
        Log.i("setdata","  "+categoryInfos.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Category getItem(int position) {
        return mCategories.get(position);
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
       // convertView.setBackgroundResource(R.color.white);

        final Category categoryInfo = mCategories.get(position);
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
