package com.lymytz.android.thread;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyRecycleView extends MyView {

    public MyRecycleView(RecyclerView object, Context context) {
        super(object, context);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((RecyclerView) object).setAdapter(adapter);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((RecyclerView) object).setLayoutParams(params);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
