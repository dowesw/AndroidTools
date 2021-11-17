package com.lymytz.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lymytz.android.R;
import com.lymytz.android.adapter.SlideImageAdapter;
import com.lymytz.android.component.ImageModel;
import com.lymytz.android.component.ObjectWrapperForBinder;
import com.lymytz.android.listener.OnSwipeTouchListener;
import com.lymytz.android.tools.Messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyImageViewSlide extends RelativeLayout implements ViewPager.OnPageChangeListener {

    boolean slide_auto_start = false;
    boolean slide_visible_navigator = false;
    int slide_flip_interval = 10000;
    ImageView.ScaleType slide_scale_type = ImageView.ScaleType.FIT_CENTER;
    Drawable slide_image_preview = null;
    Drawable slide_image_next = null;
    Drawable slide_border_preview = null;
    Drawable slide_border_next = null;

    Context context;

    ViewPager viewPager;
    TabLayout indicator;
    ImageView btn_preview;
    ImageView btn_next;

    View.OnClickListener clickListener;
    View.OnLongClickListener longClickListener;

    public ViewPager getPage() {
        return viewPager;
    }

    public MyImageViewSlide(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public MyImageViewSlide(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        inflate(context, R.layout.component_viewpage_image, this);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyImageViewSlide);
            slide_auto_start = typedArray.getBoolean(R.styleable.MyImageViewSlide_slide_auto_start, slide_auto_start);
            slide_flip_interval = typedArray.getInteger(R.styleable.MyImageViewSlide_slide_flip_interval, slide_flip_interval);
            slide_scale_type = ImageView.ScaleType.values()[typedArray.getInt(R.styleable.MyImageViewSlide_slide_scale_type, 3)];
            slide_visible_navigator = typedArray.getBoolean(R.styleable.MyImageViewSlide_slide_visible_navigator, slide_visible_navigator);
            slide_image_preview = typedArray.getDrawable(R.styleable.MyImageViewSlide_slide_image_preview);
            slide_image_next = typedArray.getDrawable(R.styleable.MyImageViewSlide_slide_image_next);
            slide_border_preview = typedArray.getDrawable(R.styleable.MyImageViewSlide_slide_border_preview);
            slide_border_next = typedArray.getDrawable(R.styleable.MyImageViewSlide_slide_border_next);
            typedArray.recycle();
        }
        viewPager = findViewById(R.id.viewPage);
        indicator = findViewById(R.id.indicator);
        btn_preview = findViewById(R.id.btn_preview);
        btn_next = findViewById(R.id.btn_next);
        indicator.setupWithViewPager(viewPager, true);

        if (slide_auto_start) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new SliderTimer(), slide_flip_interval, slide_flip_interval);
        }
        try {
            if (btn_preview != null) {
                btn_preview.setVisibility(slide_visible_navigator ? View.VISIBLE : View.GONE);
                if (slide_image_preview != null) {
                    btn_preview.setImageDrawable(slide_image_preview);
                }
                if (slide_border_preview != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn_preview.setBackground(slide_border_preview);
                    } else {
                        btn_preview.setBackgroundDrawable(slide_border_preview);
                    }
                }
                btn_preview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNavigate(false);
                    }
                });
            }
            if (btn_next != null) {
                btn_next.setVisibility(slide_visible_navigator ? View.VISIBLE : View.GONE);
                if (slide_image_next != null) {
                    btn_next.setImageDrawable(slide_image_next);
                }
                if (slide_border_next != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        btn_next.setBackground(slide_border_next);
                    } else {
                        btn_next.setBackgroundDrawable(slide_border_next);
                    }
                }
                btn_next.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNavigate(true);
                    }
                });
            }
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
        viewPager.addOnPageChangeListener(this);
        viewPager.setOnTouchListener(new OnSwipeTouchListener() {
            public void onSwipeRight() {
                Log.e("TAGS", "onSwipeRight");
                if (viewPager.getCurrentItem() == 0)
                    viewPager.setCurrentItem(getCount() - 1);
            }

            public void onSwipeLeft() {
                Log.e("TAGS", "onSwipeLeft");
                if (viewPager.getCurrentItem() == getCount() - 1)
                    viewPager.setCurrentItem(0);
            }

        });
    }

    public PagerAdapter getAdapter() {
        return viewPager != null ? viewPager.getAdapter() : null;
    }

    public int getCurrentIndex() {
        return viewPager != null ? viewPager.getCurrentItem() : -1;
    }

    public Object getCurrentItem() {
        return getCurrentItem(getCurrentIndex());
    }

    public Object getCurrentItem(int index) {
        try {
            if (viewPager != null ? index != -1 : false) {
                return viewPager.findViewWithTag("View" + index);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getCount() {
        return viewPager != null ? viewPager.getAdapter() != null ? viewPager.getAdapter().getCount() : 0 : 0;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        clickListener = l;
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        super.setOnLongClickListener(l);
        longClickListener = l;
    }

    public void removeAllView() {
        try {
            if (viewPager != null ? viewPager.getAdapter() != null : false) {
                ((SlideImageAdapter) viewPager.getAdapter()).removeAllView();
            }
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Serializable onSaveInstanceStates() {
        List<ImageModel> data = new ArrayList<>();
        if (viewPager.getAdapter() != null) {
            data.addAll(((SlideImageAdapter) viewPager.getAdapter()).getImages());
        }
        return new ObjectWrapperForBinder(data);
    }

    public void onRestoreInstanceStates(Serializable state) {
        try {
            removeAllView();
            if (state != null ? state instanceof ObjectWrapperForBinder : false) {
                if (((ObjectWrapperForBinder) state).getData() != null ? ((ObjectWrapperForBinder) state).getData() instanceof ArrayList : false) {
                    addImageModel((List<ImageModel>) ((ObjectWrapperForBinder) state).getData());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addImageRessource(int[] resources) {
        try {
            List<ImageModel> images = new ArrayList<>();
            for (int resource : resources) {
                images.add(new ImageModel(resource, slide_scale_type));
            }
            addImageModel(images);
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addImageBitmap(Bitmap[] bitmaps) {
        try {
            List<ImageModel> images = new ArrayList<>();
            for (Bitmap bitmap : bitmaps) {
                images.add(new ImageModel(bitmap, slide_scale_type));
            }
            addImageModel(images);
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addImageModel(List<ImageModel> images) {
        try {
            SlideImageAdapter adapter = new SlideImageAdapter(context, images);
            adapter.setOnClickListener(clickListener);
            adapter.setOnLongClickListener(longClickListener);
            viewPager.setAdapter(adapter);
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addImageBitmap(int index, Bitmap bitmap) {
        try {
            addImageModel(index, new ImageModel(bitmap, slide_scale_type));
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addImageBitmap(Bitmap bitmap) {
        try {
            addImageModel(new ImageModel(bitmap, slide_scale_type));
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addImageModel(int index, ImageModel image) {
        try {
            if (viewPager != null) {
                List<ImageModel> images = new ArrayList<>();
                if (viewPager.getAdapter() == null) {
                    images.add(image);
                } else {
                    images = new ArrayList<>(((SlideImageAdapter) viewPager.getAdapter()).getImages());
                    if (images == null) {
                        images = new ArrayList<>();
                    }
                    if (images.size() > index) {
                        images.add(index, image);
                    } else {
                        images.add(image);
                    }
                }
                addImageModel(images);
                viewPager.setCurrentItem(index);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addImageModel(ImageModel image) {
        try {
            if (viewPager != null) {
                List<ImageModel> images = new ArrayList<>();
                if (viewPager.getAdapter() == null) {
                    images.add(image);
                } else {
                    images = new ArrayList<>(((SlideImageAdapter) viewPager.getAdapter()).getImages());
                    images.add(image);
                }
                addImageModel(images);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removeImageModel(int index, boolean delete) {
        try {
            if (viewPager != null ? viewPager.getAdapter() != null : false) {
                List<ImageModel> images = new ArrayList<>(((SlideImageAdapter) viewPager.getAdapter()).getImages());
                if (delete) {
                    deleteImageModel(index);
                }
                if (index > -1 ? images.size() > index : false) {
                    images.remove(index);
                }
                addImageModel(images);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteImageModel(int index) {
        try {
            if (viewPager != null ? viewPager.getAdapter() != null : false) {
                ((SlideImageAdapter) viewPager.getAdapter()).deleteImage(index);
            }
        } catch (Exception ex) {
            Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void onNavigate(boolean next) {
        try {
            if (viewPager != null ? viewPager.getAdapter() != null ? viewPager.getAdapter().getCount() > 1 : false : false) {
                int position = 0;
                int index = viewPager.getCurrentItem();
                if (index > -1) {
                    if (next) {
                        if (index == getCount() - 1) {
                            position = 0;
                        } else {
                            position = index + 1;
                        }
                    } else {
                        if (index == 0) {
                            position = getCount() - 1;
                        } else {
                            position = index - 1;
                        }
                    }
                }
                viewPager.setCurrentItem(position);
            }
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == getCount()) {

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            try {
                if (viewPager != null ? viewPager.getAdapter() != null ? viewPager.getAdapter().getCount() > 1 : false : false) {
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int current = viewPager.getCurrentItem();
                                if (current == viewPager.getAdapter().getCount() - 1) {
                                    viewPager.setCurrentItem(0);
                                } else {
                                    viewPager.setCurrentItem(current + 1);
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            } catch (Exception ex) {
                Logger.getLogger(MyImageViewSlide.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
