package com.sbk.imageplus.raster;


public interface RasterPlus {
    int[] getPixel(int x, int y);
    int[] getPixelWithAlpha(int x, int y);
    int getWidth();
    int getHeight();
}
