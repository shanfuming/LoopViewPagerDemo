package com.cfox.loopviewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.cfox.looplibrary.CircleIndicator;
import com.cfox.looplibrary.LoopListener;
import com.cfox.looplibrary.LoopViewPager;
import com.cfox.loopviewpagerdemo.adapter.LoopMainAdapter;
import com.cfox.loopviewpagerdemo.bean.LoopBean;
import com.cfox.loopviewpagerdemo.data.DataFactory;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : LoopViewPagerDemo
 * <br/>PACKAGE_NAME : com.cfox.loopviewpagerdemo
 * <br/>AUTHOR : Machao
 * <br/>DATA : 2016/9/10 0010
 * <br/>TIME : 14:08
 * <br/>MSG :
 * <br/>************************************************
 */
public class LoopActivity extends Activity {
    private LoopViewPager mViewPager;
    private CircleIndicator mPointView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_looop);

        mViewPager = (LoopViewPager) findViewById(R.id.loop_view);
        mViewPager.setAdapter(new LoopMainAdapter(this, DataFactory.getLoopData()));
        mViewPager.setOnItemClickListener(new LoopListener<LoopBean>() {
            @Override
            public void onItemClickListener(LoopBean loopBean, int position) {

                Toast.makeText(LoopActivity.this,"title--->" + loopBean.getTitle(),Toast.LENGTH_SHORT).show();

            }
        });

        mPointView = (CircleIndicator) findViewById(R.id.loop_point);
        mPointView.setViewPager(mViewPager);
    }
}
