package com.qianyri.facepay.service;

import com.qianyri.facepay.bmob.User;

public class UserManager {

    private static UserManager userManager;
    private User user;
    private UserManager(){}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void delUser(User user){
        this.user = null;
    }


    public static UserManager get(){
        if (userManager == null) {
            synchronized (UserManager.class){
                if (userManager == null){
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }


}
