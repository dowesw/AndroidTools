package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyGroupPanel extends MyView {

    public MyGroupPanel(com.lymytz.android.view.MyGroupPanel object, Context context) {
        super(object, context);
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyGroupPanel) object).addView(child);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void removeAllViews() {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyGroupPanel) object).removeAllViews();
                }
            } catch (Exception ex) {
                Logger.getLogger(MyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setCurrentItem(int positionItem) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyGroupPanel) object).setCurrentItem(positionItem);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
