package com.example.pc.restoapplication.helper;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Macbook on 2016/7/10.
 */
public interface OnItemClickListener<T> {
    void onItemClick(View v, int position, T t);

}
