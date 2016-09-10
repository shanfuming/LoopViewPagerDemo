package com.cfox.looplibrary;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : LoopView
 * <br/>PACKAGE_NAME : com.cfox.looplibrary
 * <br/>AUTHOR : Machao
 * <br/>DATA : 2016/9/8 0008
 * <br/>TIME : 21:04
 * <br/>MSG :
 * <br/>************************************************
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


import java.util.ArrayList;

/**
 * 需要实现循环滚动的时候，不需要改动代码，只需要把xml的节点换成LoopViewPager就可以了
 * 如果滑动到边界的时候，出现了闪现的情况，调用setBoundaryCaching( true ), 或者 DEFAULT_BOUNDARY_CASHING 变成 true
 * 如果使用 FragmentPagerAdapter 或者 FragmentStatePagerAdapter, 必须改变Adapter，使其多创建两个条目，比如
 * original adapter position    [0,1,2,3]
 * modified adapter position  [0,1,2,3,4,5]
 * modified     realPosition  [3,0,1,2,3,0]
 * modified     InnerPosition [4,1,2,3,4,1]
 */
public class LoopViewPager extends ViewPager {

    private Context mContext;

    /** 原始的Adapter */
    private LoopAdapter mAdapter;
    /** 实现了循环滚动的Adapter */
    private LoopAdapterWrapper mLoopAdapter;

    /** 内部定义的监听器 */
    private OnPageChangeListener loopPageChangeListener;
    /** 外部通过set传进来的 */
    private OnPageChangeListener mOnPageChangeListener;
    /** 外部通过add传进来的 */
    private ArrayList<OnPageChangeListener> mOnPageChangeListeners;

    /** 处理轮播的Handler  */
    private Handler mHandler;
    /** 是否自动轮播 */
    private boolean mIsAutoLoop = true;
    /** 轮播的延时时间 */
    private int mDelayTime = 3000;
    /** 是否被回收过 */
    private boolean isDetached;
    /** 当前的条目位置 */
    private int currentPosition;

    /** Loop 中点击事件监听 */
    private LoopListener mListener;

    private LoopPageAdapter mLoopPageAdapter;

    public LoopViewPager(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        loopPageChangeListener = new MyOnPageChangeListener();
        super.addOnPageChangeListener(loopPageChangeListener);

        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.LoopViewPager);
        mIsAutoLoop = a.getBoolean(R.styleable.LoopViewPager_lvp_isAutoLoop, mIsAutoLoop);
        mDelayTime = a.getInteger(R.styleable.LoopViewPager_lvp_delayTime, mDelayTime);
        a.recycle();

        setAutoLoop(mIsAutoLoop, mDelayTime,true);
    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();
        if (isDetached) {
            if (loopPageChangeListener != null) {
                super.addOnPageChangeListener(loopPageChangeListener);
            }
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(0, mDelayTime);
            }
            isDetached = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (loopPageChangeListener != null) {
            super.removeOnPageChangeListener(loopPageChangeListener);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        isDetached = true;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("currentPosition", currentPosition);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("superState"));
            currentPosition = bundle.getInt("currentPosition");
        } else {
            super.onRestoreInstanceState(state);
        }
        setCurrentItem(currentPosition);
    }

    /**
     * 默认返回的是传进来的Adapter
     */
    @Override
    public PagerAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = mLoopAdapter == null ? 0 : mLoopAdapter.getInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    @Override
    public int getCurrentItem() {
        return mLoopAdapter == null ? 0 : mLoopAdapter.toRealPosition(super.getCurrentItem());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    @Override
    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    private class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            int realPosition = mLoopAdapter == null ? 0 : mLoopAdapter.toRealPosition(position);
            currentPosition = realPosition;

            //分发事件给外部传进来的监听
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(realPosition);
            }

            if(mLoopPageAdapter != null){
                mLoopPageAdapter.onPageSelected(mLoopPageAdapter.getDatas().get(realPosition),realPosition);
            }

            if (mOnPageChangeListeners != null) {
                for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageSelected(realPosition);
                    }
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = mLoopAdapter == null ? 0 : mLoopAdapter.toRealPosition(position);

            //分发事件给外部传进来的监听
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
            if (mOnPageChangeListeners != null) {
                for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //当滑动到了第一页 或者 最后一页的时候，跳转到指定的对应页
            //不能在onPageSelected中写这段逻辑，因为onPageSelected当松手的时候，就调用了
            //不是在滑动结束后再调用
            int position = LoopViewPager.super.getCurrentItem();
            int realPosition = mLoopAdapter == null ? 0 : mLoopAdapter.toRealPosition(position);
            int count = mLoopAdapter == null ? 0 : mLoopAdapter.getCount();
            if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0 || position == count - 1)) {
                setCurrentItem(realPosition, false);
            }

            //分发事件给外部传进来的监听
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
            if (mOnPageChangeListeners != null) {
                for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageScrollStateChanged(state);
                    }
                }
            }
        }
    }

    public void setAdapter(LoopPageAdapter pageAdapter) {
        mLoopPageAdapter = pageAdapter;
        mAdapter = new LoopAdapter(mContext,pageAdapter);
        if (mListener != null) mAdapter.setOnClickListener(mListener);
        mLoopAdapter = new LoopAdapterWrapper(mAdapter);
        super.setAdapter(mLoopAdapter);
        setCurrentItem(0, false);
    }

    public void setOnItemClickListener(LoopListener listener){
        mListener = listener;
        if(mAdapter != null) mAdapter.setOnClickListener(mListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mIsAutoLoop)
                setAutoLoop(mIsAutoLoop,mDelayTime,false);
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if (mIsAutoLoop)
                setAutoLoop(mIsAutoLoop,mDelayTime,true);
                break;

            case MotionEvent.ACTION_CANCEL:
                if (mIsAutoLoop)
                    setAutoLoop(mIsAutoLoop,mDelayTime,true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置是否自动轮播  delayTime延时的毫秒
     */
    public void setAutoLoop(boolean isAutoLoop, int delayTime,boolean isRun) {
        mIsAutoLoop = isAutoLoop;
        mDelayTime = delayTime;
        if (mIsAutoLoop && isRun) {
            if (mHandler == null) {
                mHandler = new InnerHandler();
                mHandler.sendEmptyMessageDelayed(0, mDelayTime);
            } else {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(0, mDelayTime);
            }
        } else {
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
        }
    }

    /**
     * 自动轮播的Handler
     */
    private class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            setCurrentItem(getCurrentItem() + 1);
            sendEmptyMessageDelayed(0, mDelayTime);
        }
    }
}

