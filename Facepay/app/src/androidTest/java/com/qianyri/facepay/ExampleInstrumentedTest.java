package com.qianyri.facepay;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.qianyri.facepay.bmob.BmobManager;
import com.qianyri.facepay.bmob.User;
import com.qianyri.facepay.service.UserManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.qianyri.facepay", appContext.getPackageName());
    }

    @Test
    public void Testsendcode(){
        CharSequence s="23.5";
        String str=(String)s;
        int idx=str.indexOf('.');
        Log.e("asd", String.valueOf(idx));
    }
}
