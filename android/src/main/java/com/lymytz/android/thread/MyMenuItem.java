package com.lymytz.android.thread;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lymytz.android.tools.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMenuItem {
    MenuItem object;
    Context context;

    public MenuItem getObject() {
        return object;
    }

    public MyMenuItem(MenuItem object, Context context) {
        this.context = context;
        this.object = object;
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

    public void setEnabled(final boolean enabled) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    getObject().setEnabled(enabled);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setVisible(final boolean visible) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    getObject().setVisible(visible);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public SubMenu getSubMenu() {
        return getObject().getSubMenu();
    }
}
