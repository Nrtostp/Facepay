package com.qianyri.facepay.receiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.orhanobut.logger.Logger;
import com.qianyri.facepay.R;
import com.qianyri.facepay.activity.MainActivity;
import com.qianyri.facepay.activity.PayActivity;
import com.qianyri.facepay.service.Pay;
import com.xuexiang.xutil.app.ActivityUtils;

import cn.bmob.push.PushConstants;

import cn.bmob.v3.util.BmobNotificationManager;

//TODO 集成：1.3、创建自定义的推送消息接收器，并在清单文件中注册
public class PushMessageReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            ActivityUtils.startActivity(PayActivity.class);
        }
    }

}