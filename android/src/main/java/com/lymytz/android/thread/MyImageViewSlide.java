package com.lymytz.android.thread;

import android.graphics.Bitmap;
import android.content.Context;

import com.lymytz.android.component.ImageModel;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyImageViewSlide extends MyView {

    public MyImageViewSlide(com.lymytz.android.view.MyImageViewSlide object, Context context) {
        super(object, context);
    }

    public void addImageRessource(int[] images) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyImageViewSlide) object).addImageRessource(images);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addImageBitmap(Bitmap[] bitmap) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyImageViewSlide) object).addImageBitmap(bitmap);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void addImageModel(List<ImageModel> models) {
        runOutUiThread(() -> {
            try {
                if (object != null) {
                    ((com.lymytz.android.view.MyImageViewSlide) object).addImageModel(models);
                }
            } catch (Exception ex) {
                Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
