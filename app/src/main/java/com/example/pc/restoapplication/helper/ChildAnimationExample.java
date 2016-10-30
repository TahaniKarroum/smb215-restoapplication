package com.example.pc.restoapplication.helper;

import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.Animations.BaseAnimationInterface;

public class ChildAnimationExample implements BaseAnimationInterface {

    private final static String TAG = "ChildAnimationExample";

    @Override
    public void onPrepareCurrentItemLeaveScreen(View current) {
    }

    @Override
    public void onPrepareNextItemShowInScreen(View next) {
    }

    @Override
    public void onCurrentItemDisappear(View view) {
        Log.e(TAG,"onCurrentItemDisappear called");
    }

    @Override
    public void onNextItemAppear(View view) {
    }
}
