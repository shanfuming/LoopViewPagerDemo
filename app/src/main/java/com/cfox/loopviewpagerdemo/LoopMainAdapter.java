package com.cfox.loopviewpagerdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cfox.looplibrary.LoopPageAdapter;
import com.cfox.loopviewpagerdemo.bean.LoopBean;

import java.util.List;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : LoopViewPagerDemo
 * <br/>PACKAGE_NAME : com.cfox.loopviewpagerdemo
 * <br/>AUTHOR : CFOX
 * <br/>DATA : 2016/9/9 0009
 * <br/>TIME : 16:22
 * <br/>MSG :
 * <br/>************************************************
 */
public class LoopMainAdapter extends LoopPageAdapter<LoopBean> {

    private Context mCtx;

    public LoopMainAdapter(Context context ,@NonNull List<LoopBean> loopBeen) {
        super(loopBeen);
        mCtx = context;
    }

    @Override
    public void onPageSelected(LoopBean loopBean, int position) {
        Log.e("TAG","selected number+++:" + position  + "title:" + loopBean.getTitle());
    }

    @Override
    public View onLoadPage(ImageView view, LoopBean loopBean, int position) {
        Glide.with(mCtx).load(loopBean.getImgUrl()).into(view);
        return view;
    }
}
