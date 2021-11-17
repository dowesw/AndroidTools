package com.lymytz.android.component;

import android.os.Binder;

import java.io.Serializable;

public class ObjectWrapperForBinder extends Binder implements Serializable {

    private final Object mData;

    public ObjectWrapperForBinder(Object data) {
        mData = data;
    }

    public Object getData() {
        return mData;
    }
}