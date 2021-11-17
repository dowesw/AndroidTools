package com.lymytz.android.thread;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyItem extends MyView {

    public MyItem(com.lymytz.android.view.MyItem object, Context context) {
        super(object, context);
    }

    public void setIndicator(int indicator) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyItem) object).setIndicator(indicator);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setNombre(String nombre) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyItem) object).setNombre(nombre);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTitre(String text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyItem) object).setTitre(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setTextRight(String text, int background) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyItem) object).setTextRight(text, background);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void setSousTitre(String text) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyItem) object).setSousTitre(text);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyItem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
