package com.example.newapp.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class FixedViewPager extends ViewPager {
    public FixedViewPager(@NonNull Context context) {
        super(context);
    }

    public FixedViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //关闭动画，解决第一个item 切换到第三个item， 第二个item 显示的问题
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}