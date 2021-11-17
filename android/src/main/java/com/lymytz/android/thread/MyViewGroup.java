package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyViewGroup extends MyView {

    public MyViewGroup(ViewGroup object, Context context) {
        super(object, context);
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ViewGroup) object).addView(child);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyViewGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ViewGroup) object).addView(child, params);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyViewGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
