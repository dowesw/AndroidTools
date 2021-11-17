package com.lymytz.android.thread;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyRecyclerView<T extends Serializable> extends MyView {

    public MyRecyclerView(com.lymytz.android.view.MyRecyclerView object, Context context) {
        super(object, context);
    }

    public void setAdapter(ArrayAdapter adapter) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyRecyclerView) object).setAdapter(adapter);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addAll(List<T> objects){
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyRecyclerView) object).addAll(objects);
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
                    ((com.lymytz.android.view.MyRecyclerView) object).setLayoutParams(params);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
