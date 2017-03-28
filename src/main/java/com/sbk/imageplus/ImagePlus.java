package com.sbk.imageplus;

import com.sbk.imageplus.pixel.PixelPlus;

import java.io.IOException;

public interface ImagePlus {
    PixelPlus getPixel(int x, int y);
    int width();
    int height();
    int type();
    boolean isAlphaPremultiplied();
    int[] getRGBDataElements();
    int[][] getRGBDataElements2D();
    void writeToFile(String filePath) throws IOException;
}
