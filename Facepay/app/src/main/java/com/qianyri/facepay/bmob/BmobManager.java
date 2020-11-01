package com.qianyri.facepay.bmob;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobManager {
    private static BmobManager manager;
    private BmobManager(){}
    public static BmobManager get(){
        if (manager == null) {
            synchronized (BmobManager.class) {
                if (manager == null) {
                    manager = new BmobManager();
                }
            }
        }
        return manager;
    }

    public void sendCode(String userid, QueryListener<Integer> listener){
        BmobSMS.requestSMSCode(userid,"",listener);
    }

    public void checkCode(String userid, String code, UpdateListener listener){
        BmobSMS.verifySmsCode(userid,code,listener);
    }
}
