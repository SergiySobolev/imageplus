package com.sbk.imageplus.histogram;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DefaultHistogramTest {
    @Test
    void draw() throws InterruptedException {
        DefaultHistogram h = new DefaultHistogram(new int[]{20,34,66,23,1,98,45,88});
    }

}