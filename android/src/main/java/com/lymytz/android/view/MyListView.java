package com.lymytz.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lymytz.android.adapter.CustomArrayAdapter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyListView extends ListView implements AbsListView.OnScrollListener {
    private int mOffset = 0;

    public interface OnBottomReachedListener {
        public void onBottomReached();
    }

    public OnBottomReachedListener mListener;

    public MyListView(Context context) {
        this(context, null, 0);
        defineScrolling();
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        defineScrolling();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        defineScrolling();
    }

    /**
     * Defines scrolling behaviour by subscribing a scroll listener.
     */
    private void defineScrolling() {
        this.setOnScrollListener(this);
    }

    /**
     * Removes internal scroll listener.
     */
    public void reset() {
        this.setOnScrollListener(null);
    }

    public OnBottomReachedListener getOnBottomReachedListener() {
        return mListener;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        mListener = onBottomReachedListener;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null ? adapter instanceof CustomArrayAdapter : false) {
            ((CustomArrayAdapter) adapter).setSeparator(true);
        }
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        try {
            View view = (View) getChildAt(getChildCount() - 1);
            int diff = 0;
            if (view != null) {
                diff = (view.getBottom() - (getHeight() + getScrollY()));
            }
            if (diff == 0 && mListener != null) {
//            mListener.onBottomReached();
            }
        } catch (Exception ex) {
            Logger.getLogger(MyListView.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.onScrollChanged(x, y, oldX, oldY);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        try {
            int threshold = 1;
            int count = getCount();
            if (scrollState == SCROLL_STATE_IDLE && mListener != null) {
                if (getLastVisiblePosition() >= count - threshold) {
                    mListener.onBottomReached();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MyListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        try {
            int position = firstVisibleItem + visibleItemCount;
            int limit = totalItemCount - mOffset;
            // Check if bottom has been reached
            if (position >= limit && totalItemCount > 0) {
                if (mListener != null) {
//                mListener.onBottomReached();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MyListView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
