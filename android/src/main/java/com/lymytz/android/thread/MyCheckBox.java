package com.lymytz.android.thread;

import android.content.Context;
import android.widget.CheckBox;

import androidx.annotation.ColorInt;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyCheckBox extends MyView {

    public MyCheckBox(CheckBox object, Context context) {
        super(object, context);
    }

    public void setText(int resid) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((CheckBox) object).setText(resid);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyCheckBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTextColor(@ColorInt int color) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((CheckBox) object).setTextColor(color);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyCheckBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setText(CharSequence text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((CheckBox) object).setText(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyCheckBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setChecked(boolean checked) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((CheckBox) object).setChecked(checked);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyCheckBox.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
