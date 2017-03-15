package com.sbk.imageplus.grayscale;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;

import java.awt.image.Raster;


public class GrayScaleImagePlus {
    private ImagePlus imagePlus;
    public GrayScaleImagePlus(ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
    }

    public ImagePlus grayScale() {
        Raster raster = imagePlus.raster();
        int width = raster.getWidth();
        int height = raster.getHeight();
        int[] matrix = new int[width * height];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                int[] buffer = new int[3];
                int[] colorBuffer = raster.getPixel(j,i, buffer);
                matrix[j + i*width] = new GrayScaleColor(colorBuffer).grayScaleColorValue();
            }
        }
        return new DefaultImagePlus(matrix, width, height);
    }

}
