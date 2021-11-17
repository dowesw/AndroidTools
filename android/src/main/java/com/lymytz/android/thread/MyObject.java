package com.lymytz.android.thread;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.ContextThemeWrapper;

import com.lymytz.android.tools.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyObject {
    Object object;
    Context context;

    public Object getObject() {
        return object;
    }

    public MyObject(Object object, Context context) {
        this.object = object;
        this.context = context;
    }

    protected void runOutUiThread(Runnable runnable) {
        try {
            if (context == null) {
                return;
            }
            Activity activity = Utils.unwrap(context);
            if (activity != null) {
                activity.runOnUiThread(runnable);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
