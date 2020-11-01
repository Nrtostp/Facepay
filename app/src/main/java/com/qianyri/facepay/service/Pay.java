package com.qianyri.facepay.service;

import com.qianyri.facepay.bmob.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Pay {
    private Pay.PayCallBack payCallBack;

    public void payMoney(String r_userid, String s_userid, final Double money, PayCallBack callback) {
        this.payCallBack=callback;
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("userid",r_userid);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> list, BmobException e) {
                if (e == null){
                    if (list.size() > 0 ){
                        User user = list.get(0);
                        user.setMoney(user.getMoney()+money);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e!=null){
                                    payCallBack.error(e.getMessage(),e.getErrorCode());
                                }
                            }
                        });
                    }else {
                        payCallBack.error(e.getMessage(),e.getErrorCode());
                    }
                } else {
                    payCallBack.error(e.getMessage(),e.getErrorCode());
                }
            }
        });
        BmobQuery<User> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("userid",s_userid);
        query2.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> list, BmobException e) {
                if (e == null){
                    if (list.size() > 0 ){
                        final User user = list.get(0);
                        user.setMoney(user.getMoney()-money);
                        UserManager.get().setUser(user);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e!=null){
                                    payCallBack.error(e.getMessage(),e.getErrorCode());
                                } else {
                                    payCallBack.done(user);
                                }
                            }
                        });
                    }else {
                        payCallBack.error(e.getMessage(),e.getErrorCode());
                    }
                } else {
                    payCallBack.error(e.getMessage(),e.getErrorCode());
                }
            }
        });
    }


    public interface PayCallBack{
        void done(User user);
        void error(String msg,int errorCode);
    }
}

