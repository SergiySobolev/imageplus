package com.sbk.imageplus;

import com.sbk.imageplus.pixel.PixelPlus;

import java.io.IOException;

public interface ImagePlus {
    PixelPlus getPixel(int x, int y);
    int getWidth();
    int getHeight();
    int type();
    void writeToFile(String filePath) throws IOException;
}
