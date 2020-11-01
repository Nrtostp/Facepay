package com.qianyri.facepay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.qianyri.facepay.R;
import com.qianyri.facepay.base.BaseCompatActivity;
import com.qianyri.facepay.bmob.User;
import com.qianyri.facepay.service.UserManager;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StartActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid","");
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(userid)){
            intent.setClass(StartActivity.this, MainActivity.class);
        } else {
            intent.setClass(StartActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
