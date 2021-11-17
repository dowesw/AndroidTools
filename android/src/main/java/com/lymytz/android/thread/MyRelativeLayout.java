package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyRelativeLayout extends MyView {

    public MyRelativeLayout(RelativeLayout object, Context context) {
        super(object, context);
    }

    public int getChildCount() {
        return object != null ? ((RelativeLayout) object).getChildCount() : 0;
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((RelativeLayout) object).addView(child);
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
                    ((RelativeLayout) object).addView(child, params);
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
                    ((RelativeLayout) object).removeAllViews();
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
