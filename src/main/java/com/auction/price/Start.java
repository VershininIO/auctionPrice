package com.auction.price;

import com.auction.price.model.Buyer;
import com.auction.price.model.Deal;
import com.auction.price.model.Seller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Start {
    public static void main(String[] args) throws IOException {

        String path = Start.class.getClassLoader().getResource("case1").getPath();

        Processor processor = new Processor();

        int rowCount = processor.countLines(path);

        if (rowCount > 500_000L) {
            throw new RuntimeException("Illegal row count for input file \"" + rowCount + "\"");
        }

        Set<Buyer> buyerList = new HashSet<>();
        TreeSet<Seller> sellerList = new TreeSet<>();

        processor.readFile(path, buyerList, sellerList);

        Optional<Double> discreteAuctionPrice = buyerList.stream().map(Buyer::getPrice).distinct().max(
                (price1, price2) -> {
                    int countDeals1 = processor.countDeals(price1, buyerList, sellerList);
                    int countDeals2 = processor.countDeals(price2, buyerList, sellerList);

                    if (countDeals1 != countDeals2) {
                        return Integer.compare(countDeals1, countDeals2);
                    }

                    return Double.compare(price2, price1);
                }
        );


        List <Deal> deals = processor.makeDeals(buyerList, sellerList);

        if (deals.isEmpty() || !discreteAuctionPrice.isPresent()) {
            System.out.println("0 n/a");
            return;
        }

        int dealsCount = deals.stream().mapToInt(Deal::getBuyedCount).sum();
        System.out.println(dealsCount + " " + discreteAuctionPrice.get());
    }


}
