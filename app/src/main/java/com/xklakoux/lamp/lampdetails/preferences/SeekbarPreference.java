package com.xklakoux.lamp.lampdetails.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.xklakoux.lamp.R;

/**
 * Created by artur on 08/05/16.
 */
public class SeekbarPreference extends Preference {

    private final int mMax;
    private SeekBar mSeekbar;

    public SeekbarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SeekbarPreference,
                0, 0);

        try {
            mMax = a.getInteger(R.styleable.SeekbarPreference_seekbar_max, 100);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = li.inflate(R.layout.seekbar_preference, parent, false);
        mSeekbar = (SeekBar) root.findViewById(R.id.seekbar);
        mSeekbar.setMax(mMax);
        return root;
    }
}
