package com.auction.price;

import com.auction.price.model.Buyer;
import com.auction.price.model.Deal;
import com.auction.price.model.Seller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Processor {

    public void readFile(String path, Set<Buyer> buyerList, Set<Seller> sellerList) throws IOException {

        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {

            String[] lineArr = line.split(" ");

            if (lineArr.length != 3) {
                throw new RuntimeException("Illegal elements count in row \"" + line + "\"");
            }

            int paperCount = Integer.parseInt(lineArr[1]);

            if (paperCount < 1 || paperCount > 1000) {
                throw new RuntimeException("Illegal paperCount in row \"" + line + "\"");
            }

            double price = Double.valueOf(lineArr[2]);

            if (price < 1 || price > 200) {
                throw new RuntimeException("Illegal price in row \"" + line + "\"");
            }

            if (lineArr[0].equals("B")) {
                buyerList.add(new Buyer(
                        Integer.parseInt(lineArr[1]),
                        Double.valueOf(lineArr[2])
                ));
            } else if (lineArr[0].equals("S")) {
                sellerList.add(new Seller(
                        Integer.parseInt(lineArr[1]),
                        Double.valueOf(lineArr[2])
                ));
            } else {
                throw new RuntimeException("Illegal row type in row \"" + line + "\"");
            }
        }

    }

    public int countDeals(double price, Set<Buyer> buyerSet, TreeSet<Seller> sellerTreeSet) {

        int dealcount = 0;

        Map<Seller, Integer> soldCountMap = new HashMap<>();
        for (Buyer buyer : buyerSet) {
                int needToBuyCount = buyer.getNeedToBuyCount();

                for (Seller seller : sellerTreeSet) {
                    if (price <= seller.getPrice()) {
                        if(needToBuyCount <= seller.getNeedToSelCount(soldCountMap)){
                            dealcount += buyer.getNeedToBuyCount();
                            soldCountMap.put(seller, buyer.getNeedToBuyCount());
                        }
                    } else if (needToBuyCount > seller.getNeedToSelCount()) {
                        soldCountMap.put(seller, seller.getNeedToSelCount(soldCountMap));
                        dealcount += seller.getNeedToSelCount();
                        needToBuyCount = needToBuyCount - seller.getNeedToSelCount();
                    }
                }
        }

        return dealcount;
    }

    public List<Deal> makeDeals(Set<Buyer> buyerSet, TreeSet<Seller> sellerTreeSet) {

        List<Deal> deal = new ArrayList<>();

        for (Buyer buyer : buyerSet) {
            for (Seller seller : sellerTreeSet) {
                if (seller.getPrice() <= buyer.getPrice()) {
                    if (buyer.getNeedToBuyCount() <= seller.getNeedToSelCount()) {
                        int buyedCount = buyer.getNeedToBuyCount();
                        seller.setNeedToSelCount(seller.getNeedToSelCount() - buyedCount);
                        buyer.setNeedToBuyCount(0);
                        deal.add(new Deal(buyedCount, buyer.getPrice()));
                        break;
                    } else if (buyer.getNeedToBuyCount() > seller.getNeedToSelCount()) {
                        int buyedCount = seller.getNeedToSelCount();
                        buyer.setNeedToBuyCount(buyer.getNeedToBuyCount() - buyedCount);
                        seller.setNeedToSelCount(0);
                        deal.add(new Deal(buyedCount, buyer.getPrice()));
                    }
                }
            }
        }

        return deal;
    }




    // https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
    // A relatively quick way to count lines in a file
    // Doesn't count the last line. Not critical for this task.
    public int countLines(String filename) throws IOException {
        try(InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        }
    }

}
