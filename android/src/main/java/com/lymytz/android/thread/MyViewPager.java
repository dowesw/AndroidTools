package com.lymytz.android.thread;

import android.content.Context;
import android.widget.RatingBar;

import androidx.viewpager.widget.ViewPager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyViewPager extends MyView {

    public MyViewPager(ViewPager object, Context context) {
        super(object, context);
    }

    public void setCurrentItem(int item) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ViewPager) object).setCurrentItem(item);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyViewPager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
