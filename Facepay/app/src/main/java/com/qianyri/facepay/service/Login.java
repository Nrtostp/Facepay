package com.qianyri.facepay.service;

import com.qianyri.facepay.bmob.BmobManager;
import com.qianyri.facepay.bmob.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Login {
    private LoginCallBack loginCallBack;
    private String userid;

    public void checkCode(String userid,String code,LoginCallBack callback){
        this.loginCallBack = callback;
        this.userid = userid;
        BmobManager.get().checkCode(userid, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    findUserByUserid();
                } else {
                    loginCallBack.error(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }
    private void findUserByUserid(){
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("userid",userid);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> list, BmobException e) {
                if (e == null){
                    if (list.size() > 0 ){
                        User user = list.get(0);
                        loginCallBack.done(user);
                    }else {
                        final User user = new User.Builder()
                                .setUserid(userid)
                                .setPhoto("")
                                .setMoney(0.0)
                                .setLastmoney(0.0)
                                .setNickname("nickname")
                                .setPwd("")
                                .build();
                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    loginCallBack.done(user);
                                } else {
                                    loginCallBack.error(e.getMessage(),e.getErrorCode());
                                }
                            }
                        });
                    }
                } else {
                    loginCallBack.error(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }

    public interface LoginCallBack{
        void done(User user);
        void error(String msg,int errorCode);
    }
}
