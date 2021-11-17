package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFrameLayout extends MyView {

    public MyFrameLayout(FrameLayout object, Context context) {
        super(object, context);
    }

    public int getChildCount() {
        return object != null ? ((FrameLayout) object).getChildCount() : 0;
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((FrameLayout) object).addView(child);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((FrameLayout) object).addView(child, params);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void removeAllViews() {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((FrameLayout) object).removeAllViews();
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
