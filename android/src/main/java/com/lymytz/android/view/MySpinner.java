package com.lymytz.android.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.RequiresApi;

import com.lymytz.android.R;
import com.lymytz.android.thread.MyProgressBar;
import com.lymytz.android.tools.Utils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressLint("AppCompatCustomView")
public class MySpinner extends RelativeLayout {
    SpinnerMode spinner_mode = SpinnerMode.DROPDOWN;
    Drawable spinner_border = null;

    Spinner spinner;
    ProgressBar pbar;
    LinearLayout blog;

    Object instance;
    String methode;
    Class[] paramType;
    Object[] params;
    Object currentValue;
    Class adapterClass;

    MySpinnerInterface mySpinnerInterface;

    List<Object> objects = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MySpinner(Context context) {
        super(context);
        init(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init(Context context, AttributeSet attrs) {
        inflate(getContext(), R.layout.component_spinner, this);
        try {
            if (attrs != null) {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySpinner);
                spinner_mode = SpinnerMode.values()[typedArray.getInt(R.styleable.MySpinner_spinner_mode, 0)];
                spinner_border = typedArray.getDrawable(R.styleable.MySpinner_spinner_border);
                typedArray.recycle();
            }
            initComponents();
            if (spinner_border != null && blog != null) {
                blog.setBackground(spinner_border);
            }
        } catch (Exception ex) {
            Logger.getLogger(MySpinner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initComponents() {
        pbar = findViewById(R.id.pbar);
        blog = findViewById(R.id.blog);
        if (spinner_mode == SpinnerMode.DIALOG) {
            spinner = findViewById(R.id.spinner_dialog);
        } else {
            spinner = findViewById(R.id.spinner_dropdown);
        }
        spinner.setVisibility(View.VISIBLE);
    }

    public MySpinner defined(Class adapterClass, MySpinnerInterface mySpinnerInterface) {
        this.adapterClass = adapterClass;
        this.mySpinnerInterface = mySpinnerInterface;
        return this;
    }

    public MySpinner defined(Class adapterClass, Object instance, String methode) {
        this.adapterClass = adapterClass;
        this.instance = instance;
        this.methode = methode;
        return this;
    }

    public MySpinner defined(Class adapterClass, Object instance, String methode, Object currentValue) {
        this.defined(adapterClass, instance, methode);
        this.currentValue = currentValue;
        return this;
    }

    public MySpinner defined(Class adapterClass, MySpinnerInterface mySpinnerInterface, Object currentValue) {
        this.defined(adapterClass, mySpinnerInterface);
        this.currentValue = currentValue;
        return this;
    }

    public MySpinner defined(Class adapterClass, Object instance, String methode, Class[] paramType, Object[] params) {
        this.defined(adapterClass, instance, methode);
        this.paramType = paramType;
        this.params = params;
        return this;
    }

    public MySpinner defined(Class adapterClass, Object instance, String methode, Class[] paramType, Object[] params, Object currentValue) {
        this.defined(adapterClass, instance, methode, paramType, params);
        this.currentValue = currentValue;
        return this;
    }

    public void setCurrentValue(Object currentValue) {
        this.currentValue = currentValue;
        int visibility = this.pbar.getVisibility();
        if (visibility == View.GONE) {
            int index = objects.indexOf(currentValue);
            if (index > -1) {
                spinner.setSelection(index);
            }
        }
    }

    public void setAdapter(SpinnerAdapter adapter) {
        spinner.setAdapter(adapter);
    }

    public SpinnerAdapter getAdapter() {
        return spinner.getAdapter();
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        spinner.setOnItemSelectedListener(listener);
    }

    public void setSelection(int index) {
        if (index > -1) {
            spinner.setSelection(index);
        }
    }

    public void setSelectedItem(Object value) {
        if (value != null ? objects != null ? !objects.isEmpty() : false : false) {
            int index = objects.indexOf(value);
            if (index > -1) {
                setSelection(index);
            }
        }
    }

    public Object getSelectedItem() {
        return spinner != null ? spinner.getSelectedItem() : null;
    }

    public void removeAll() {
        try {
            objects.clear();
            SpinnerAdapter adapter = (SpinnerAdapter) instanceAdapter();
            if (adapter != null) {
                spinner.setAdapter(adapter);
            }
        } catch (Exception ex) {
            Logger.getLogger(MySpinner.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pbar.setVisibility(View.GONE);
        }
    }

    public void onLoad() {
        MyProgressBar pbar = new MyProgressBar(this.pbar, getContext());
        com.lymytz.android.thread.MySpinner spinner = new com.lymytz.android.thread.MySpinner(this.spinner, getContext());

        pbar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                objects = null;
                if (mySpinnerInterface != null) {
                    objects = mySpinnerInterface.load();
                } else {
                    if (instance != null ? Utils.asString(methode) : false) {
                        Method method;
                        if (paramType != null ? paramType.length > 0 : false) {
                            method = instance.getClass().getDeclaredMethod(methode, paramType);
                        } else {
                            method = instance.getClass().getDeclaredMethod(methode);
                        }
                        if (method != null) {
                            objects = (List<Object>) method.invoke(instance, params);
                        }
                    }
                }
                if (adapterClass != null && objects != null) {
                    SpinnerAdapter adapter = (SpinnerAdapter) instanceAdapter();
                    if (adapter != null) {
                        spinner.setAdapter(adapter);
                        if (currentValue != null) {
                            int index = objects.indexOf(currentValue);
                            if (index > -1) {
                                spinner.setSelection(index);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(MySpinner.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                pbar.setVisibility(View.GONE);
            }
        }).start();
    }

    private Object instanceAdapter() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] argumant = new Class[3];
        argumant[0] = Context.class;
        argumant[1] = int.class;
        argumant[2] = List.class;

        Context context = getContext();
        int resource = R.layout.support_simple_spinner_dropdown_item;
        return adapterClass.getDeclaredConstructor(argumant).newInstance(context, resource, objects);
    }

    enum SpinnerMode {
        DROPDOWN, DIALOG;
    }


    public interface MySpinnerInterface<T extends Serializable> {

        List<T> load();
    }
}