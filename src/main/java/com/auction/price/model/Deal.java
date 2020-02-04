package com.auction.price.model;


public class Deal {

    int buyedCount;
    final double price;

    public Deal(int buyedCount, double price) {
        this.buyedCount = buyedCount;
        this.price = price;
    }

    public int getBuyedCount() {
        return buyedCount;
    }
}
