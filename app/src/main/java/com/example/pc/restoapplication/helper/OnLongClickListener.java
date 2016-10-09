package com.example.pc.restoapplication.helper;

import android.view.View;

/**
 * Created by Macbook on 2016/7/10.
 */
public interface OnLongClickListener<T> {
    void onLongClick(View v, int position, T t);

}
