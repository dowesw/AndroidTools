package com.lymytz.android.thread;

import android.content.Context;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MySpinner extends MyView {

    public MySpinner(Spinner object, Context context) {
        super(object, context);
    }

    public MySpinner(com.lymytz.android.view.MySpinner object, Context context) {
        super(object, context);
    }

    public void setAdapter(SpinnerAdapter adapter) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    if (object instanceof Spinner)
                        ((Spinner) object).setAdapter(adapter);
                    else if (object instanceof com.lymytz.android.view.MySpinner)
                        ((com.lymytz.android.view.MySpinner) object).setAdapter(adapter);
                }
            } catch (Exception ex) {
                Logger.getLogger(MySpinner.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setSelection(int index) {
        runOutUiThread(() -> {
            try {
                if (object != null ? index > -1 : false) {
                    if (object instanceof Spinner)
                        ((Spinner) object).setSelection(index);
                    else if (object instanceof com.lymytz.android.view.MySpinner)
                        ((com.lymytz.android.view.MySpinner) object).setSelection(index);
                }
            } catch (Exception ex) {
                Logger.getLogger(MySpinner.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
