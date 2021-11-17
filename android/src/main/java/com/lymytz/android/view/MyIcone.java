package com.lymytz.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.lymytz.android.R;

public class MyIcone extends LinearLayout {
    Drawable drawable;
    String text;
    int position;
    int dimens = -1;
    int background = -1;
    int colorText = -1;

    Context context;
    ImageView image;
    RelativeLayout zone_top;
    RelativeLayout zone_bottom;
    TextView top;
    TextView bottom;
    TextView left;
    TextView right;
    TextView top_left;
    TextView bottom_right;
    TextView bottom_left;
    TextView top_right;

    public MyIcone(Context context) {
        super(context);
        this.context = context;
        inflate(context, R.layout.component_icone, this);

        image = findViewById(R.id.image);
        zone_top = findViewById(R.id.zone_top);
        zone_bottom = findViewById(R.id.zone_bottom);
        top = findViewById(R.id.top);
        bottom = findViewById(R.id.bottom);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        top_left = findViewById(R.id.top_left);
        bottom_right = findViewById(R.id.bottom_right);
        bottom_left = findViewById(R.id.bottom_left);
        top_right = findViewById(R.id.top_right);
    }

    private void InitValue(String text, int dimens, int colorText, int background) {
        top.setText(text);
        bottom.setText(text);
        left.setText(text);
        right.setText(text);
        top_left.setText(text);
        bottom_right.setText(text);
        bottom_left.setText(text);
        top_right.setText(text);
        if (colorText > -1) {
            top.setTextColor(context.getResources().getColor(colorText));
            bottom.setTextColor(context.getResources().getColor(colorText));
            left.setTextColor(context.getResources().getColor(colorText));
            right.setTextColor(context.getResources().getColor(colorText));
            top_left.setTextColor(context.getResources().getColor(colorText));
            bottom_right.setTextColor(context.getResources().getColor(colorText));
            bottom_left.setTextColor(context.getResources().getColor(colorText));
            top_right.setTextColor(context.getResources().getColor(colorText));
        }
        if (dimens > -1) {
            top.setTextSize(context.getResources().getDimension(dimens));
            bottom.setTextSize(context.getResources().getDimension(dimens));
            left.setTextSize(context.getResources().getDimension(dimens));
            right.setTextSize(context.getResources().getDimension(dimens));
            top_left.setTextSize(context.getResources().getDimension(dimens));
            bottom_right.setTextSize(context.getResources().getDimension(dimens));
            bottom_left.setTextSize(context.getResources().getDimension(dimens));
            top_right.setTextSize(context.getResources().getDimension(dimens));
        }
        if (background > -1) {
            top.setBackgroundResource(background);
            bottom.setBackgroundResource(background);
            left.setBackgroundResource(background);
            right.setBackgroundResource(background);
            top_left.setBackgroundResource(background);
            bottom_right.setBackgroundResource(background);
            bottom_left.setBackgroundResource(background);
            top_right.setBackgroundResource(background);
        }
    }

    private void InitValue(Drawable drawable, String text, int background) {
        InitValue(text, dimens, colorText, background);
        if (drawable != null) {
            image.setImageDrawable(drawable);
        }
    }

    private void InitVisible(int position) {
        top.setVisibility(position == Position.TOP ? View.VISIBLE : View.GONE);
        bottom.setVisibility(position == Position.BOTTOM ? View.VISIBLE : View.GONE);
        left.setVisibility(position == Position.LEFT ? View.VISIBLE : View.GONE);
        right.setVisibility(position == Position.RIGHT ? View.VISIBLE : View.GONE);
        top_left.setVisibility(position == Position.TOP_LEFT ? (com.lymytz.android.tools.Utils.asString(text) ? View.VISIBLE : View.INVISIBLE) : View.GONE);
        top_right.setVisibility(position == Position.TOP_RIGHT ? (com.lymytz.android.tools.Utils.asString(text) ? View.VISIBLE : View.INVISIBLE) : View.GONE);
        bottom_left.setVisibility(position == Position.BOTTOM_LEFT ? (com.lymytz.android.tools.Utils.asString(text) ? View.VISIBLE : View.INVISIBLE) : View.GONE);
        bottom_right.setVisibility(position == Position.BOTTOM_RIGHT ? (com.lymytz.android.tools.Utils.asString(text) ? View.VISIBLE : View.INVISIBLE) : View.GONE);
        zone_top.setVisibility((position == Position.TOP_RIGHT || position == Position.TOP_LEFT) ? View.VISIBLE : View.GONE);
        zone_bottom.setVisibility((position == Position.BOTTOM_RIGHT || position == Position.BOTTOM_LEFT) ? View.VISIBLE : View.GONE);
    }

    public MyIcone(Context context, Drawable drawable, String text, int dimens, int colorText, int background, int position) {
        this(context);
        this.drawable = drawable;
        this.text = text;
        this.dimens = dimens;
        this.colorText = colorText;
        this.position = position;
        this.background = background;
        InitValue(drawable, text, background);
        InitVisible(position);
    }

    public MyIcone(Context context, int drawable, int text, int dimens, int position) {
        this(context, (drawable > -1 ? context.getResources().getDrawable(drawable) : null), context.getString(text), dimens, -1, -1, position);
    }

    public MyIcone(Context context, int drawable, String text, int position) {
        this(context, (context != null && drawable > -1 ? context.getResources().getDrawable(drawable) : null), text, -1, -1, -1, position);
    }

    public MyIcone(Context context, int drawable, String text, int colorText, int background, int position) {
        this(context, (context != null && drawable > -1 ? context.getResources().getDrawable(drawable) : null), text, -1, colorText, background, position);
    }

    public MyIcone(Context context, Drawable drawable, int text, int position) {
        this(context, drawable, context.getString(text), -1, -1, -1, position);
    }

    public MyIcone(Context context, int drawable, int text, int position) {
        this(context, (context != null && drawable > -1 ? context.getResources().getDrawable(drawable) : null), context.getString(text), -1, -1, -1, position);
    }

    public MyIcone(Context context, int drawable, String text) {
        this(context, (drawable > -1 ? context.getResources().getDrawable(drawable) : null), text, -1, -1, -1, 1);
    }
    public MyIcone(Context context, int drawable, String text,int colorText,int position) {
        this(context, (drawable > -1 ? context.getResources().getDrawable(drawable) : null), text, -1, colorText, -1, position);
    }

    public MyIcone(Context context, int drawable, int text) {
        this(context, (drawable > -1 ? context.getResources().getDrawable(drawable) : null), context.getString(text), -1, -1, -1, 1);
    }

    public MyIcone(Context context, int text) {
        this(context, null, context.getString(text), -1, -1, -1, 1);
    }

    public MyIcone(Context context, String text) {
        this(context, null, text, -1, -1, -1, 1);
    }

    public Drawable getDrawable() {
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());

        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bitmap bitmap = null;
        if (this.getDrawingCache() == null) {
            bitmap = loadLargeBitmapFromView(this);
        } else {
            bitmap = Bitmap.createBitmap(this.getDrawingCache());
        }
        setDrawingCacheEnabled(false);

        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public int getRessource() {
        ImageView view = new ImageView(context);
        view.setImageDrawable(getDrawable());
        int ressource = -1;
        try {
            ressource = Integer.parseInt(view.getTag().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ressource > -1 ? ressource : R.drawable.ic_launcher_background;
    }

    public Drawable getDrawableSource() {
        return drawable;
    }

    public CharSequence getCharacter() {
        return getCharacter(-1);
    }

    public CharSequence getCharacter(int color) {
        Drawable draft = null;
        String value = text;
        if (drawable != null) {
            draft = image.getDrawable();
            draft.setBounds(0, 0, draft.getIntrinsicWidth(), draft.getIntrinsicHeight());
            value = position == Position.LEFT ? ("    " + text) : (text + "    ");
        }
        SpannableString sb = new SpannableString(value);
        if (draft != null) {
            ImageSpan imageSpan = new ImageSpan(draft, ImageSpan.ALIGN_BOTTOM);
            if (position == Position.LEFT) {
                sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                sb.setSpan(imageSpan, text.length() + 2, text.length() + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        if (color != -1) {
            sb.setSpan(new ForegroundColorSpan(color), 0, sb.length(), 0);
        }
        return sb;
    }

    public class Position {

        public final static int ANY = -1;

        public final static int TOP = 0;
        public final static int BOTTOM = 1;
        public final static int LEFT = 2;
        public final static int RIGHT = 3;

        public final static int TOP_RIGHT = 4;
        public final static int BOTTOM_RIGHT = 5;
        public final static int TOP_LEFT = 6;
        public final static int BOTTOM_LEFT = 7;
    }

    public static Bitmap loadLargeBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.layout(0, 0, v.getWidth(), v.getHeight());
        v.draw(c);
        return b;
    }
}
