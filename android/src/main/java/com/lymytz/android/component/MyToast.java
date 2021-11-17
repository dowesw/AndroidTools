package com.lymytz.android.component;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.lymytz.android.tools.Utils;

public class MyToast {
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;

    static Context context;

    static Toast toast;
    static MyToast instance;

    public static MyToast makeText(Context context, CharSequence text, int duration) {
        MyToast.context = context;
        if (toast != null) {
            toast.cancel();
        }
        Activity activity = Utils.unwrap(context);
        if (activity == null) {
            return instance;
        }
        activity.runOnUiThread(() -> toast = Toast.makeText(context, text, duration));
        if (instance == null) {
            instance = new MyToast();
        }
        return instance;
    }

    public static MyToast makeText(Context context, int resId, int duration) {
        MyToast.context = context;
        if (toast != null) {
            toast.cancel();
        }
        Activity activity = Utils.unwrap(context);
        if (activity == null) {
            return instance;
        }
        activity.runOnUiThread(() -> toast = Toast.makeText(context, resId, duration));
        if (instance == null) {
            instance = new MyToast();
        }
        return instance;
    }

    public void show() {
        if (toast != null ? context != null : false) {
            Activity activity = Utils.unwrap(context);
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(() -> toast.show());
        }
    }
}
