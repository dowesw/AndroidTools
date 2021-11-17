package com.lymytz.android.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.lymytz.android.tools.Messages;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context context;
    FragmentManager manager;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public ViewPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
        this.manager = manager;
    }

    public ViewPagerAdapter(Context context, FragmentManager manager, List<Fragment> fragments) {
        this(context, manager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (position > -1 && getCount() > 0 && getCount() > position) {
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void removeAll() {
        fragments.clear();
        titles.clear();
    }

    public void addFragment(Fragment fragment, CharSequence title) {
        try {
            if (fragment != null ? !fragments.contains(fragment) : false) {
                titles.add(title.toString());
                if (fragment.getContext() == null) {
                    fragment.onAttach(context);
                }
                fragments.add(fragment);
            }
        } catch (Exception ex) {
            Messages.Exception(context, ex);
        }
    }
}
