package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyHorizontalScrollView extends MyView {

    public MyHorizontalScrollView(LinearLayout object, Context context) {
        super(object, context);
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            if (object != null) {
                ((LinearLayout) object).addView(child);
            }
        });
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        runOutUiThread(() -> {
            if (object != null) {
                ((LinearLayout) object).addView(child, params);
            }
        });
    }

    public void removeAllViews() {
        runOutUiThread(() -> {
            if (object != null) {
                ((LinearLayout) object).removeAllViews();
            }
        });
    }
}
