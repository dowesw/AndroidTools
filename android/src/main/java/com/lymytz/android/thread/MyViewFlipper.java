package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;
import android.widget.ViewFlipper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyViewFlipper extends MyView {

    public MyViewFlipper(ViewFlipper object, Context context) {
        super(object, context);
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ViewFlipper) object).addView(child);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyViewFlipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void removeAllViews() {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ViewFlipper) object).removeAllViews();
                }
            } catch (Exception ex) {
                Logger.getLogger(MyViewFlipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
