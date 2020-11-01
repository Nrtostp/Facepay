package com.qianyri.facepay;

import android.util.Log;

import com.qianyri.facepay.bmob.BmobManager;

import org.junit.Test;

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
    public void addition_isCorrect() {
        CharSequence s="23.5";
        String str=(String)s;
        int idx=str.indexOf('.');
        System.out.println(String.valueOf(idx));
    }


}