package com.lymytz.android.thread;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLinearLayout extends MyView {

    public MyLinearLayout(LinearLayout object, Context context) {
        super(object, context);
    }

    public int getChildCount() {
        return object != null ? ((LinearLayout) object).getChildCount() : 0;
    }

    public final <T extends View> T findViewById(int id) {
        return object != null ? ((LinearLayout) object).findViewById(id) : null;
    }

    public void setOrientation(@LinearLayoutCompat.OrientationMode int orientation) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((LinearLayout) object).setOrientation(orientation);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addView(View child) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((LinearLayout) object).addView(child);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((LinearLayout) object).addView(child, params);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addView(View child, int index) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((LinearLayout) object).addView(child, index);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void removeView(View view) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((LinearLayout) object).removeView(view);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void removeViewAt(int index) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((LinearLayout) object).removeViewAt(index);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void removeAllViews() {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((LinearLayout) object).removeAllViews();
                }
            } catch (Exception ex) {
                Logger.getLogger(MyLinearLayout.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
