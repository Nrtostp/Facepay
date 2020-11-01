package com.qianyri.facepay.bmob;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {
    private String userid;
    private String nickname;
    private String photo;
    private String pwd;
    private Double money;
    private Double lastmoney;
    private boolean flag;

    public boolean getFlag() {
        return flag;
    }

    public User setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }


    public String getUserid() {
        return userid;
    }

    public User setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public User setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public User setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public Double getMoney() {
        BigDecimal b   =   new   BigDecimal(money);
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    public User setMoney(Double money) {
        this.money = money;
        return this;
    }

    public Double getLastmoney() {
        return lastmoney;
    }

    public User setLastmoney(Double lastmoney) {
        this.lastmoney = lastmoney;
        return this;
    }

    public static class Builder{
        private String userid;
        private String nickname;
        private String photo;
        private String pwd;
        private Double money;
        private Double lastmoney;

        public boolean isFlag() {
            return flag;
        }

        public Builder setFlag(boolean flag) {
            this.flag = flag;
            return this;
        }

        private boolean flag;

        public String getUserid() {
            return userid;
        }

        public Builder setUserid(String userid) {
            this.userid = userid;
            return this;
        }

        public String getNickname() {
            return nickname;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public String getPhoto() {
            return photo;
        }

        public Builder setPhoto(String photo) {
            this.photo = photo;
            return this;
        }

        public String getPwd() {
            return pwd;
        }

        public Builder setPwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public Double getMoney() {
            return money;
        }

        public Builder setMoney(Double money) {
            this.money = money;
            return this;
        }

        public Double getLastmoney() {
            return lastmoney;
        }

        public Builder setLastmoney(Double lastmoney) {
            this.lastmoney = lastmoney;
            return this;
        }

        public User build(){
            User user = new User();
            user.setUserid(userid);
            user.setNickname(nickname);
            user.setPhoto(photo);
            user.setPwd(pwd);
            user.setMoney(money);
            user.setLastmoney(lastmoney);
            user.setFlag(flag);
            return user;
        }
    }
}
