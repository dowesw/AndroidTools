package com.lymytz.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.lymytz.android.R;
import com.lymytz.android.tools.Utils;

public class MyPanel extends LinearLayout {
    String panel_title = "";
    String panel_text_right = "";
    boolean panel_closable = true;
    boolean panel_checked = false;
    boolean panel_checked_visible = true;
    boolean panel_text_right_alway_visible = false;
    Drawable panel_image = null;
    Drawable panel_header_background = null;
    Drawable panel_content_background = null;
    float panel_header_height = -1;
    @ColorInt
    int panel_text_right_color = -1;
    @ColorInt
    int panel_item_color = -1;

    RelativeLayout btn_title;
    ImageView image;
    TextView header;
    TextView title;
    TextView text_right;
    CheckBox checked;
    LinearLayout content;
    LinearLayout items;

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_panel, this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyPanel);
            panel_title = typedArray.getString(R.styleable.MyPanel_panel_title);
            panel_text_right = typedArray.getString(R.styleable.MyPanel_panel_text_right);
            panel_checked = typedArray.getBoolean(R.styleable.MyPanel_panel_checked, panel_checked);
            panel_closable = typedArray.getBoolean(R.styleable.MyPanel_panel_closable, panel_closable);
            panel_checked_visible = typedArray.getBoolean(R.styleable.MyPanel_panel_checked_visible, panel_checked_visible);
            panel_text_right_alway_visible = typedArray.getBoolean(R.styleable.MyPanel_panel_text_right_alway_visible, panel_text_right_alway_visible);
            panel_image = typedArray.getDrawable(R.styleable.MyPanel_panel_image);
            panel_text_right_color = typedArray.getColor(R.styleable.MyPanel_panel_text_right_color, 0xff000000);
            panel_item_color = typedArray.getColor(R.styleable.MyPanel_panel_item_color, 0xffffffff);
            panel_header_background = typedArray.getDrawable(R.styleable.MyPanel_panel_header_background);
            panel_content_background = typedArray.getDrawable(R.styleable.MyPanel_panel_content_background);
            panel_header_height = typedArray.getDimension(R.styleable.MyPanel_panel_header_height, panel_header_height);
            typedArray.recycle();
        }

        btn_title = findViewById(R.id.btn_title);
        image = findViewById(R.id.image);
        header = findViewById(R.id.header);
        title = findViewById(R.id.title);
        text_right = findViewById(R.id.text_right);
        checked = findViewById(R.id.checked);
        content = findViewById(R.id.content);
        items = findViewById(R.id.items);

        setClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewParent parent = getParent();
                if (parent != null ? parent instanceof MyGroupPanel : false) {
                    ((MyGroupPanel) parent).click(MyPanel.this);
                } else {
                    click();
                }
            }
        });

        title.setText(panel_title);
        text_right.setText(panel_text_right);
        checked.setChecked(panel_checked);
        checked.setVisibility(panel_checked_visible ? View.VISIBLE : View.GONE);
        content.setVisibility(panel_closable ? panel_checked ? View.VISIBLE : View.GONE : View.VISIBLE);
        text_right.setTextColor(panel_text_right_color);
        items.setBackgroundColor(panel_item_color);
        onDefinedVisibleTextRight();
        if (panel_image != null) {
            image.setImageDrawable(panel_image);
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }
        if (panel_header_height > -1) {
            btn_title.getLayoutParams().height = (int) panel_header_height;
        }
        if (panel_header_background != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btn_title.setBackground(panel_header_background);
            } else {
                btn_title.setBackgroundDrawable(panel_header_background);
            }
        }
        if (panel_content_background != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                content.setBackground(panel_content_background);
            } else {
                content.setBackgroundDrawable(panel_content_background);
            }
        }
    }

    public MyPanel(Context context) {
        super(context);
        init(context, null);
    }

    public MyPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MyPanel(Context context, String header, String title, int image) {
        super(context);
        init(context, null);
        this.header.setText(header);
        this.title.setText(title);
        this.header.setVisibility(Utils.asString(header) ? View.VISIBLE : View.GONE);
        if (image > -1) {
            this.image.setImageResource(image);
            this.image.setVisibility(View.VISIBLE);
        } else {
            this.image.setVisibility(View.GONE);
        }
    }

    public MyPanel(Context context, String title, int image) {
        this(context, "", title, image);
    }

    public void click() {
        getContent().setVisibility(panel_closable ? getContent().getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE : View.VISIBLE);
        onDefinedVisibleTextRight();
    }

    private void onDefinedVisibleTextRight() {
        if (checked.getVisibility() != View.VISIBLE ? Utils.asString(text_right.getText().toString()) : false) {
            text_right.setVisibility(panel_closable ? content.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE : View.VISIBLE);
        }
        text_right.setVisibility(panel_text_right_alway_visible ? View.VISIBLE : text_right.getVisibility());
    }

    public LinearLayout getContent() {
        return content;
    }

    public CheckBox getChecked() {
        return checked;
    }

    public boolean isChecked() {
        return this.checked.isChecked();
    }

    public void setChecked(boolean checked) {
        this.checked.setChecked(checked);
    }

    public TextView getTextRight() {
        return text_right;
    }

    public void addView(View view) {
        items.addView(view);
    }

    public void removeAllViews() {
        items.removeAllViews();
    }

    public void setPanelImage(int panel_image) {
        this.image.setImageResource(panel_image);
        this.image.setVisibility(View.VISIBLE);
    }

    public void setBorderImage(int background) {
        if (background != -1) {
            this.image.setBackgroundResource(background);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.image.setBackground(null);
            } else {
                this.image.setBackgroundDrawable(null);
            }
        }
    }

    public void setTitle(int resId) {
        this.title.setText(resId);
    }

    public void setTextRight(String text) {
        setTextRight(text, Utils.asString(text));
    }

    public void setTextRight(String text, boolean visible) {
        text_right.setText(text);
        text_right.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setClickListener(OnClickListener clickListener) {
        btn_title.setOnClickListener(clickListener);
        checked.setOnClickListener(clickListener);
    }

    public void setLongClickListener(OnLongClickListener clickListener) {
        btn_title.setOnLongClickListener(clickListener);
        checked.setOnLongClickListener(clickListener);
    }

    public void setRightClickListener(OnClickListener clickListener) {
        text_right.setOnClickListener(clickListener);
    }
}
