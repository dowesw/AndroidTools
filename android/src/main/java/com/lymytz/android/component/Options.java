package com.lymytz.android.component;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Options {
    private int position;
    private String libelle;
    private String value;
    private Object data;

    public Options() {

    }

    public Options(String value) {
        this();
        this.value = value;
    }

    public Options(String value, String libelle) {
        this(value);
        this.libelle = libelle;
    }

    public Options(String value, int data){
        this(value);
        this.data = data;
    }

    public Options(int position, Object data){
        this.position = position;
        this.data = data;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Options options = (Options) o;
        return Objects.equals(value, options.value);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
