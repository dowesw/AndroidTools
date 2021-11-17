package com.lymytz.android.thread;

import android.content.Context;
import android.widget.ProgressBar;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyProgressBar extends MyView {

    public MyProgressBar(ProgressBar object, Context context) {
        super(object, context);
    }

    public void setMax(int max) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ProgressBar) object).setMax(max);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setProgress(int progress) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ProgressBar) object).setProgress(progress);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
