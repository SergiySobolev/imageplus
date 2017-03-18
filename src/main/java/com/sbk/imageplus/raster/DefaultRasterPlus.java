package com.sbk.imageplus.raster;


import java.awt.image.Raster;

public class DefaultRasterPlus implements RasterPlus {
    private final Raster raster;
    public DefaultRasterPlus(Raster raster) {
        this.raster = raster;
    }
    @Override
    public int[] getPixel(int x, int y) {
        return raster.getPixel(x,y,new int[3]);
    }

    @Override
    public int[] getPixelWithAlpha(int x, int y) {
        int[] rgbArray = raster.getPixel(x,y, new int[4]);
        int alpha = ((raster.getPixels(x,y,3,3,( int[ ] ) null)[0]) >> 24) & 0xff;
        rgbArray[3] = alpha;
        return rgbArray;
    }

    @Override
    public int getWidth() {
        return raster.getWidth();
    }

    @Override
    public int getHeight() {
        return raster.getHeight();
    }
}
