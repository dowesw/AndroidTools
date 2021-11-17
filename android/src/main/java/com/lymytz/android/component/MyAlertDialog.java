package com.lymytz.android.component;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class MyAlertDialog extends AlertDialog.Builder {

    Context context;
    View view;
    AlertDialog dialog;

    public MyAlertDialog(Context context, int resource) {
        super(context, AlertDialog.THEME_HOLO_LIGHT);
        this.context = context;
        LayoutInflater factory = LayoutInflater.from(context);
        view = factory.inflate(resource, null);
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getView() {
        return view;
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }
}
