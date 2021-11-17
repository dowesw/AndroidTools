package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;
import android.widget.TableLayout;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTableLayout extends MyView {

    public MyTableLayout(TableLayout object, Context context) {
        super(object, context);
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TableLayout) object).addView(child);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTableLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addView(View child, TableLayout.LayoutParams params) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TableLayout) object).addView(child, params);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTableLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
