package com.lymytz.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lymytz.android.component.ObjectWrapperForBinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyLinearLayout extends LinearLayout implements Serializable {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Serializable onSaveInstanceStates() {
        List<View> data = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            data.add(getChildAt(i));
        }
        return new ObjectWrapperForBinder(data);
    }

    public void onRestoreInstanceStates(Serializable state) {
        if (state != null ? state instanceof ObjectWrapperForBinder : false) {
            if (((ObjectWrapperForBinder) state).getData() != null ? ((ObjectWrapperForBinder) state).getData() instanceof ArrayList : false) {
                List<View> data = (List<View>) ((ObjectWrapperForBinder) state).getData();
                for (View view : data) {
                    ((ViewGroup) view.getParent()).removeView(view);
                    addView(view);
                }
            }
        }
    }
}
