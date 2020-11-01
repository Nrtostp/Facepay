/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.qianyri.facepay.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.qianyri.facepay.R;
import com.qianyri.facepay.baidu.APIService;
import com.qianyri.facepay.baidu.exception.FaceError;
import com.qianyri.facepay.baidu.model.RegResult;
import com.qianyri.facepay.baidu.utils.ImageSaveUtil;
import com.qianyri.facepay.baidu.utils.OnResultListener;
import com.qianyri.facepay.base.BaseCompatActivity;
import com.qianyri.facepay.bmob.User;
import com.qianyri.facepay.service.Pay;
import com.qianyri.facepay.service.UserManager;
import com.xuexiang.xui.widget.toast.XToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 此登录方式 认证登录：先使用用户名拿到uid（uid可以保存在终端，不在显示用户名输入界面），再使用uid和人脸 调用https://aip.baidubce.com/rest/2.0/face/v3/search接口
 * 演示示例为了跑通流程，简单省略的服务端，实际使用中建议采用，在移动端使用用户名+人脸（替代密码）请求你的服务端，根据用户名获取uid后，
 * 在您的服务端使用uid + 人脸 调用百度verify接口，根据verfiy返回的分数判断是否是通一个人（建议80分，），以此判断是否登录成功，
 * 最后登录信息返回给移动端
 */

public class PayActivity extends BaseCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CODE = 100;
    private EditText res_userid_text;
    private EditText res_money_text;
    private Button nextBtn;
    private String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pay);

        // TODO 实际应用时，为了防止破解app盗取ak，sk（为您在百度的标识，有了ak，sk就能使用您的账户），
        // TODO 建议把ak，sk放在服务端，移动端把相关参数传给您出服务端去调用百度人脸注册和比对服务，
        // TODO 然后再加上您的服务端返回的登录相关的返回参数给移动端进行相应的业务逻辑

        findView();
        addListener();
        initView();
        initEvent();
        nextBtn.setEnabled(false);
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
        final boolean[] f = new boolean[2];
        f[0]=f[1]=false;
        res_userid_text.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                BmobQuery<User> query = new BmobQuery<>();
                query.addWhereEqualTo("userid",res_userid_text.getText().toString().trim());
                query.findObjects(new FindListener<User>() {
                    @Override
                    public void done(final List<User> list, BmobException e) {
                        if (e == null){
                            if (list.size() > 0 ){
                                f[0] =true;
                                if(f[0]&&f[1]){
                                    nextBtn.setEnabled(true);
                                }
                            }else{
                                nextBtn.setEnabled(false);
                                XToast.warning(PayActivity.this,"该收款用户不存在").show();
                            }
                        }
                    }
                });
            }
        });
        res_money_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
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
                                    if(user.getMoney()>Double.parseDouble(res_money_text.getText().toString().trim())){
                                        f[1] =true;
                                        if(f[0]&&f[1]){
                                            nextBtn.setEnabled(true);
                                        }
                                    }else{
                                        nextBtn.setEnabled(false);
                                        XToast.warning(PayActivity.this,"余额不足").show();
                                    }
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    private void findView() {
        res_userid_text = (EditText) findViewById(R.id.res_userid_text);
        res_money_text=(EditText)findViewById(R.id.res_money_text);
        res_money_text.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        nextBtn = (Button) findViewById(R.id.next_btn);

    }

    private void addListener() {
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (nextBtn == v) {
            String res_username = res_userid_text.getText().toString().trim();
            Double res_money = Double.parseDouble(res_money_text.getText().toString().trim());
            // uid应使用你系统的用户id，示例里暂时用用户名
            String uid = UserManager.get().getUser().getUserid();
            if (TextUtils.isEmpty(uid)) {
                Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(PayActivity.this, FaceDetectActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // filePath = data.getStringExtra("file_path");
            filePath = ImageSaveUtil.loadCameraBitmapPath(this, "head_tmp.jpg");
            faceLogin(filePath);
        }
    }

    /**
     * 上传图片进行比对，分数达到80认为是同一个人，认为登录可以通过
     * 建议上传自己的服务器，在服务器端调用https://aip.baidubce.com/rest/2.0/face/v3/search，比对分数阀值（如：80分），认为登录通过
     * 返回登录认证的参数给客户端
     *
     * @param filePath
     */
    private void faceLogin(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(this, "人脸图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String username = sharedPreferences.getString("userid","");

        // uid应使用你系统的用户id，示例里暂时用用户名
        String uid = username;
        //Md5.MD5(username, "utf-8");

        final File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(this, "人脸图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        APIService.getInstance().verify(new OnResultListener<RegResult>() {
            @Override
            public void onResult(RegResult result) {
                // deleteFace(file);
                if (result == null) {
                    return;
                }

                displayData(result);
                // showResultIv();
            }


            @Override
            public void onError(FaceError error) {
                error.printStackTrace();
                //  deleteFace(file);
                // showResultIv();
            }
        }, file, uid);
    }

    private void deleteFace(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }


    private void displayData(RegResult result) {

        String res = result.getJsonRes();
        Log.d("VerifyLoginActivity", "res is:" + res);
        double maxScore = 0;
        String userId = "";
        String userInfo = "";
        if (TextUtils.isEmpty(res)) {
            return;
        }

        JSONObject obj = null;
        try {
            obj = new JSONObject(res);
            JSONObject resObj = obj.optJSONObject("result");
            if (resObj != null) {
                JSONArray resArray = resObj.optJSONArray("user_list");
                int size = resArray.length();


                for (int i = 0; i < size; i++) {
                    JSONObject s = (JSONObject) resArray.get(i);
                    if (s != null) {
                        double score = s.getDouble("score");
                        if (score > maxScore) {
                            maxScore = score;
                        }

                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (maxScore < 80) {
            XToast.warning(PayActivity.this,"支付失败").show();
        } else {
            // 分数可以根据安全级别调整，
            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
            String userid = sharedPreferences.getString("userid","");
            Pay pay=new Pay();
            pay.payMoney(res_userid_text.getText().toString().trim(),userid,Double.parseDouble(res_money_text.getText().toString().trim()),new Pay.PayCallBack(){

                @Override
                public void done(User user) {
                    XToast.warning(PayActivity.this,"支付成功").show();
                    Intent intent = new Intent();
                    intent.setClass(PayActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void error(String msg, int errorCode) {
                    XToast.warning(PayActivity.this,"支付失败").show();
                }
            });
        }
    }


}
