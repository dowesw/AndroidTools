package com.lymytz.android.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import java.io.File;

public class Chemins extends Activity {

    public static final File Storage = Environment.getDataDirectory();

    public static File Root(Context context) {
        return context != null ? context.getExternalFilesDir("") : new File("");
    }

    public static File Cache(Context context) {
        return context.getExternalCacheDir();
    }

    public static String Data(Context context) {
        File file = Root(context);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String Logs(Context context) {
        File file = new File(Data(context), "logs");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String Database(Context context) {
        File file = new File(Data(context), "database");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String Images(Context context) {
        File file = new File(Data(context), "images");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
