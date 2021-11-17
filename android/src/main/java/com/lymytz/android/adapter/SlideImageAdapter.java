package com.lymytz.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.lymytz.android.R;
import com.lymytz.android.component.ImageModel;
import com.lymytz.android.component.MyBitmapFactory;
import com.lymytz.android.thread.MyProgressBar;
import com.lymytz.android.tools.Messages;
import com.lymytz.android.tools.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Parsania Hardik on 23/04/2016.
 */
public class SlideImageAdapter extends PagerAdapter {

    private List<ImageModel> images;
    private LayoutInflater inflater;
    private Context context;

    View.OnClickListener clickListener;
    View.OnLongClickListener longClickListener;

    public SlideImageAdapter(Context context, List<ImageModel> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    public List<ImageModel> getImages() {
        return images;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View root = inflater.inflate(R.layout.component_viewpage_image_item, view, false);
        assert root != null;
        final ImageView image = root.findViewById(R.id.image);
        final ProgressBar pbar = root.findViewById(R.id.pbar);
        ImageModel model = images.get(position);
        model.setProgressBar(pbar);
        if (!model.isDownload()) {
            onDownload(model, position);
        } else if (pbar != null) {
            pbar.setVisibility(View.GONE);
        }
        if (model.getResource() > -1) {
            image.setImageResource(model.getResource());
        } else {
            image.setImageBitmap(model.getBitmap());
        }
        root.setTag("View" + position);
        image.setScaleType(model.getScaleType());
        view.addView(root, 0);
        if (clickListener != null)
            root.setOnClickListener(clickListener);
        if (longClickListener != null)
            root.setOnLongClickListener(longClickListener);
        return root;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void removeAllView() {
        images.clear();
        notifyDataSetChanged();
    }

    public void deleteImage(int index) {
        try {
            if (index > -1 ? images.size() > index : false) {
                ImageModel image = images.get(index);
                if (image != null ? Utils.asString(image.getFileCible()) : false) {
                    File file = new File(image.getFileCible());
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
    }

    public void setOnClickListener(@Nullable View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnLongClickListener(@Nullable View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onDownload(ImageModel model, int position) {
        MyProgressBar pbar = new MyProgressBar(model.getProgressBar(), context);
        new Thread(() -> {
            pbar.setVisibility(View.VISIBLE);
            try {
                Bitmap bitmap = null;
                if (com.lymytz.android.tools.Utils.asString(model.getFileSource())) {
                    try {
                        URL urlImage = new URL(model.getFileSource());
                        InputStream input = null;
                        try {
                            URLConnection urlConnection = urlImage.openConnection();
                            urlConnection.setConnectTimeout(30000);
                            input = urlConnection.getInputStream();
                        } catch (Exception ex) {
//                            Messages.Exception(context, ex);
                        }
                        if (input != null) {
                            com.lymytz.android.tools.Utils.write(model.getFileCible(), input);
                            bitmap = MyBitmapFactory.decodeStream(new FileInputStream(model.getFileCible()));
                        }

                    } catch (Exception ex) {
                        Messages.Exception(context, ex);
                    }
                    if (bitmap != null) {
                        model.setBitmap(bitmap);
                        model.setResource(-1);
                        model.setDownload(true);
                        if (position > -1) {
                            images.set(position, model);
                        }
                    }
                }
                pbar.setVisibility(View.GONE);
            } catch (Exception ex) {
                Messages.Exception(context, ex);
            } finally {
                pbar.setVisibility(View.GONE);
            }
            pbar.setVisibility(View.GONE);
        }).start();
    }
}