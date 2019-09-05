package com.example.zzgcalendar.view.wheelview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zzgcalendar.view.wheelview.adapter.WheelViewAdapter;


/**
 * Created by Admin on 2017/9/27.
 */

public class BaseWheelView extends View {
    public BaseWheelView(Context context) {
        super(context);
    }

    public BaseWheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Cyclic
    public boolean isCyclic = false;

    /**
     * Tests if wheel is cyclic. That means before the 1st item there is shown
     * the last one
     *
     * @return true if wheel is cyclic
     */
    public boolean isCyclic() {
        return isCyclic;
    }

    // View adapter
    public WheelViewAdapter viewAdapter;

    /**
     * Gets view adapter
     *
     * @return the view adapter
     */
    public WheelViewAdapter getViewAdapter() {
        return viewAdapter;
    }
}
