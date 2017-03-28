package com.sbk.imageplus.contrast;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;
import com.sbk.imageplus.histogram.RedGreedBlueHistogram;

import java.io.IOException;

public class ContrastImagePlus implements ImagePlus {

    private final ImagePlus contrastImage;

    ContrastImagePlus(ImagePlus imagePlus) throws IOException {
        this.contrastImage = makeContrast(imagePlus);
    }

    private ImagePlus makeContrast(ImagePlus imagePlus) throws IOException {
        RedGreedBlueHistogram cumulativeDistributionHistogram = new RedGreedBlueHistogram(imagePlus).buildCumulativeDistributionHistogram();
        int width = imagePlus.width();
        int height = imagePlus.height();
        int[] matrix = new int[imagePlus.width() * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                PixelPlus p = imagePlus.getPixel(j,i);
                PixelPlus equalizedPixel = cumulativeDistributionHistogram.getEqualizedPixel(p);
                matrix[j + i * width] = equalizedPixel.toRgb();
            }
        }
        return new DefaultImagePlus(matrix, width, height);
    }

    @Override
    public PixelPlus getPixel(int x, int y) {
        return contrastImage.getPixel(x,y);
    }

    @Override
    public int width() {
        return contrastImage.width();
    }

    @Override
    public int height() {
        return contrastImage.height();
    }

    @Override
    public int type() {
        return contrastImage.type();
    }

    @Override
    public boolean isAlphaPremultiplied() {
        return contrastImage.isAlphaPremultiplied();
    }

    @Override
    public int[] getRGBDataElements() {
        return contrastImage.getRGBDataElements();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        contrastImage.writeToFile(filePath);
    }

    @Override
    public int[][] getRGBDataElements2D() {
        return contrastImage.getRGBDataElements2D();
    }

    @Override
    public boolean equals(Object o) {
       return contrastImage.equals(o);
    }

    @Override
    public int hashCode() {
        return contrastImage != null ? contrastImage.hashCode() : 0;
    }
}
