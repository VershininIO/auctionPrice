package com.auction.price.model;



public class Buyer implements Comparable {

    int needToBuyCount;
    final double price;

    public Buyer(int needToBuyCount, double price) {
        this.needToBuyCount = needToBuyCount;
        this.price = price;
    }

    @Override
    public int compareTo(Object o) {
        Buyer buyer = (Buyer) o;
        return Double.compare(this.price, buyer.price);
    }

    public int getNeedToBuyCount() {
        return needToBuyCount;
    }

    public double getPrice() {
        return price;
    }

    public void setNeedToBuyCount(int needToBuyCount) {
        this.needToBuyCount = needToBuyCount;
    }
}
