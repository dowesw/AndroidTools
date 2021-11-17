package com.lymytz.android.thread;

import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MySwipeRefreshLayout extends MyView {

    public MySwipeRefreshLayout(SwipeRefreshLayout object, Context context) {
        super(object, context);
    }

    public void setRefreshing(boolean refreshing) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((SwipeRefreshLayout) object).setRefreshing(refreshing);
                }
            } catch (Exception ex) {
                Logger.getLogger(MySwipeRefreshLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
