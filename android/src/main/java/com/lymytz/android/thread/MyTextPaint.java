package com.lymytz.android.thread;

import android.content.Context;
import android.text.TextPaint;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTextPaint extends MyObject {

    public MyTextPaint(TextPaint object, Context context) {
        super(object, context);
    }

    public void setStrikeThruText(boolean strikeThruText) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextPaint) object).setStrikeThruText(strikeThruText);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextPaint.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
