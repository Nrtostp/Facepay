package com.qianyri.facepay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qianyri.facepay.R;
import com.qianyri.facepay.base.BaseCompatActivity;
import com.qianyri.facepay.bmob.BmobManager;
import com.qianyri.facepay.bmob.User;
import com.qianyri.facepay.service.Login;
import com.qianyri.facepay.service.NetworkTester;
import com.xuexiang.xui.widget.toast.XToast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class LoginActivity extends BaseCompatActivity implements View.OnClickListener {
    private EditText login_userid_text;
    private EditText login_code_text;
    private TextView login_getcode_bt;
    private TextView login_usecode_bt;
    private TextView login_useface_bt;
    private String userid;
    private String code;
    private Handler handler;
    private int count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
        initData();
        //默认获取短信和验证按钮不可点击，输入达到规范后，可点击
        login_usecode_bt.setEnabled(false);
        login_getcode_bt.setEnabled(false);
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        login_userid_text = findViewById(R.id.login_userid_text);
        login_code_text = findViewById(R.id.login_code_text);
        login_getcode_bt = findViewById(R.id.login_getcode_bt);
        login_usecode_bt = findViewById(R.id.login_usecode_bt);
        login_useface_bt = findViewById(R.id.login_useface_bt);
        login_getcode_bt.setOnClickListener(this);
        login_usecode_bt.setOnClickListener(this);
        login_useface_bt.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        login_userid_text.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_getcode_bt.setEnabled(login_userid_text.getText() != null && login_userid_text.getText().length() == 11);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_code_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_usecode_bt.setEnabled(login_code_text.getText() != null && login_code_text.getText().length() >= 6 && login_userid_text.getText() != null && login_userid_text.getText().length() == 11);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    login_getcode_bt.setEnabled(true);
                    login_getcode_bt.setText("重新发送验证码");
                } else {
                    count--;
                    login_getcode_bt.setText("发送验证码(" + count + "s)");
                    handler.sendEmptyMessageDelayed(count,1000);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_getcode_bt:
                login_getcode_bt.setEnabled(false);
                count=60;
                handler.sendEmptyMessage(count);
                if(!NetworkTester.get().testNetwork(LoginActivity.this.getApplicationContext())) {
                    XToast.warning(LoginActivity.this,"网络连接失败 请稍候再试").show();
                    break;
                }
                BmobManager.get().sendCode(login_userid_text.getText().toString().trim(), new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            XToast.success(LoginActivity.this,"发送成功").show();
                        } else {
                            XToast.warning(LoginActivity.this,"发送失败 " + e.getMessage()).show();
                        }
                    }
                });
                break;
            case R.id.login_usecode_bt:
                if(!NetworkTester.get().testNetwork(LoginActivity.this.getApplicationContext())) {
                    XToast.warning(LoginActivity.this,"网络连接失败 请稍候再试").show();
                    break;
                }
                userid=login_userid_text.getText().toString().trim();
                code=login_code_text.getText().toString().trim();
                Login login = new Login();
                login.checkCode(userid, code, new Login.LoginCallBack() {
                    @Override
                    public void done(User user) {
                        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userid",userid);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        XToast.success(LoginActivity.this,"验证成功").show();
                        finish();
                    }
                    @Override
                    public void error(String msg, int errorCode) {
                        XToast.warning(LoginActivity.this,msg + errorCode).show();
                    }
                });
                break;
            case R.id.login_useface_bt:
                if(!NetworkTester.get().testNetwork(LoginActivity.this.getApplicationContext())) {
                    XToast.warning(LoginActivity.this,"网络连接失败 请稍候再试").show();
                    break;
                }
                Intent intent = new Intent(LoginActivity.this, DetectLoginActivity.class);
                startActivity(intent);
                break;
        }
    }

}