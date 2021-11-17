package com.lymytz.android.thread;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyEditTextView extends MyView {

    public MyEditTextView(com.lymytz.android.view.MyEditTextView object, Context context) {
        super(object, context);
    }

    public void setText(String text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyEditTextView) object).setText(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyEditTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
