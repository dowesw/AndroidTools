package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyView extends MyObject {

    public View getObject() {
        return (View) object;
    }

    public MyView(View object, Context context) {
        super(object, context);
    }

    public void setEnabled(final boolean enabled) {
        runOutUiThread(() -> {
            try {
                if (object != null ? object instanceof View : false) {
                    getObject().setEnabled(enabled);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setVisibility(final int visibility) {
        runOutUiThread(() -> {
            try {
                if (object != null ? object instanceof View : false) {
                    getObject().setVisibility(visibility);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTag(Object tag) {
        runOutUiThread(() -> {
            try {
                if (object != null ? object instanceof View : false) {
                    getObject().setTag(tag);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyViewFlipper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
