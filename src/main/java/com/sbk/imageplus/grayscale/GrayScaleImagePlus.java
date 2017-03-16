package com.sbk.imageplus.grayscale;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;

import java.awt.image.Raster;
import java.io.IOException;


public class GrayScaleImagePlus implements ImagePlus {
    private final ImagePlus imagePlus;
    private ImagePlus grayScaleImagePlus;
    GrayScaleImagePlus(ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
    }

    @Override
    public Raster raster() {
        checkAndInitializeIfNullGrayScaleImage();
        return grayScaleImagePlus.raster();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        checkAndInitializeIfNullGrayScaleImage();
        grayScaleImagePlus.writeToFile(filePath);
    }

    private ImagePlus makeGrayScale(ImagePlus img) {
        Raster raster = img.raster();
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

    private void checkAndInitializeIfNullGrayScaleImage() {
        if(grayScaleImagePlus == null) {
            grayScaleImagePlus = makeGrayScale(imagePlus);
        }
    }
}
