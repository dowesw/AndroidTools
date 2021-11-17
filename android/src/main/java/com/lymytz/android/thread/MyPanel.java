package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyPanel extends MyView {

    public MyPanel(com.lymytz.android.view.MyPanel object, Context context) {
        super(object, context);
    }

    public void removeAllViews() {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyPanel) object).removeAllViews();
                }
            } catch (Exception ex) {
                Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addView(View view) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyPanel) object).addView(view);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTextRight(String text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyPanel) object).setTextRight(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTextRight(String text, boolean visible) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyPanel) object).setTextRight(text, visible);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
