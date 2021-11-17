package com.lymytz.android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import com.lymytz.android.R;


@SuppressLint("AppCompatCustomView")
public class MyEditTextView extends LinearLayout {
    Type edit_text_type = Type.TYPE_TEXT;
    String edit_text_value = "";
    String edit_text_hint = "";
    Drawable edit_text_border = null;
    Drawable edit_text_image_done = null;
    Drawable edit_text_image_edit = null;
    float edit_text_height = 45;
    float edit_text_size = 14;
    @ColorInt
    int edit_text_color = -1;

    RelativeLayout bloc_text;
    EditText edit;
    TextView text;
    ImageView btn_edit;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyEditTextView(Context context) {
        super(context);
        init(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_edit_text_view, this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEditTextView);
            edit_text_type = Type.values()[typedArray.getInt(R.styleable.MyEditTextView_edit_text_type, 0)];
            edit_text_hint = typedArray.getString(R.styleable.MyEditTextView_edit_text_hint);
            edit_text_value = typedArray.getString(R.styleable.MyEditTextView_edit_text_value);
            edit_text_border = typedArray.getDrawable(R.styleable.MyEditTextView_edit_text_border);
            edit_text_image_done = typedArray.getDrawable(R.styleable.MyEditTextView_edit_text_image_done);
            edit_text_image_edit = typedArray.getDrawable(R.styleable.MyEditTextView_edit_text_image_edit);
            edit_text_height = typedArray.getDimension(R.styleable.MyEditTextView_edit_text_height, edit_text_height);
            edit_text_size = typedArray.getDimension(R.styleable.MyEditTextView_edit_text_size, edit_text_size);
            edit_text_color = typedArray.getColor(R.styleable.MyEditTextView_edit_text_color, 0xff000000);
            typedArray.recycle();
        }
        text = findViewById(R.id.text);
        edit = findViewById(R.id.edit);
        btn_edit = findViewById(R.id.btn_edit);
        bloc_text = findViewById(R.id.bloc_text);

        text.setHint(edit_text_hint);
        edit.setHint(edit_text_hint);

        setText(edit_text_value);

        text.setTextColor(edit_text_color);
        text.setTextSize(edit_text_size);
        edit.setBackground(edit_text_border);
        edit.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) edit_text_height));
        if (edit_text_image_done != null) {
            edit.setCompoundDrawablesWithIntrinsicBounds(null, null, edit_text_image_done, null);
        }
        if (edit_text_image_edit != null) {
            btn_edit.setImageDrawable(edit_text_image_edit);
        }
        if (edit_text_type == Type.TYPE_TEXT) {
            text.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        } else {
            edit.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
        }

        btn_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bloc_text.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
            }
        });
        edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (edit.getCompoundDrawables()[2] != null) {
                        if (motionEvent.getX() >= (edit.getRight() - edit.getLeft() - edit.getCompoundDrawables()[2].getBounds().width())) {
                            edit.setVisibility(View.GONE);
                            bloc_text.setVisibility(View.VISIBLE);
                        }
                    }
                }
                return false;
            }
        });
    }

    public String getText() {
        return text.getText().toString();
    }

    public void setText(String value) {
        text.setText(value);
        edit.setText(value);
    }

    enum Type {
        TYPE_TEXT, TYPE_EDIT;
    }
}
