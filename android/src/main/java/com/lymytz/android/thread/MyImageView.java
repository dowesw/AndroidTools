package com.lymytz.android.thread;

import android.graphics.Bitmap;
import android.content.Context;
import android.widget.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyImageView extends MyView {

    public MyImageView(ImageView object, Context context) {
        super(object, context);
    }

    public void setImageResource(int resId) {
        try {
            runOutUiThread(() -> {
                if (object != null) {
                    ((ImageView) object).setImageResource(resId);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(MyImageView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setImageBitmap(Bitmap bm) {
        try {
            runOutUiThread(() -> {
                if (object != null) {
                    ((ImageView) object).setImageBitmap(bm);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(MyImageView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
