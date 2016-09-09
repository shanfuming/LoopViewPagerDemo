package com.cfox.loopviewpagerdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.cfox.loopviewpagerdemo.bean.LoopBean;
import com.cfox.loopviewpagerdemo.loopview.LoopAdapter;
import com.cfox.loopviewpagerdemo.loopview.LoopPageAdapter;

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

    public LoopMainAdapter(@NonNull List<LoopBean> loopBeen) {
        super(loopBeen);
    }

    @Override
    public void onPageSelected(LoopBean loopBean, int position) {

    }

    @Override
    public View onLoadPage(View view, LoopBean loopBean, int position) {
        return null;
    }
}
