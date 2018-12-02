package com.jifisher.infohouses.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jifisher.infohouses.R;
import com.jifisher.infohouses.MainActivity;

public class SwipeImagesView extends RelativeLayout {
    public String text;
    public Drawable image;
    public ViewPager mediumback;
    public TabLayout tab_layout;
    public PercentRelativeLayout backRelativeLayout;


    public SwipeImagesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public SwipeImagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public SwipeImagesView(Context context) {
        super(context);
        initialize();
    }


    public void initialize() {
        LayoutInflater li = ((MainActivity) getContext()).getLayoutInflater();
        li.inflate(R.layout.swipe_images_view, this);
        this.backRelativeLayout = findViewById(R.id.backBigMenuView);
        this.tab_layout = findViewById(R.id.tab_layout);
        this.mediumback = findViewById(R.id.view_pager_wl);
        this.setBackgroundColor(Color.parseColor("#00000000"));
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setPage(final ImageView... mediumMenuViews){
        this.mediumback.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mediumMenuViews.length;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view;
                    view = mediumMenuViews[position];
                container.addView(view);
                return view;
            }
        });
        tab_layout.setupWithViewPager(mediumback);
        TabLayout.Tab tab = tab_layout.getTabAt(0);
        tab.select();
        mediumback.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}


