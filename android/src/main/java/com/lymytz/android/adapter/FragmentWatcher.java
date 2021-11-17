package com.lymytz.android.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public abstract class FragmentWatcher<T extends Serializable> extends Fragment {

    public List<Object> selections = new ArrayList<>();

    public List<Object> getSelections() {
        return selections;
    }

    public abstract FragmentWatcher getInstance();

    public abstract CustomArrayAdapter getAdapter();
}
