package com.sbk.imageplus.grayscale;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;

import java.io.IOException;


public class GrayScaleImagePlus implements ImagePlus {

    private final ImagePlus grayScaleImage;

    GrayScaleImagePlus(ImagePlus imagePlus) {
        this.grayScaleImage = makeGrayScale(imagePlus);
    }

    private ImagePlus makeGrayScale(ImagePlus imagePlus) {
        int width = imagePlus.width();
        int height = imagePlus.height();
        int[] matrix = new int[width * height];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                PixelPlus pixel = imagePlus.getPixel(j, i);
                matrix[j + i*width] = pixel.grayScaleIntensity();
            }
        }
        return new DefaultImagePlus(matrix, width, height);
    }

    @Override
    public PixelPlus getPixel(int x, int y) {
        return grayScaleImage.getPixel(x,y);
    }

    @Override
    public int width() {
        return grayScaleImage.width();
    }

    @Override
    public int height() {
        return grayScaleImage.height();
    }

    @Override
    public int type() {
        return grayScaleImage.type();
    }

    @Override
    public boolean isAlphaPremultiplied() {
        return grayScaleImage.isAlphaPremultiplied();
    }

    @Override
    public int[] getRGBDataElements() {
        return grayScaleImage.getRGBDataElements();
    }

    @Override
    public int[][] getRGBDataElements2D() {
        return grayScaleImage.getRGBDataElements2D();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        grayScaleImage.writeToFile(filePath);
    }

}
