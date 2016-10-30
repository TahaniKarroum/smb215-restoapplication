package com.example.pc.restoapplication.FeedBacks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pc.restoapplication.R;
import com.example.pc.restoapplication.helper.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FeedbacksListViewAdapter extends BaseAdapter {

    private List<FeedBack> feedBacks;

    private OnItemClickListener mListener;

    public FeedbacksListViewAdapter() {
        this.feedBacks = new ArrayList<FeedBack>();
    }

    public void setData(List<FeedBack> categoryInfos) {
        this.feedBacks.clear();
        this.feedBacks.addAll(categoryInfos);
        Log.i("setdata","  "+categoryInfos.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return feedBacks.size();
    }

    @Override
    public FeedBack getItem(int position) {
        return feedBacks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       final FeedBack categoryInfo = feedBacks.get(position);
        holder.tvFName.setText(categoryInfo.getName() + " " );
        Log.i("setdata","  "+categoryInfo.getName());
        return convertView;
    }

    public final static class ViewHolder {

        @Bind(R.id.tvFName)
        public TextView tvFName;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
