package com.lymytz.android.thread;

import android.content.Context;
import android.widget.Button;

import androidx.annotation.ColorInt;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyButton extends MyView {

    public MyButton(Button object, Context context) {
        super(object, context);
    }

    public void setText(int resid) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((Button) object).setText(resid);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTextColor(@ColorInt int color) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((Button) object).setTextColor(color);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setText(CharSequence text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((Button) object).setText(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
