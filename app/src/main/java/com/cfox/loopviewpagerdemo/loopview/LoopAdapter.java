package com.cfox.loopviewpagerdemo.loopview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cfox.loopviewpagerdemo.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : LoopViewPagerDemo
 * <br/>PACKAGE_NAME : com.cfox.loopviewpagerdemo.loopview
 * <br/>AUTHOR : CFOX
 * <br/>DATA : 2016/9/9 0009
 * <br/>TIME : 10:38
 * <br/>MSG :
 * <br/>************************************************
 */
public class LoopAdapter<T> extends PagerAdapter{
    private static final String TAG = "LoopAdapter";
    private List<T> mLoopDatas;
    private Context mCtx;

    private LoopPageAdapter mLoopPageAdapter;

    private LoopListener<T> mClickListener;

    public LoopAdapter(Context context , LoopPageAdapter adapter){
        this.mLoopDatas.clear();
        this.mCtx = context;
        this.mLoopPageAdapter = adapter;
        this.mLoopDatas = adapter.getDatas();
    }

    public void setOnClickListener(LoopListener listener){
        this.mClickListener = listener;
    }


    @Override
    public int getCount() {
        return mLoopDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = new ImageView(mCtx);
        View viewBuffer = null;
        ((ImageView)view).setScaleType(ImageView.ScaleType.CENTER_CROP);

        if(mLoopPageAdapter != null){
            viewBuffer = mLoopPageAdapter.onLoadPage(view,mLoopDatas.get(position),position);
            if(viewBuffer != null){
                view = viewBuffer;
                viewBuffer = null;
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener != null){
                    mClickListener.onItemClickListener(mLoopDatas.get(position),position);
                }
            }
        });
//        Glide.with(MainActivity.this).load(imgUrls[position]).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);

    }


}
