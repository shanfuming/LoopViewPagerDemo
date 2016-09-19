package com.cfox.loopviewpagerdemo.data;

import com.cfox.loopviewpagerdemo.bean.LoopBean;

import java.util.ArrayList;
import java.util.List;

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
public class DataFactory {

    public static List getLoopData(){
        List<LoopBean> list = new ArrayList<>();
        LoopBean bean = new LoopBean();
        bean.setImgUrl("http://i-3.yiwan.com/2016/8/25/W3dtOjEucG5nLHI6MTMsYjoxM10=/7841adb0-7039-4c52-8e13-d0e3d3bc7ac4.png");
        bean.setTitle("good");
        list.add(bean);

        bean = new LoopBean();
        bean.setImgUrl("http://img2015.zdface.com/20160816/e11b00654e7441fb4143dfcf8d1ab487.jpg");
        bean.setTitle("北京欢迎您");
        list.add(bean);

        bean = new LoopBean();
        bean.setImgUrl("http://cdn2.bjweekly.com/news/image2/132/17890456360124417156.jpg");
        bean.setTitle("长春也欢迎您");
        list.add(bean);

        bean = new LoopBean();
        bean.setImgUrl("http://cdn.duitang.com/uploads/item/201509/14/20150914113327_ahWQs.jpeg");
        bean.setTitle("您好马先生");
        list.add(bean);

        return list;

    }
}
