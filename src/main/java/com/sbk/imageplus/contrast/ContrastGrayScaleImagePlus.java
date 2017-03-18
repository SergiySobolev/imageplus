package com.sbk.imageplus.contrast;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;

import java.awt.*;
import java.io.IOException;


public class ContrastGrayScaleImagePlus implements ImagePlus {

    private ImagePlus contrastGrayImage;

    ContrastGrayScaleImagePlus(ImagePlus imagePlus) {
        this.contrastGrayImage = makeContrast(imagePlus);
    }

    private ImagePlus makeContrast(ImagePlus img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int pixelCount = width * height;
        int[] intensityHistogram = new int[255];

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                PixelPlus pixel = img.getPixel(i,j);
                int intensity = pixel.intensity();
                intensityHistogram[intensity] += 1;
            }
        }

        int sum = 0;
        int[] cumulativeDistributionFunction = new int[255];

        for(int i = 0; i < 255; ++i) {
            sum += intensityHistogram[i];
            cumulativeDistributionFunction[i] = sum * 255 / pixelCount;
        }

        int[] matrix = new int[width * height];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                PixelPlus pixel = img.getPixel(j,i);
                int intensityBefore = pixel.intensity();
                int intensityAfter = cumulativeDistributionFunction[intensityBefore];
                matrix[j + i*width] = new Color(intensityAfter, intensityAfter, intensityAfter).getRGB();
            }
        }
        return new DefaultImagePlus(matrix, width, height);
    }

    @Override
    public PixelPlus getPixel(int x, int y) {
        return contrastGrayImage.getPixel(x,y);
    }

    @Override
    public int getWidth() {
        return contrastGrayImage.getWidth();
    }

    @Override
    public int getHeight() {
        return contrastGrayImage.getHeight();
    }

    @Override
    public int type() {
        return contrastGrayImage.type();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        contrastGrayImage.writeToFile(filePath);
    }

    @Override
    public boolean equals(Object o) {
        return contrastGrayImage.equals(o);
    }

    @Override
    public int hashCode() {
        return contrastGrayImage != null ? contrastGrayImage.hashCode() : 0;
    }

}
