package com.lymytz.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.lymytz.android.R;
import com.lymytz.android.thread.MyTextView;
import com.lymytz.android.tools.Utils;

public class MyItem extends LinearLayout {
    Drawable item_image = null;
    Drawable item_indicator = null;
    String item_titre = "";
    String item_sous_titre = "";
    String item_nombre = "";
    @ColorInt
    int item_color_text = -1;
    int item_nombre_position = TextPosition.LEFT;

    FrameLayout blog_right;
    ImageView image;
    ImageView indicator;
    TextView txt_titre;
    TextView txt_sous_titre;
    TextView txt_nombre_left;
    TextView txt_nombre_right;
    LinearLayout sous;

    IndicatorListener indicatorListener;

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_item, this);

        image = findViewById(R.id.image);
        indicator = findViewById(R.id.indicator);
        blog_right = findViewById(R.id.blog_right);
        txt_titre = findViewById(R.id.txt_titre);
        txt_sous_titre = findViewById(R.id.txt_sous_titre);
        txt_nombre_left = findViewById(R.id.txt_nombre_left);
        txt_nombre_right = findViewById(R.id.txt_nombre_right);
        sous = findViewById(R.id.sous);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyItem);
            item_image = typedArray.getDrawable(R.styleable.MyItem_item_image);
            item_indicator = typedArray.getDrawable(R.styleable.MyItem_item_indicator);
            item_titre = typedArray.getString(R.styleable.MyItem_item_titre);
            item_sous_titre = typedArray.getString(R.styleable.MyItem_item_sous_titre);
            item_nombre = typedArray.getString(R.styleable.MyItem_item_nombre);
            item_nombre_position = typedArray.getInt(R.styleable.MyItem_item_nombre_position, 0);
            item_color_text = typedArray.getColor(R.styleable.MyItem_item_color_text, 0xff000000);
            typedArray.recycle();

            if (item_image != null) {
                image.setImageDrawable(item_image);
                image.setVisibility(View.VISIBLE);
            } else {
                image.setVisibility(View.GONE);
            }
            if (item_indicator != null) {
                indicator.setImageDrawable(item_indicator);
                indicator.setVisibility(View.VISIBLE);
            } else {
                indicator.setVisibility(View.GONE);
            }
            txt_titre.setText(item_titre);
            txt_sous_titre.setText(item_sous_titre);
            txt_nombre_left.setText(item_nombre);
            txt_nombre_right.setText(item_nombre);
            txt_nombre_left.setTextColor(item_color_text);
            txt_nombre_right.setTextColor(item_color_text);
            if (Utils.asString(item_nombre)) {
                if (item_nombre_position == TextPosition.RIGHT) {
                    txt_nombre_right.setVisibility(View.VISIBLE);
                    indicator.setVisibility(View.GONE);
                } else {
                    txt_nombre_left.setVisibility(View.VISIBLE);
                    image.setVisibility(View.GONE);
                }
            } else {
                txt_nombre_left.setVisibility(View.GONE);
                txt_nombre_right.setVisibility(View.GONE);
            }
            txt_sous_titre.setVisibility(Utils.asString(item_sous_titre) ? View.VISIBLE : View.GONE);
        }
        setVisibility(View.VISIBLE);
    }

    public MyItem(Context context) {
        super(context);
        init(context, null);
    }

    public MyItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MyItem(Context context, String titre, String sous_titre, int color, int resource, String nombre, int indicator, int position) {
        super(context);
        init(context, null);
        txt_titre.setText(titre);
        txt_sous_titre.setText(sous_titre);
        txt_nombre_left.setText(nombre);
        txt_nombre_right.setText(nombre);
        if (color > -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                txt_nombre_left.setTextColor(context.getColor(color));
                txt_nombre_right.setTextColor(context.getColor(color));
            } else {
                txt_nombre_left.setTextColor(context.getResources().getColor(color));
                txt_nombre_right.setTextColor(context.getResources().getColor(color));
            }
        }
        if (resource > -1) {
            image.setImageResource(resource);
        } else if (resource == -2) {
            image.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.INVISIBLE);
        }
        if (indicator > -1) {
            this.indicator.setImageResource(indicator);
        } else {
            this.indicator.setVisibility(View.INVISIBLE);
        }
        if (Utils.asString(nombre)) {
            if (position == TextPosition.RIGHT) {
                txt_nombre_right.setVisibility(View.VISIBLE);
                this.indicator.setVisibility(View.GONE);
            } else {
                txt_nombre_left.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
            }
        } else {
            txt_nombre_left.setVisibility(View.GONE);
            txt_nombre_right.setVisibility(View.GONE);
        }
        txt_sous_titre.setVisibility(Utils.asString(sous_titre) ? View.VISIBLE : View.GONE);
        setIndicator(indicator);

        this.indicator.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indicatorListener != null) {
                    indicatorListener.onClick(v);
                }
            }
        });
    }

    public MyItem(Context context, String titre, int color, String nombre, int indicator, int position) {
        this(context, titre, "", color, -1, nombre, indicator, position);
    }

    public MyItem(Context context, int titre, int color, String nombre, int indicator, int position) {
        this(context, (context != null ? context.getString(titre) : ""), "", color, -1, nombre, indicator, position);
    }

    public MyItem(Context context, int titre, int resource, String nombre) {
        this(context, (context != null ? context.getString(titre) : ""), "", -1, resource, nombre, -1, TextPosition.RIGHT);
    }

    public MyItem(Context context, int titre, int resource, int indicator) {
        this(context, (context != null ? context.getString(titre) : ""), "", -1, resource, "", indicator, TextPosition.LEFT);
    }

    public MyItem(Context context, int titre, int resource, int indicator, IndicatorListener indicatorListener) {
        this(context, titre, resource, indicator);
        this.indicatorListener = indicatorListener;
    }

    public MyItem(Context context, String titre, int resource, int indicator) {
        this(context, titre, "", -1, resource, "", indicator, TextPosition.LEFT);
    }

    public MyItem(Context context, String titre, String sous_titre, int resource) {
        this(context, titre, sous_titre, -1, resource, "", -1, TextPosition.LEFT);
    }

    public MyItem(Context context, int titre, String sous_titre, int resource) {
        this(context, (context != null ? context.getString(titre) : ""), sous_titre, -1, resource, "", -1, TextPosition.LEFT);
    }

    public MyItem(Context context, int titre, String sous_titre) {
        this(context, (context != null ? context.getString(titre) : ""), sous_titre, -1, -1, "", -1, TextPosition.LEFT);
    }

    public void setIndicator(int indicator) {
        if (indicator > -1) {
            this.indicator.setImageResource(indicator);
        }
    }

    public void setTextRight(String text, int background) {
        if (Utils.asString(text)) {
            this.txt_nombre_right.setText(text);
            this.txt_nombre_right.setVisibility(View.VISIBLE);
            if (background > -1) {
                this.txt_nombre_right.setBackgroundResource(background);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.txt_nombre_right.setTextColor(getContext().getColor(android.R.color.white));
                } else {
                    this.txt_nombre_right.setTextColor(getContext().getResources().getColor(android.R.color.white));
                }
            }
            this.indicator.setVisibility(View.GONE);
        } else {
            this.txt_nombre_right.setVisibility(View.GONE);
            this.indicator.setVisibility(View.VISIBLE);
        }
    }

    public void setBorderRight(int background) {
        if (background > -1) {
            this.blog_right.setBackgroundResource(background);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.blog_right.setBackground(null);
            } else {
                this.blog_right.setBackgroundDrawable(null);
            }
        }
    }

    public LinearLayout getSous() {
        return sous;
    }

    public void setNombre(String titre) {
        new MyTextView(this.txt_nombre_right, getContext()).setText(titre);
        new MyTextView(this.txt_nombre_left, getContext()).setText(titre);
    }

    public void setTitre(int titre) {
        this.txt_titre.setText(titre);
    }

    public void setTitre(String titre) {
        setTitre(titre, false);
    }

    public void setTitre(String titre, boolean bold) {
        this.txt_titre.setText(titre);
        if (bold) {
            txt_titre.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    public void setBoldTitre(boolean bold) {
        txt_titre.setTypeface(bold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    }

    public String getTitre() {
        return this.txt_titre.getText().toString();
    }

    public void setSousTitre(String titre) {
        this.txt_sous_titre.setText(titre);
    }

    public class TextPosition {
        public final static int LEFT = 0;
        public final static int RIGHT = 1;
    }

    public interface IndicatorListener {

        public void onClick(View v);
    }
}
