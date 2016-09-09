package com.cfox.loopviewpagerdemo;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cfox.loopviewpagerdemo.bean.LoopBean;
import com.cfox.loopviewpagerdemo.loopview.CircleIndicator;
import com.cfox.loopviewpagerdemo.loopview.LoopAdapterWrapper;
import com.cfox.loopviewpagerdemo.loopview.LoopListener;
import com.cfox.loopviewpagerdemo.loopview.LoopPageAdapter;
import com.cfox.loopviewpagerdemo.loopview.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private LoopViewPager mViewPager;
    private CircleIndicator mPointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<LoopBean> list = new ArrayList<>();
        LoopBean bean = new LoopBean();
        bean.setImgUrl("http://i-3.yiwan.com/2016/8/25/W3dtOjEucG5nLHI6MTMsYjoxM10=/7841adb0-7039-4c52-8e13-d0e3d3bc7ac4.png");
        bean.setTitle("good");
        list.add(bean);

        bean = new LoopBean();
        bean.setImgUrl("http://img2015.zdface.com/20160816/e11b00654e7441fb4143dfcf8d1ab487.jpg");
        bean.setTitle("北京欢迎您");
        list.add(bean);

        mViewPager = (LoopViewPager) findViewById(R.id.loop_view);
        mViewPager.setAdapter(new LoopMainAdapter(list));
//        mViewPager.setOnItemClickListener();
        mViewPager.setOnPageChangeListener(new ChangeListener());



        mPointView = (CircleIndicator) findViewById(R.id.loop_point);
        mPointView.setViewPager(mViewPager);

    }

    private class HeaderAdapter  extends PagerAdapter{

        String[] imgUrls = {
                "http://i-3.yiwan.com/2016/8/25/W3dtOjEucG5nLHI6MTMsYjoxM10=/7841adb0-7039-4c52-8e13-d0e3d3bc7ac4.png",
                "http://img2015.zdface.com/20160816/e11b00654e7441fb4143dfcf8d1ab487.jpg",
                "http://cdn2.bjweekly.com/news/image2/132/17890456360124417156.jpg",
                "http://cdn.duitang.com/uploads/item/201509/14/20150914113327_ahWQs.jpeg",
        };


        @Override
        public int getCount() {
            return imgUrls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(getApplication());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this,"onClick  ...." + position, Toast.LENGTH_SHORT).show();
                }
            });
            Glide.with(MainActivity.this).load(imgUrls[position]).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);

        }
    }

    private class ChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
