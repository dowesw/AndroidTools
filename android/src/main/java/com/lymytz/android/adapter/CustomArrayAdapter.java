package com.lymytz.android.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.lymytz.android.component.ObjectWrapperForBinder;
import com.lymytz.android.tools.Constantes;
import com.lymytz.android.tools.Messages;
import com.lymytz.android.tools.Utils;
import com.lymytz.entitymanager.tools.Classe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CustomArrayAdapter<T extends Serializable> extends ArrayAdapter<T> {

    protected int ressource = -1;
    protected List<T> list = new ArrayList<>();
    protected boolean selection;
    protected boolean separator;

    protected List<Object> views = new ArrayList<>();

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public boolean isSeparator() {
        return separator;
    }

    public void setSeparator(boolean separator) {
        this.separator = separator;
    }

    public CustomArrayAdapter(Context context, int ressource, List<T> list) {
        super(context, ressource, list);
        this.list = list;
        this.ressource = ressource;
    }

    public CustomArrayAdapter(Context context, List<T> list) {
        this(context, 0, list);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public int getPosition(@Nullable T item) {
        return list != null && item != null ? list.indexOf(item) : -1;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return list != null ? getCount() > position ? list.get(position) : null : null;
    }

    public List<T> getValues() {
        return list;
    }

    protected void runOnUiThread(Runnable runnable) {
        try {
            Activity activity = getContext() != null ? Utils.unwrap(getContext()) : null;
            if (activity != null) {
                activity.runOnUiThread(runnable);
            } else {
                runnable.run();
            }
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    protected void setDefiniedSelection(ViewHolder viewHolder, FragmentWatcher fragment) {
        try {
            if (viewHolder.selected != null) {
                viewHolder.selected.setVisibility(isSelection() ? View.VISIBLE : View.GONE);
                viewHolder.selected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewHolder.getEntity() == null) {
                            return;
                        }
                        if (viewHolder.selected.isChecked()) {
                            fragment.getInstance().selections.add(viewHolder.getEntity());
                        } else {
                            int index = fragment.getInstance().selections.indexOf(viewHolder.getEntity());
                            if (index > -1) {
                                fragment.getInstance().selections.remove(index);
                            }
                        }
                        if (getContext() != null ? getContext() instanceof Activity : false) {
                            ((Activity) getContext()).invalidateOptionsMenu();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public View contructView(int i, View view, ViewGroup parent) {
        if (isSeparator()) {
            View separator = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
            params.setMargins(0, 5, 0, 0);
            separator.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                separator.setBackgroundColor(getContext().getColor(android.R.color.darker_gray));
            } else {
                separator.setBackgroundColor(getContext().getResources().getColor(android.R.color.darker_gray));
            }
            ((ViewGroup) view).addView(separator);
        }
        return view;
    }

    public void notifyDataSetChanged() {
        try {
            runOnUiThread(() -> {
                super.notifyDataSetChanged();
            });
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public void notifyDataSetInvalidated() {
        try {
            runOnUiThread(() -> {
                super.notifyDataSetInvalidated();
            });
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public void addAll(List<T> list) {
        try {
            if (this.list != null && list != null) {
                for (T item : list) {
                    if (!this.list.contains(item)) {
                        this.list.add(item);
                    }
                }
                notifyDataSetChanged();
            }
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public void insert(T entity) {
        try {
            if (!this.list.contains(entity)) {
                list.add(0, entity);
            }
            notifyDataSetChanged();
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public void update(T entity) {
        try {
            int index = list.indexOf(entity);
            if (index > -1) {
                list.set(index, entity);
                notifyDataSetChanged();
            }
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public void delete(T entity) {
        try {
            list.remove(entity);
            notifyDataSetChanged();
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public void clear() {
        try {
            list.clear();
            notifyDataSetChanged();
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

    public abstract void select(T entity);

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void select(T entity, Class CLass) {
        try {
            Intent intent = new Intent(getContext(), CLass);
            final Bundle bundle = new Bundle();
            bundle.putBinder(Constantes.TAG_ENTITY, new ObjectWrapperForBinder(entity));
            intent.putExtras(bundle);
            Utils.startActivity(getContext(), intent);
        } catch (Exception ex) {
            Messages.Exception(getContext(), ex);
        }
    }

//    public static class ViewState {
//        public View view;
//        public int position;
//
//        public ViewState(int position) {
//            this.position = position;
//        }
//
//        public ViewState(View view, int position) {
//            this(position);
//            this.view = view;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            ViewState viewState = (ViewState) o;
//            return position == viewState.position;
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        public int hashCode() {
//            return Objects.hash(position);
//        }
//    }

    public static abstract class ViewHolder {
        public CheckBox selected;

        public abstract Classe getEntity();
    }
}
