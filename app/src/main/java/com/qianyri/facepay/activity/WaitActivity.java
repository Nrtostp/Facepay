package com.qianyri.facepay.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.qianyri.facepay.R;
import com.qianyri.facepay.base.BaseCompatActivity;

import cn.bmob.push.BmobPush;

public class WaitActivity extends BaseCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        BmobPush.startWork(this);
    }

    @Override
    public void onClick(View v) {

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
