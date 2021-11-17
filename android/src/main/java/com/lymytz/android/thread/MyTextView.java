package com.lymytz.android.thread;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTextView extends MyView {

    public MyTextView(TextView object, Context context) {
        super(object, context);
    }

    public void setText(int resid) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextView) object).setText(resid);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTextColor(@ColorInt int color) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextView) object).setTextColor(color);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setText(CharSequence text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextView) object).setText(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setHint(int resid) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextView) object).setHint(resid);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setHint(String hint) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextView) object).setHint(hint);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setHintTextColor(@ColorInt int color) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextView) object).setHintTextColor(color);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setStrikeThruText(boolean strikeThruText) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((TextView) object).getPaint().setStrikeThruText(strikeThruText);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyTextView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
