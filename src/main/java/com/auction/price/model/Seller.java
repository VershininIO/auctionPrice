package com.auction.price.model;

import java.io.ObjectStreamException;
import java.util.Map;

public class Seller implements Comparable {

    static int indexCounter = 0;
    int elementIndex = 0;

    int needToSelCount;
    double price;

    public Seller(int needToSelCount, double price) {
        this.needToSelCount = needToSelCount;
        this.price = price;
        indexCounter++;
        elementIndex = indexCounter;
    }

    @Override
    public int compareTo(Object o) {
        Seller compSeller = (Seller) o;

        if (this == compSeller) {
            return 0;
        }

        // from big price to low price
        int compareByPrice = Double.compare(compSeller.price, price);

        if (compareByPrice != 0) {
            return compareByPrice;
        }

        return Integer.compare(elementIndex, compSeller.elementIndex);
    }

    public int getNeedToSelCount() {
        return needToSelCount;

    } public int getNeedToSelCount(Map<Seller, Integer> soldCountMap) {
        int soldCount = soldCountMap.get(this) == null ? 0 : soldCountMap.get(this);
        return needToSelCount - soldCount;
    }

    public double getPrice() {
        return price;
    }

    public void setNeedToSelCount(int needToSelCount) {
        this.needToSelCount = needToSelCount;
    }
}
