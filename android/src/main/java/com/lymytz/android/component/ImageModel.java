package com.lymytz.android.component;

import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class ImageModel implements Serializable {
    private int resource = -1;
    private Bitmap bitmap = null;
    private ImageView.ScaleType scaleType;
    private Object data;
    private boolean download = false;
    private String fileSource = null;
    private String fileCible = null;
    private ProgressBar progressBar;

    public ImageModel(int resource, ImageView.ScaleType scaleType) {
        this.resource = resource;
        this.scaleType = scaleType;
    }

    public ImageModel(Bitmap bitmap, ImageView.ScaleType scaleType) {
        this.bitmap = bitmap;
        this.scaleType = scaleType;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public String getFileSource() {
        return fileSource;
    }

    public void setFileSource(String fileSource) {
        this.fileSource = fileSource;
    }

    public String getFileCible() {
        return fileCible;
    }

    public void setFileCible(String fileCible) {
        this.fileCible = fileCible;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageModel that = (ImageModel) o;
        return Objects.equals(bitmap, that.bitmap);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(bitmap);
    }
}
