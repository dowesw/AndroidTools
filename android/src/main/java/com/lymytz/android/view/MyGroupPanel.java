package com.lymytz.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.lymytz.android.R;
import com.lymytz.android.tools.Messages;
import com.lymytz.android.tools.Utils;

public class MyGroupPanel extends LinearLayout {
    String group_panel_title = "";
    String group_panel_header_title = "";
    @ColorInt
    int group_panel_title_color = -1;
    int group_panel_position_active = -1;
    Drawable group_panel_header_image = null;

    LinearLayout header;
    TextView header_title;
    ImageView header_image;
    TextView title;

    private int child_defined_size = 1;

    private int positionItem;
    private View currentItem;
    private OnItemChangeListener mOnItemChangeListener;

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_group_panel, this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyGroupPanel);
            group_panel_title = typedArray.getString(R.styleable.MyGroupPanel_group_panel_title);
            group_panel_header_title = typedArray.getString(R.styleable.MyGroupPanel_group_panel_header_title);
            group_panel_title_color = typedArray.getColor(R.styleable.MyGroupPanel_group_panel_title_color, 0xff000000);
            group_panel_position_active = typedArray.getInt(R.styleable.MyGroupPanel_group_panel_position_active, group_panel_position_active);
            group_panel_header_image = typedArray.getDrawable(R.styleable.MyGroupPanel_group_panel_header_image);
            typedArray.recycle();
        }
        try {
            header = findViewById(R.id.header);
            header_title = findViewById(R.id.header_title);
            header_image = findViewById(R.id.header_image);
            title = findViewById(R.id.title);

            title.setText(group_panel_title);
            header_title.setText(group_panel_header_title);
            header_image.setImageDrawable(group_panel_header_image);

            if (group_panel_title_color > -1) {
                title.setBackgroundColor(group_panel_title_color);
            }

            title.setVisibility(Utils.asString(group_panel_title) ? View.VISIBLE : View.GONE);
            header_title.setVisibility(Utils.asString(group_panel_header_title) ? View.VISIBLE : View.GONE);
            header_image.setVisibility(group_panel_header_image != null ? View.VISIBLE : View.GONE);
            boolean visible_header = Utils.asString(group_panel_title) || Utils.asString(group_panel_header_title) || group_panel_header_image != null;
            header.setVisibility(visible_header ? View.VISIBLE : View.GONE);
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
        setOrientation(LinearLayout.VERTICAL);
    }

    public MyGroupPanel(Context context) {
        super(context);
        init(context, null);
    }

    public MyGroupPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyGroupPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MyGroupPanel(Context context, String text, int textColor) {
        super(context);
        init(context, null);
        setTitle(text, textColor);
    }

    public MyGroupPanel(Context context, String header_title, String title, int header_image) {
        super(context);
        init(context, null);
        setTitles(header_title, title, header_image);
    }

    public void click(MyPanel current) {
        try {
            if (current != null) {
                MyPanel child;
                int position = 0;
                int count_panel = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    if (getChildAt(i) instanceof MyPanel) {
                        child = (MyPanel) getChildAt(i);
                        child.setChecked(false);
                        child.getContent().setVisibility(View.GONE);
                        if (child.equals(current)) {
                            position = count_panel;
                        }
                        count_panel++;
                    }
                }
                current.setChecked(true);
                current.getContent().setVisibility(current.getContent().isEnabled() ? View.VISIBLE : View.GONE);
                if (mOnItemChangeListener != null) {
                    mOnItemChangeListener.onChange(current, position);
                }
                this.currentItem = current;
                this.positionItem = position;
            }
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public void setTitles(String headerTitle, String title, int header_image) {
        this.header_title.setText(headerTitle);
        this.header_title.setVisibility(Utils.asString(headerTitle) ? View.VISIBLE : View.GONE);

        this.title.setText(title);
        this.title.setVisibility(Utils.asString(title) ? View.VISIBLE : View.GONE);

        if (header_image > -1) {
            this.header_image.setImageResource(header_image);
            this.header_image.setVisibility(View.VISIBLE);
        }
        boolean visible_header = Utils.asString(headerTitle) || Utils.asString(title) || header_image > -1;
        header.setVisibility(visible_header ? View.VISIBLE : View.GONE);
    }

    public void setTitle(String title, int color) {
        this.title.setText(title);
        if (color > -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.title.setTextColor(getContext().getColor(color));
            } else {
                this.title.setTextColor(getContext().getResources().getColor(color));
            }
        }
        this.title.setVisibility(Utils.asString(title) ? View.VISIBLE : View.GONE);
        header.setVisibility(Utils.asString(title) ? View.VISIBLE : View.GONE);
    }

    public void setBorderHeaderImage(int background) {
        this.header_image.setBackgroundResource(background);
    }

    public void removeAllViews() {
        super.removeAllViews();
        ViewParent parent = header.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(header);
        }
        addView(header);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        if (group_panel_position_active > -1 ? group_panel_position_active < getChildCountDefined() : false) {
            setCurrentItem(group_panel_position_active);
        }
    }

    public void addHeader(MyItemHeaderPanel header) {
        this.header.addView(header);
    }

    public OnItemChangeListener getOnItemChangeListener() {
        return mOnItemChangeListener;
    }

    public void setOnItemChangeListener(OnItemChangeListener mOnItemChangeListener) {
        this.mOnItemChangeListener = mOnItemChangeListener;
    }

    public int getPositionItem() {
        return positionItem;
    }

    public View getCurrentItem() {
        return currentItem;
    }

    public int getChildCountDefined() {
        return super.getChildCount() - child_defined_size;
    }

    public boolean isChildChecked() {
        MyPanel child;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof MyPanel) {
                child = (MyPanel) getChildAt(i);
                if (child.isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setCurrentItem(int positionItem) {
        if (positionItem < getChildCountDefined()) {
            setCurrentItem(getChildAt(positionItem + child_defined_size));
        }
    }

    public void setCurrentItem(View currentItem) {
        if (currentItem != null ? currentItem instanceof MyPanel : false) {
            click((MyPanel) currentItem);
        }
    }

    public interface OnItemChangeListener {
        public void onChange(View child, int position);
    }
}
