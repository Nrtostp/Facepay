package com.qianyri.facepay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianyri.facepay.R;
import com.qianyri.facepay.base.BaseCompatActivity;
import com.qianyri.facepay.bmob.User;
import com.qianyri.facepay.service.NetworkTester;
import com.qianyri.facepay.service.UserManager;
import com.xuexiang.xui.widget.toast.XToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class MainActivity extends BaseCompatActivity  implements View.OnClickListener  {
    private TextView reg_btn;
    private TextView pay_btn;
    private TextView name_text;
    private TextView id_text;
    private TextView money_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();
        reg_btn.setEnabled(true);
        pay_btn.setEnabled(true);
        if(!NetworkTester.get().testNetwork(MainActivity.this.getApplicationContext())){
            XToast.warning(MainActivity.this,"网络连接失败 请检查网络").show();
        }
            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
            String userid = sharedPreferences.getString("userid","");
            BmobQuery<User> query = new BmobQuery<>();
            query.addWhereEqualTo("userid",userid);
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(final List<User> list, BmobException e) {
                    if (e == null){
                        if (list.size() > 0 ){
                            User user = list.get(0);
                            UserManager.get().setUser(user);
                            name_text.setText(UserManager.get().getUser().getNickname());
                            id_text.setText(UserManager.get().getUser().getUserid());
                            money_text.setText(UserManager.get().getUser().getMoney().toString());
                        }
                    } else {
                        XToast.warning(MainActivity.this,e.getErrorCode()+e.getMessage()).show();
                    }
                }
            });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid","");
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("userid",userid);
        query.findObjects(new FindListener<User>() {
                @Override
                public void done(final List<User> list, BmobException e) {
                    if (e == null){
                        if (list.size() > 0 ){
                            User user = list.get(0);
                            UserManager.get().setUser(user);
                            name_text.setText(UserManager.get().getUser().getNickname());
                            id_text.setText(UserManager.get().getUser().getUserid());
                            money_text.setText(UserManager.get().getUser().getMoney().toString());
                        }
                    } else {
                        XToast.warning(MainActivity.this,e.getErrorCode()+e.getMessage()).show();
                    }
                }
            });
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        reg_btn=(TextView)findViewById(R.id.reg_btn);
        pay_btn=(TextView)findViewById(R.id.pay_btn);
        name_text=(TextView)findViewById(R.id.name_text);
        id_text=(TextView)findViewById(R.id.id_text);
        money_text=(TextView)findViewById(R.id.money_text);
        reg_btn.setOnClickListener(this);
        pay_btn.setOnClickListener(this);

    }

    @Override
    public void initData() {


    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_btn:
                if(!NetworkTester.get().testNetwork(MainActivity.this.getApplicationContext())) {
                    XToast.warning(MainActivity.this,"网络连接失败 请稍候再试").show();
                    break;
                }
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, RegActivity.class);
                startActivity(intent1);
                break;
            case R.id.pay_btn:
                if(!NetworkTester.get().testNetwork(MainActivity.this.getApplicationContext())) {
                    XToast.warning(MainActivity.this,"网络连接失败 请稍候再试").show();
                    break;
                }
                Intent intent2=new Intent();
                intent2.setClass(MainActivity.this, PayActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
