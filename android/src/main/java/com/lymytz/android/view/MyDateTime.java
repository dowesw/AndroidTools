package com.lymytz.android.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.ColorInt;

import com.lymytz.android.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressLint("AppCompatCustomView")
public class MyDateTime extends LinearLayout {
    boolean date_initialise = false;
    boolean date_enabled_text = true;
    boolean date_enabled_button = true;
    Type date_type = Type.TYPE_DATE;
    String date_hint = "";
    String date_text = "";
    Drawable date_border = null;
    @ColorInt
    int date_background = -1;
    Drawable date_image = null;
    int date_gravity = 0x03 | 0x10;

    EditText text;
    ImageButton button;
    public static final DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
    public static final DateFormat formatTime = new SimpleDateFormat("HH:mm");

    onSelectDateListener listener;

    public MyDateTime(Context context) {
        super(context);
        init(context, null);
    }

    public MyDateTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyDateTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_date, this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyDateTime);
            date_initialise = typedArray.getBoolean(R.styleable.MyDateTime_date_initialise, date_initialise);
            date_enabled_text = typedArray.getBoolean(R.styleable.MyDateTime_date_enabled_text, date_enabled_text);
            date_enabled_button = typedArray.getBoolean(R.styleable.MyDateTime_date_enabled_button, date_enabled_button);
            date_type = Type.values()[typedArray.getInt(R.styleable.MyDateTime_date_type, 0)];
            date_hint = typedArray.getString(R.styleable.MyDateTime_date_hint);
            date_text = typedArray.getString(R.styleable.MyDateTime_date_text);
            date_border = typedArray.getDrawable(R.styleable.MyDateTime_date_border);
            date_background = typedArray.getColor(R.styleable.MyDateTime_date_background, 0xff000000);
            date_image = typedArray.getDrawable(R.styleable.MyDateTime_date_image);
            date_gravity = typedArray.getInt(R.styleable.MyDateTime_date_gravity, date_gravity);
            typedArray.recycle();
        }
        initComponents();
        text.setHint(date_hint);
        text.setText(date_text);
        text.setGravity(date_gravity);
        text.setEnabled(date_enabled_text);
        button.setEnabled(date_enabled_button);

        final Calendar c = Calendar.getInstance();
        if (date_initialise) {
            if (date_type == Type.TYPE_DATE) {
                text.setText(formatDate.format(c.getTime()));
            } else {
                text.setText(formatTime.format(c.getTime()));
            }
        }
        if (date_border != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                text.setBackground(date_border);
            } else {
                text.setBackgroundDrawable(date_border);
            }
        }
        button.setBackgroundColor(date_background);
        if (date_image != null) {
            button.setImageDrawable(date_image);
        }

        final Type mode = date_type;
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == Type.TYPE_DATE) {
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            text.setText(getDate(year, month, dayOfMonth));
                            c.setTime(getDate());
                            if (listener != null) {
                                listener.onSelect(getDate());
                            }
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                } else {
                    int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    TimePickerDialog datePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int min) {
                            text.setText(getTime(hour, min));
                            c.setTime(getDate());
                            if (listener != null) {
                                listener.onSelect(getDate());
                            }
                        }
                    }, hourOfDay, minute, true);
                    datePickerDialog.show();
                }
            }
        });
    }

    private void initComponents() {
        text = findViewById(R.id.text);
        button = findViewById(R.id.button);
    }

    public void setOnSelectDateListener(onSelectDateListener listener) {
        this.listener = listener;
    }

    public String getText() {
        return text.getText().toString();
    }

    public Date getDate() {
        if (asString(getText())) {
            try {
                if (date_type == Type.TYPE_DATE) {
                    return formatDate.parse(getText());
                } else {
                    return formatTime.parse(getText());
                }
            } catch (ParseException ex) {
                Logger.getLogger(MyDateTime.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void setDate(Date value) {
        if (value != null) {
            if (date_type == Type.TYPE_DATE) {
                text.setText(formatDate.format(value));
            } else {
                text.setText(formatTime.format(value));
            }
        }
    }

    public void setBackgroundColor(int color) {
        button.setBackgroundColor(color);
    }

    public void setEnabledButton(boolean enabled) {
        button.setEnabled(enabled);
    }

    public void setEnabledText(boolean enabled) {
        text.setEnabled(enabled);
    }

    enum Type {
        TYPE_DATE, TYPE_TIME;
    }

    private boolean asString(String value) {
        return value != null ? value.trim().length() > 0 : false;
    }

    private String getDate(int year, int month, int dayOfMonth) {
        return ((dayOfMonth < 10 ? "0" : "") + dayOfMonth) + "-" + (((month + 1) < 10 ? "0" : "") + (month + 1)) + "-" + year;
    }

    private String getTime(int hour, int min) {
        return ((hour < 10 ? "0" : "") + hour) + ":" + ((min < 10 ? "0" : "") + min);
    }

    public interface onSelectDateListener {
        void onSelect(Date date);
    }
}
