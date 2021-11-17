package com.lymytz.android.thread;

import android.content.Context;
import android.widget.RatingBar;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyRatingBar extends MyView {

    public MyRatingBar(RatingBar object, Context context) {
        super(object, context);
    }

    public void setRating(float rating) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((RatingBar) object).setRating(rating);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyRatingBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
