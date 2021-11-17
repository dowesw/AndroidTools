package com.lymytz.android.thread;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyListView extends MyView {

    public MyListView(ListView object, Context context) {
        super(object, context);
    }

    public void setAdapter(ListAdapter adapter) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((ListView) object).setAdapter(adapter);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyListView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
