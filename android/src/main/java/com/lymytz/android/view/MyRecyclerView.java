package com.lymytz.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.lymytz.android.R;
import com.lymytz.android.tools.Messages;
import com.lymytz.android.view.MyScrollView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerView<T extends Serializable> extends MyScrollView {
    int spanCount = 2;
    LinearLayout linearLayout;
    ArrayAdapter adapter;

    public void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_recyclerview, this);
        linearLayout = findViewById(R.id.linearlayout);
    }

    public MyRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void addAll(List<T> objects) {
        try {
            boolean insert = false;
            if (adapter != null && objects != null) {
                List<T> list = new ArrayList<>();
                for (T t : objects) {
                    if (adapter.getPosition(t) < 1) {
                        list.add(t);
                    }
                }
                adapter.addAll(list);
                int count = adapter.getCount();
                LinearLayout line = null;
                if (linearLayout.getChildCount() > 0) {
                    line = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 1);
                }
                if (line == null) {
                    insert = true;
                    line = new LinearLayout(getContext());
                    line.setOrientation(LinearLayout.HORIZONTAL);
                }
                if (line.getChildCount() == spanCount) {
                    line = new LinearLayout(getContext());
                    line.setOrientation(LinearLayout.HORIZONTAL);
                }
                addView(line, (count - (list.size() + 1)), count, insert);
            }

        } catch (Exception e) {
            Messages.Exception(getContext(), e);
        }
    }

    public void setAdapter(ArrayAdapter adapter) {
        try {
            if (this.linearLayout != null) {
                this.linearLayout.removeAllViews();
            }
            this.adapter = adapter;
            if (adapter != null) {
                int count = adapter.getCount();
                LinearLayout line = new LinearLayout(getContext());
                line.setOrientation(LinearLayout.HORIZONTAL);
                addView(line, 0, count, true);
            }
        } catch (Exception e) {
            Messages.Exception(getContext(), e);
        }
    }

    private void addView(LinearLayout line, int indexStart, int indexEnd, boolean insert) {
        for (int i = indexStart; i < indexEnd; i++) {
            View view = adapter.getView(i, null, line);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(2, 2, 2, 2);
            view.setLayoutParams(params);
            line.addView(view);

            if (line.getChildCount() == spanCount) {
                if (insert) {
                    linearLayout.addView(line);
                }
                insert = true;
                line = new LinearLayout(getContext());
                line.setOrientation(LinearLayout.HORIZONTAL);
            }
        }
        if (line.getChildCount() == spanCount) {
            linearLayout.addView(line);
        }
    }

    public void addOnScrollListener(OnBottomReachedListener onBottomReachedListener) {
        this.setOnBottomReachedListener(onBottomReachedListener);
    }

    public void setLayoutManager(int spanCount) {
        this.spanCount = spanCount;
    }

    public void setHasFixedSize(boolean hasFixedSize) {

    }

    public ArrayAdapter getAdapter() {
        return this.adapter;
    }


}
