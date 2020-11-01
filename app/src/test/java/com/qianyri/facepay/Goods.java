package com.qianyri.facepay;

import java.io.Serializable;

public class Goods implements Serializable {
    public Goods(String kind, double price, int number) {
        this.kind = kind;
        this.price = price;
        this.number = number;
    }
    public Goods(){}

    public String getKind() {
        return kind;
    }

    public Goods setKind(String kind) {
        this.kind = kind;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Goods setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Goods setNumber(int number) {
        this.number = number;
        return this;
    }

    private String kind;
    private double price;
    private int number;
}