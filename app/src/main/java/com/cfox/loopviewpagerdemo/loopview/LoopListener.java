package com.cfox.loopviewpagerdemo.loopview;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : LoopViewPagerDemo
 * <br/>PACKAGE_NAME : com.cfox.loopviewpagerdemo.loopview
 * <br/>AUTHOR : CFOX
 * <br/>DATA : 2016/9/9 0009
 * <br/>TIME : 10:50
 * <br/>MSG :
 * <br/>************************************************
 */
public interface LoopListener<T> {
    public void onItemClickListener(T t, int position);
}
