package com.qianyri.facepay.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.qianyri.facepay.R;
import com.qianyri.facepay.base.BaseCompatActivity;
import com.qianyri.facepay.utils.TokenUtils;
import com.xuexiang.xutil.app.ActivityUtils;


public class StartActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (TokenUtils.hasToken()){
            ActivityUtils.startActivity(WaitActivity.class);
        } else {
            ActivityUtils.startActivity(LoginActivity.class);
        }
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
