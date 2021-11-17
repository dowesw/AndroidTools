package com.lymytz.android.thread;

import android.content.Context;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.ListAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyAutoCompleteTextView extends MyView {

    public MyAutoCompleteTextView(AutoCompleteTextView object, Context context) {
        super(object, context);
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((AutoCompleteTextView) object).setAdapter(adapter);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyAutoCompleteTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setThreshold(int threshold) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((AutoCompleteTextView) object).setThreshold(threshold);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyAutoCompleteTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setText(int resid) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((AutoCompleteTextView) object).setText(resid);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyAutoCompleteTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setText(CharSequence text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((AutoCompleteTextView) object).setText(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyAutoCompleteTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
