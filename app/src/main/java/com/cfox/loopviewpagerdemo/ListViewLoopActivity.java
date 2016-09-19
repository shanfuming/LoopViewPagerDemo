package com.cfox.loopviewpagerdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cfox.looplibrary.CircleIndicator;
import com.cfox.looplibrary.LoopListener;
import com.cfox.looplibrary.LoopViewPager;
import com.cfox.loopviewpagerdemo.adapter.LoopMainAdapter;
import com.cfox.loopviewpagerdemo.bean.LoopBean;
import com.cfox.loopviewpagerdemo.data.DataFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : LoopViewPagerDemo
 * <br/>PACKAGE_NAME : com.cfox.loopviewpagerdemo
 * <br/>AUTHOR : CFOX
 * <br/>DATA : 2016/9/12 0012
 * <br/>TIME : 9:55
 * <br/>MSG :
 * <br/>************************************************
 */
public class ListViewLoopActivity extends Activity {

    private ListView mPullListView;

    private SimpleAdapter mSimpleAdapter;

    private List<Map<String,String>> mListData = new ArrayList<Map<String,String>>();

    private Context mContext;


    private LoopViewPager mViewPager;
    private CircleIndicator mPointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mContext = this;
        initView();
        initData();
        setListViewData();
    }

    private void initView() {
        mPullListView = (ListView) findViewById(R.id.listview);

        View loopView = LayoutInflater.from(this).inflate(R.layout.loop_view,null);
        mPullListView.addHeaderView(loopView);

        mViewPager = (LoopViewPager) loopView.findViewById(R.id.loop_view);
        mViewPager.setAdapter(new LoopMainAdapter(this, DataFactory.getLoopData()));
        mViewPager.setOnItemClickListener(new LoopListener<LoopBean>() {
            @Override
            public void onItemClickListener(LoopBean loopBean, int position) {

                Toast.makeText(ListViewLoopActivity.this,"title--->" + loopBean.getTitle(),Toast.LENGTH_SHORT).show();

            }
        });

        mPointView = (CircleIndicator) findViewById(R.id.loop_point);
        mPointView.setViewPager(mViewPager);
    }



    private void initData() {
        int index = mListData.size();
        for(int i = index; i < index + 40 ; i ++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("index","index = " + i + ",content" + i);
            mListData.add(map);
        }
    }

    private void setListViewData() {
        mSimpleAdapter = new SimpleAdapter(this,mListData,R.layout.item_listview,new String[]{"index"},new int[]{R.id.index});
        mPullListView.setAdapter(mSimpleAdapter);
    }
}
