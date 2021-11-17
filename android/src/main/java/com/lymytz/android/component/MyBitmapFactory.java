package com.lymytz.android.component;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyBitmapFactory extends BitmapFactory {

    private static int MAX_BOUCLE = 50;

    public static Bitmap decodeStream(InputStream is) {
        Bitmap bitmap = null;
        try {
            int inSampleSize = 1;
            int boucle = 0;
            BitmapFactory.Options options = new BitmapFactory.Options();
            boolean correct = false;
            while (!correct && boucle < MAX_BOUCLE) {
                try {
                    options.inSampleSize = inSampleSize;
                    bitmap = BitmapFactory.decodeStream(is, null, options);
                    correct = true;
                } catch (Exception ex) {
                    Logger.getLogger(MyBitmapFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally {
                    boucle++;
                }
                inSampleSize += 1;
            }
        } catch (Exception ex) {
            Logger.getLogger(MyBitmapFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bitmap;
    }
}
