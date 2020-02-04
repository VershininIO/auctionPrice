package com.auction.price;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class PriceProcessorTest {

    @org.junit.Test
    public void calculate() {

        FileSystem fs = FileSystems.getDefault();
        Path case1 = fs.getPath("case1");

    }
}