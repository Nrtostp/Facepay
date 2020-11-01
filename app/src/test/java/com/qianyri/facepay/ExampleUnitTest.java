package com.qianyri.facepay;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qianyri.facepay.bmob.BmobManager;

import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {


    @Test
    public void Testsendcode(){
        Goods g1=new Goods("大米",2.5,20);
        Goods g2=new Goods("萝卜",1.0,2);
        Goods g3=new Goods("苹果",5.1,3);
        List<Goods> list=new ArrayList<Goods>();
        list.add(g1);
        list.add(g2);
        list.add(g3);
        String msg= JSONArray.toJSONString(list);
        System.out.println(msg);
        list= JSONArray.parseArray(msg,Goods.class);
        for (Goods x:list
             ) {
            System.out.println(x.getKind());
        }
    }

}