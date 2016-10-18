package com.example.fanyan.fanyanproject;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by fanyan on 2016/10/3.
 */
public class ADAdapter extends PagerAdapter {
    private List<ImageView> adViews;

    public ADAdapter(List<ImageView> adViews){
        this.adViews = adViews;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(adViews.get(position % adViews.size()));
        return adViews.get(position  % adViews.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(adViews.get(position  % adViews.size()));
    }
}
