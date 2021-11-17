package com.lymytz.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import java.io.Serializable;

public class MyScrollView extends ScrollView implements Serializable {

    public interface OnScrollChangedListener {
        // Developer must implement these methods.
        public void onScrollStart();

        public void onScrollEnd();
    }

    public interface OnEndScrollListener {
        public void onEndScroll();
    }

    public interface OnBottomReachedListener {
        public void onBottomReached();
    }

    public OnScrollChangedListener mOnScrollListener;
    public OnEndScrollListener mOnEndScrollListener;
    public OnBottomReachedListener mListener;

    public MyScrollView(Context context) {
        this(context, null, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollChangedListener(
            OnScrollChangedListener onScrollChangedListener) {
        this.mOnScrollListener = onScrollChangedListener;
    }

    public OnEndScrollListener getOnEndScrollListener() {
        return mOnEndScrollListener;
    }

    public void setOnEndScrollListener(OnEndScrollListener mOnEndScrollListener) {
        this.mOnEndScrollListener = mOnEndScrollListener;
    }

    public OnBottomReachedListener getOnBottomReachedListener() {
        return mListener;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        mListener = onBottomReachedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        View view = (View) getChildAt(getChildCount() - 1);
        int diff = (view.getBottom() - (getHeight() + getScrollY()));
        if (diff == 0 && mListener != null) {
            mListener.onBottomReached();
        }
        super.onScrollChanged(x, y, oldX, oldY);
    }
}
