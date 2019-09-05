package com.example.zzgcalendar.view.wheelview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 2019/1/16.
 * 滑动。农历
 */
public class NumericLunarWheelAdapter extends AbstractWheelTextAdapter {

    private String[] lunarDateStrs;
    private String label;
    protected NumericLunarWheelAdapter(Context context) {
        super(context);
    }


    public NumericLunarWheelAdapter(Context context, String[] lunarDateStrs) {
        super(context);
        this.lunarDateStrs = lunarDateStrs;
    }


    @Override
    public int getItemsCount() {
        return lunarDateStrs.length;
    }


    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        if (index >= 0 && index < getItemsCount()) {
            if (convertView == null) {
                convertView = getView(itemResourceId, parent);
            }
            TextView textView = getTextView(convertView, itemTextResourceId);
            if (textView != null) {
                CharSequence text = getItemText(index);
                if (text == null) {
                    text = "";
                }
                textView.setText(text + label);

                if (itemResourceId == TEXT_VIEW_ITEM_RESOURCE) {
                    configureTextView(textView);
                }
            }
            return convertView;
        }
        return null;
    }

    @Override
    protected CharSequence getItemText(int index) {
        return lunarDateStrs[index];
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
