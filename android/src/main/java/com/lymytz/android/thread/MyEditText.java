package com.lymytz.android.thread;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.ColorInt;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyEditText extends MyView {

    public MyEditText(EditText object, Context context) {
        super(object, context);
    }

    public void setEnabled(boolean enabled) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((EditText) object).setEnabled(enabled);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyEditText.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setText(int resid) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((EditText) object).setText(resid);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyEditText.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTextColor(@ColorInt int color) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((EditText) object).setTextColor(color);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyEditText.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setText(CharSequence text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((EditText) object).setText(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyEditText.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
