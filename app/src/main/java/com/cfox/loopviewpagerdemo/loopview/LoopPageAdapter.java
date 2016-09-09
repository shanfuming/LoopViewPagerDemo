package com.cfox.loopviewpagerdemo.loopview;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : LoopViewPagerDemo
 * <br/>PACKAGE_NAME : com.cfox.loopviewpagerdemo.loopview
 * <br/>AUTHOR : CFOX
 * <br/>DATA : 2016/9/9 0009
 * <br/>TIME : 10:45
 * <br/>MSG :
 * <br/>************************************************
 */
public abstract class LoopPageAdapter<T> {

    private List<T> mDatas = new ArrayList<>();

//    public interface LoopListeners<V extends T> {
//        public void onItemClickListener(T t, int position);
//    }

    public LoopPageAdapter(List<T> ts){
        mDatas.clear();
        if (ts == null){
            return;
        }
        mDatas.addAll(ts);
    }

    public void setData(List<T> ts){
        mDatas.clear();
        if (ts == null){
            return;
        }
        mDatas.addAll(ts);
    }

    public List<T> getDatas(){
        return mDatas;
    }


    public abstract void onPageSelected(T t, int position);

    public abstract View onLoadPage(View view,T t, int position);
}
