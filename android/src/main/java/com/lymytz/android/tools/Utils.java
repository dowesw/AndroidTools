package com.lymytz.android.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.service.notification.StatusBarNotification;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.Formatter;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.lymytz.android.R;
import com.lymytz.android.component.ActionMethod;
import com.lymytz.android.component.CustomSSLSocketFactory;
import com.lymytz.android.enums.Action;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.WIFI_SERVICE;

public class Utils {

    public static final String IPv4_REGEX =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    public static final DateFormat formatDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static final DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
    public static final DateFormat formatTimeWithSecond = new SimpleDateFormat("HH:mm:ss");
    public static final DateFormat formatTime = new SimpleDateFormat("HH:mm");

    public static final NumberFormat formatWithOutDecimal = new DecimalFormat("#,##0");
    public static final NumberFormat formatWithTooDecimal = new DecimalFormat("#,##0.00");
    public static final NumberFormat formatWithDecimal = new DecimalFormat("#,##0.000000000000000");

    static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);// UNICODE 0x23 = #

    public static final byte[] UNICODE_TEXT = new byte[]{0x23, 0x23, 0x23,
            0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23,
            0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23, 0x23,
            0x23, 0x23, 0x23};

    private static String hexStr = "0123456789ABCDEF";
    private static String[] binaryArray = {"0000", "0001", "0010", "0011",
            "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
            "1100", "1101", "1110", "1111"};

    public static boolean haveNetworkConnection(Context context) {
        try {
            if (context == null) {
                return false;
            }
            ConnectivityManager cm = null;
            try {
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            } catch (Exception ex) {
            }
            if (cm == null) {
                return false;
            }
            boolean haveConnectedWifi = false;
            boolean haveConnectedMobile = false;
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isAvailable() && ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isAvailable() && ni.isConnected())
                        haveConnectedMobile = true;
            }
            if (haveConnectedWifi || haveConnectedMobile) {
//                return onPingGoogle();
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean onPingGoogle() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int mExitValue = mIpAddrProcess.waitFor();
            return (mExitValue == 0);
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static byte[] decodeBitmap(Bitmap bmp) {
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();

        List<String> list = new ArrayList<String>(); //binaryString list
        StringBuffer sb;

        int bitLen = bmpWidth / 8;
        int zeroCount = bmpWidth % 8;

        String zeroStr = "";
        if (zeroCount > 0) {
            bitLen = bmpWidth / 8 + 1;
            for (int i = 0; i < (8 - zeroCount); i++) {
                zeroStr = zeroStr + "0";
            }
        }

        for (int i = 0; i < bmpHeight; i++) {
            sb = new StringBuffer();
            for (int j = 0; j < bmpWidth; j++) {
                int color = bmp.getPixel(j, i);

                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;

                // if color close to white，bit='0', else bit='1'
                if (r > 160 && g > 160 && b > 160)
                    sb.append("0");
                else
                    sb.append("1");
            }
            if (zeroCount > 0) {
                sb.append(zeroStr);
            }
            list.add(sb.toString());
        }

        List<String> bmpHexList = binaryListToHexStringList(list);
        String commandHexString = "1D763000";
        String widthHexString = Integer
                .toHexString(bmpWidth % 8 == 0 ? bmpWidth / 8
                        : (bmpWidth / 8 + 1));
        if (widthHexString.length() > 2) {
            Log.e("decodeBitmap error", " width is too large");
            return null;
        } else if (widthHexString.length() == 1) {
            widthHexString = "0" + widthHexString;
        }
        widthHexString = widthHexString + "00";

        String heightHexString = Integer.toHexString(bmpHeight);
        if (heightHexString.length() > 2) {
            Log.e("decodeBitmap error", " height is too large");
            return null;
        } else if (heightHexString.length() == 1) {
            heightHexString = "0" + heightHexString;
        }
        heightHexString = heightHexString + "00";

        List<String> commandList = new ArrayList<String>();
        commandList.add(commandHexString + widthHexString + heightHexString);
        commandList.addAll(bmpHexList);

        return hexList2Byte(commandList);
    }

    public static List<String> binaryListToHexStringList(List<String> list) {
        List<String> hexList = new ArrayList<String>();
        for (String binaryStr : list) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < binaryStr.length(); i += 8) {
                String str = binaryStr.substring(i, i + 8);

                String hexString = myBinaryStrToHexString(str);
                sb.append(hexString);
            }
            hexList.add(sb.toString());
        }
        return hexList;

    }

    public static String myBinaryStrToHexString(String binaryStr) {
        String hex = "";
        String f4 = binaryStr.substring(0, 4);
        String b4 = binaryStr.substring(4, 8);
        for (int i = 0; i < binaryArray.length; i++) {
            if (f4.equals(binaryArray[i]))
                hex += hexStr.substring(i, i + 1);
        }
        for (int i = 0; i < binaryArray.length; i++) {
            if (b4.equals(binaryArray[i]))
                hex += hexStr.substring(i, i + 1);
        }

        return hex;
    }

    public static byte[] hexList2Byte(List<String> list) {
        List<byte[]> commandList = new ArrayList<byte[]>();

        for (String hexStr : list) {
            commandList.add(hexStringToBytes(hexStr));
        }
        byte[] bytes = sysCopy(commandList);
        return bytes;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte[] sysCopy(List<byte[]> srcArrays) {
        int len = 0;
        for (byte[] srcArray : srcArrays) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String NUMBER(Object value) {
        return NUMBER(value != null ? asNumeric(value.toString()) ? Double.valueOf(value.toString()) : 0 : 0);
    }

    public static String NUMBER(String value) {
        return NUMBER(asNumeric(value) ? Double.valueOf(value) : 0);
    }

    public static String NUMBER(Integer valeur) {
        return DN(valeur);
    }

    public static String NUMBER(Long valeur) {
        return DN(valeur);
    }

    public static String NUMBER(Double valeur) {
        if (asInteger(valeur)) {
            return DN(valeur);
        } else {
            return DNS(valeur);
        }
    }

    public static String DN(String value) {
        return DN(asNumeric(value) ? Double.valueOf(value) : 0);
    }

    public static String DN(Long value) {
        return value != null ? formatWithOutDecimal.format(value) != null ? formatWithOutDecimal.format(value) : "" : "";
    }

    public static String DN(Integer value) {
        return value != null ? formatWithOutDecimal.format(value) != null ? formatWithOutDecimal.format(value) : "" : "";
    }

    public static String DN(Double value) {
        return value != null ? formatWithOutDecimal.format(value) != null ? formatWithOutDecimal.format(value) : "" : "";
    }

    public static String DNS(Double value) {
        return value != null ? formatWithTooDecimal.format(value) != null ? formatWithTooDecimal.format(value) : "" : "";
    }

    public static String DNS(String value) {
        return DNS(asNumeric(value) ? Double.valueOf(value) : 0);
    }

    public static String DNA(String value) {
        return DNA(asNumeric(value) ? Double.valueOf(value) : 0);
    }

    public static String DNA(Double value) {
        String num = value != null ? formatWithDecimal.format(value) != null ? formatWithDecimal.format(value) : "" : "";
        while (num.charAt(num.length() - 1) == '0') {
            num = num.substring(0, num.length() - 1);
        }
        if (num.charAt(num.length() - 1) == '.'
                || num.charAt(num.length() - 1) == ',') {
            num = num.substring(0, num.length() - 1);
        }
        return num;
    }

    public static String doubleToIntString(Double value) {
        return doubleToIntString(value != null ? value + "" : "0");
    }

    public static String doubleToIntString(String value) {
        String amount = value;
        int index = amount.indexOf(".");
        if (index > -1) {
            amount = amount.substring(0, index);
        }
        return amount;
    }

    public static void startActivityForResult(Context context, Intent intent, int requestCode) {
        startActivityForResult((Activity) context, intent, requestCode);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.startActivityForResult(intent, requestCode, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                activity.startActivityForResult(intent, requestCode);
            }
        } catch (Exception ex) {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static void startActivity(Context context, Intent intent) {
        startActivity((Activity) context, intent);
    }

    public static void startActivity(Activity activity, Intent intent) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            } else {
                activity.startActivity(intent);
            }
        } catch (Exception ex) {
            activity.startActivity(intent);
        }
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable(Context context) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            int code = context.getPackageManager().checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, context.getPackageName());
            if (code == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidInet4Address(String ip) {
        if (ip == null) {
            return false;
        }
        if (ip.replace("\"", "").equals("localhost")) {
            return true;
        }
        Matcher matcher = IPv4_PATTERN.matcher(ip);
        return matcher.matches();
    }

    public static boolean contains(String element, String value) {
        return asString(element) && asString(value) ? element.toLowerCase().contains(value.toLowerCase()) : false;
    }

    public static boolean asUrl(String value) {
        if (asString(value)) {
            if (value.startsWith("http://") || value.startsWith("https://")) {
                return true;
            }
        }
        return false;
    }

    public static boolean asInteger(String value) {
        try {
            if (asNumeric(value)) {
                return asInteger(Double.valueOf(value));
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean asInteger(Double value) {
        try {
            int integer = value.intValue();
            double reste = value - integer;
            return reste > 0 ? false : true;
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean asNumeric(Object value) {
        try {
            if (value != null ? asString(value.toString()) : false) {
                Float.valueOf(value.toString());
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean asNumeric(String value) {
        try {
            if (asString(value)) {
                Float.valueOf(value);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean asDate(String value) {
        try {
            if (asString(value)) {
                formatDate.parse(value);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean asBoolean(String value) {
        try {
            if (asString(value) ? value.toLowerCase().equals("true") || value.toLowerCase().equals("false") : false)
                return true;
        } catch (Exception ex) {

        }
        return false;
    }

    public static boolean asString(String value) {
        return value != null ? value.trim().length() > 0 : false;
    }

    public static boolean asCharacter(Character value) {
        return value != null ? String.valueOf(value).trim().length() > 0 : false;
    }

    public static String getDate(int year, int month, int dayOfMonth) {
        return ((dayOfMonth < 10 ? "0" : "") + dayOfMonth) + "-" + (((month + 1) < 10 ? "0" : "") + (month + 1)) + "-" + year;
    }

    public static String getTime(int hour, int min) {
        return ((hour < 10 ? "0" : "") + hour) + ":" + ((min < 10 ? "0" : "") + min);
    }

    public static String timeStr(Date date) {
        return formatTimeWithSecond.format(date);
    }

    public static String dateStr(long date) {
        return dateStr(new Date(date));
    }

    public static String dateStr(Calendar date) {
        try {
            return dateStr(date.getTimeInMillis());
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateStr(new Date());
    }

    public static String dateStr(Date date) {
        return formatDate.format(date);
    }

    public static String dateTimeStr(Date date) {
        return formatDateTime.format(date);
    }

    public static Calendar getIniTializeDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static Date getPreviewDate(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, -1);
            return c.getTime();
        }
        return new Date();
    }

    public static Calendar dateToCalendar(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.setTime(date);
            return cal;
        }
        return Calendar.getInstance();
    }

    public static String giveDay(int day) {
        switch (day) {
            case 0:
                return "Dimanche";
            case 1:
                return "Lundi";
            case 2:
                return "Mardi";
            case 3:
                return "Mercredi";
            case 4:
                return "Jeudi";
            case 5:
                return "Vendredi";
            case 6:
                return "Samedi";
            default:
                return null;
        }
    }

    public static String getDay(Calendar jour) {
        int d = jour.get(Calendar.DAY_OF_WEEK);
        String str = "";
        if (d == Calendar.MONDAY) {
            str = "Lundi";
        } else if (d == Calendar.TUESDAY) {
            str = "Mardi";
        } else if (d == Calendar.WEDNESDAY) {
            str = "Mercredi";
        } else if (d == Calendar.THURSDAY) {
            str = "Jeudi";
        } else if (d == Calendar.FRIDAY) {
            str = "Vendredi";
        } else if (d == Calendar.SATURDAY) {
            str = "Samedi";
        } else if (d == Calendar.SUNDAY) {
            str = "Dimanche";
        }
        return str;
    }

    public static String getMonth(int month) {
        String str = "";
        if (month == Calendar.MONDAY) {
            str = "Lundi";
        } else if (month == Calendar.TUESDAY) {
            str = "Mardi";
        } else if (month == Calendar.WEDNESDAY) {
            str = "Mercredi";
        } else if (month == Calendar.THURSDAY) {
            str = "Jeudi";
        } else if (month == Calendar.FRIDAY) {
            str = "Vendredi";
        } else if (month == Calendar.SATURDAY) {
            str = "Samedi";
        } else if (month == Calendar.SUNDAY) {
            str = "Dimanche";
        }
        return str;
    }

    public static String getDay(Date day) {
        Calendar jour = Calendar.getInstance();
        jour.setTime(day);
        return getDay(jour);
    }

    public static HttpClient getHttpCLient(String adresse) {
        HttpClient client = new DefaultHttpClient();
        try {
            if (adresse.toLowerCase().contains("https://")) {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
                client = new DefaultHttpClient(ccm, params);
            }

        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return client;
    }

    @SuppressWarnings("rawtypes")
    public static Object InvokeMethod(String method, Object classe, Class returns, Class[] paramType, Object[] params) {
        try {
            if (method != null && classe != null) {
                if (paramType != null ? paramType.length > 0 : false) {
                    Method thisMethod = classe.getClass().getMethod(method, paramType);
                    thisMethod.setAccessible(true);
                    if (returns != null ? !returns.equals(void.class) : false)
                        return thisMethod.invoke(classe, params);
                    else
                        thisMethod.invoke(classe, params);
                } else {
                    Method thisMethod = classe.getClass().getMethod(method);
                    thisMethod.setAccessible(true);
                    if (returns != null ? !returns.equals(void.class) : false)
                        return thisMethod.invoke(classe);
                    else
                        thisMethod.invoke(classe);
                }
                return null;
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ActionMethod returnAction(ActionMethod[] methodes, Action action) {
        try {
            if (methodes != null ? methodes.length > 0 : false) {
                for (ActionMethod method : methodes) {
                    if (method.ACTION.equals(action)) {
                        return method;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String getPulicIpAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        if (wm != null) {
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            return ip;
        }
        return null;
    }

    public static String getAdresseMac(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        if (wm != null) {
            WifiInfo info = wm.getConnectionInfo();
            String mac = info.getMacAddress();
            return mac;
        }
        return null;
    }

    public static TextView customTab(Context context, int text, int color) {
        return customTab(context, context.getString(text), color);
    }

    public static TextView customTab(Context context, String text, int color) {
        TextView view = new TextView(context);
        view.setGravity(Gravity.CENTER);
        view.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setTextColor(context.getColor(color));
        } else {
            view.setTextColor(context.getResources().getColor(color));
        }
        return view;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + "/" + model;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static String getDescription(String description) {
        return getResume(description, 30);
    }

    public static String getLibelle(String libelle) {
        return getResume(libelle, 18);
    }

    public static String getResume(String value, int lenght) {
        if (value != null ? value.trim().length() > 0 : false) {
            if (value.trim().length() > lenght) {
                return value.substring(0, lenght) + "...";
            } else
                return value;
        }
        return "...";
    }

    public static String saveBitmap(String destination, Bitmap bitmap) {
        return saveBitmap(destination, System.currentTimeMillis() + ".jpg", bitmap);
    }

    public static String saveBitmap(String destination, String fileName, Bitmap bitmap) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            String extension = "JPEG";
            String[] parts = fileName.split("\\.");
            if (parts != null ? parts.length > 0 : false) {
                extension = parts[parts.length - 1];
            }
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            switch (extension.toUpperCase()) {
                case "PNG":
                    format = Bitmap.CompressFormat.PNG;
                    break;
            }
            bitmap.compress(format, 90, bytes);
            byte[] byteArray = bytes.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            File file = new File(destination, fileName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fo;
            try {
                file.createNewFile();
                fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return fileName;
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Bitmap getBitmapFromUri(Uri uri, Context context) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public static void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(500);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    public static void focusOnView(final HorizontalScrollView scroll, final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int vLeft = view.getLeft();
                int vRight = view.getRight();
                int sWidth = scroll.getWidth();
                scroll.smoothScrollTo(((vLeft + vRight - sWidth) / 2), 0);
            }
        });
    }

    public static void focusOnView(final ScrollView scroll, final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int vTop = view.getTop();
                int vBottom = view.getBottom();
                int sHeight = scroll.getHeight();
                scroll.smoothScrollTo(0, ((vTop + vBottom - sHeight) / 2));
            }
        });
    }

    public static CharSequence MenuIconWithText(Drawable r, String title) {
        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static File write(String fileDirectory, InputStream input) {
        try {
            if (input != null) {
                File file = new File(fileDirectory);
                if (file.exists())
                    file.delete();
                try (FileOutputStream fos = new FileOutputStream(fileDirectory)) {
                    // On utilise un tableau comme buffer
                    byte[] buf = new byte[8192];
                    // Et on utilise une variable pour connaitre le nombre
                    // de bytes lus, et donc le nombres qu'il faudra écrire :
                    int len;

                    while ((len = input.read(buf)) >= 0) {
                        fos.write(buf, 0, len);
                    }
                    file = new File(fileDirectory);
                    if (file.exists())
                        return file;
                } catch (IOException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static byte[] read(File file) {
        return readStream(file).toByteArray();
    }

    public static ByteArrayOutputStream readStream(File file) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            FileInputStream fis = new FileInputStream(file);
            int c = 0;
            try {
                while ((c = fis.read()) != -1) {
                    buffer.write(c);
                    buffer.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer;
    }

    public static String readToString(File file) {
        byte[] bytes = read(file);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static int getRandomColor() {
        try {
            Random rnd = new Random();
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        } catch (Exception ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static NotificationCompat.Builder Builder(Context context, String channel, boolean displaySound, int color) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_LOW;
            if (displaySound) {
                importance = NotificationManager.IMPORTANCE_DEFAULT;
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(channel, channelName, importance);
                if (!displaySound) {
                    mChannel.setSound(null, null);
                }
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channel);
            if (!displaySound) {
                mBuilder.setSound(null, AudioManager.STREAM_SYSTEM);
            }
            mBuilder.setAutoCancel(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mBuilder.setColor(context.getColor(color));
            } else {
                mBuilder.setColor(context.getResources().getColor(color));
            }
            return mBuilder;
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
        return null;
    }

    public static void callNumber(Activity activity, String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            activity.startActivity(intent);
        } catch (Exception ex) {
            Messages.Exception(activity, ex);
        }
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotificationIsDisplay(Context context, int notificationId) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (StatusBarNotification notification : notificationManager.getActiveNotifications()) {
                    if (notification.getId() == notificationId) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isJobServiceOn(Context context, int jobId) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        boolean hasBeenScheduled = false;
        for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == jobId) {
                hasBeenScheduled = true;
                break;
            }
        }
        return hasBeenScheduled;
    }

    public static GradientDrawable buildBackgroundRectangle(Context context, int color) {
        return buildBackground(context, GradientDrawable.RECTANGLE, color);
    }

    public static GradientDrawable buildBackground(Context context, int shape, int color) {
        GradientDrawable background = new GradientDrawable();
        background.setShape(shape);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            background.setColor(context.getColor(android.R.color.transparent));
        } else {
            background.setColor(context.getResources().getColor(android.R.color.transparent));
        }
        background.setStroke(1, color);
        return background;
    }

    public static int getColor(Context context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(color);
        } else {
            return context.getResources().getColor(color);
        }
    }

    public static Activity unwrap(Context context) {
        if (context == null) {
            return null;
        }
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }

    public static void runOnUiThread(Context context, Runnable runnable) {
        try {
            Activity activity = context != null ? unwrap(context) : null;
            if (activity != null) {
                activity.runOnUiThread(runnable);
            } else {
                runnable.run();
            }
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
    }
}


