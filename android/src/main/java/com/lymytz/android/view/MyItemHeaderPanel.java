package com.lymytz.android.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.lymytz.android.R;
import com.lymytz.android.tools.Utils;

public class MyItemHeaderPanel extends LinearLayout {

    LinearLayout header;
    ImageView header_image;
    TextView header_title;
    TextView title;
    LinearLayout sub_header;

    private void init(Context context) {
        inflate(context, R.layout.component_item_header_panel, this);

        header = findViewById(R.id.header);
        header_image = findViewById(R.id.header_image);
        header_title = findViewById(R.id.header_title);
        title = findViewById(R.id.title);
        sub_header = findViewById(R.id.sub_header);

    }

    public MyItemHeaderPanel(Context context) {
        super(context);
        init(context);
    }

    public MyItemHeaderPanel(Context context, String header_title, String title, int color_title, Drawable header_image) {
        super(context);
        init(context);

        this.title.setText(title);
        this.header_title.setText(header_title);
        this.header_image.setImageDrawable(header_image);

        if (color_title > -1) {
            this.title.setBackgroundColor(color_title);
        }

        this.title.setVisibility(Utils.asString(title) ? View.VISIBLE : View.GONE);
        this.header_title.setVisibility(Utils.asString(header_title) ? View.VISIBLE : View.GONE);
        this.header_image.setVisibility(header_image != null ? View.VISIBLE : View.GONE);
        boolean visible_header = Utils.asString(title) || Utils.asString(header_title) || header_image != null;
        this.header.setVisibility(visible_header ? View.VISIBLE : View.GONE);
    }

    public MyItemHeaderPanel(Context context, String header_title, String title, int header_image) {
        this(context, header_title, title, -1, (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? context.getDrawable(header_image) : context.getResources().getDrawable(header_image)));
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setTitle(String title, int color) {
        this.title.setText(title);
        if (color > -1) {
            this.title.setTextColor(getContext().getColor(color));
        }
        this.title.setVisibility(Utils.asString(title) ? View.VISIBLE : View.GONE);
        header.setVisibility(Utils.asString(title) ? View.VISIBLE : View.GONE);
    }

    public void setBorderHeaderImage(int background) {
        this.header_image.setBackgroundResource(background);
    }
}
