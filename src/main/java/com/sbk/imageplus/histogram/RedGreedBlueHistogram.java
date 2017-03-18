package com.sbk.imageplus.histogram;

import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;

import java.awt.image.BufferedImage;

import static com.sbk.imageplus.Constants.INTENSITY_RANGE;

public class RedGreedBlueHistogram {
    private final ImagePlus imagePlus;
    private Histogram redHistogram;
    private Histogram greenHistogram;
    private Histogram blueHistogram;

    public RedGreedBlueHistogram(ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
        initRasterHistograms();
    }

    public PixelPlus getEqualizedPixel(PixelPlus p) {
        int newAlpha = p.alpha;
        int newRed = redHistogram.get(p.red);
        int newGreen = greenHistogram.get(p.green);
        int newBlue = blueHistogram.get(p.blue);
        return new PixelPlus(newRed, newGreen, newBlue, newAlpha);
    }

    private void initRasterHistograms() {
        int[] rHistogramArr = new int[INTENSITY_RANGE];
        int[] gHistogramArr = new int[INTENSITY_RANGE];
        int[] bHistogramArr = new int[INTENSITY_RANGE];

        for(int i=0; i<imagePlus.getWidth(); i++) {
            for(int j=0; j<imagePlus.getHeight(); j++) {
                PixelPlus pixel = imagePlus.getPixel(i,j);

                rHistogramArr[pixel.red]++;
                gHistogramArr[pixel.green]++;
                bHistogramArr[pixel.blue]++;
            }
        }
        redHistogram = new DefaultHistogram(rHistogramArr);
        greenHistogram = new DefaultHistogram(gHistogramArr);
        blueHistogram = new DefaultHistogram(bHistogramArr);
    }

    public RedGreedBlueHistogram buildCumulativeDistributionHistogram() {

        RedGreedBlueHistogram cumulativeDistributionHistogram = new RedGreedBlueHistogram(imagePlus);

        int[] cumDistRedHistogramArr = new int[INTENSITY_RANGE];
        int[] cumDistGreenHistogramArr = new int[INTENSITY_RANGE];
        int[] cumDistBlueHistogramArr = new int[INTENSITY_RANGE];

        long sumR = 0;
        long sumG = 0;
        long sumB = 0;

        float scale_factor = ((float)(INTENSITY_RANGE - 1) / (imagePlus.getWidth() * imagePlus.getHeight()));

        for(int i=0; i<INTENSITY_RANGE; i++) {
            sumR += redHistogram.get(i);
            cumDistRedHistogramArr[i] = (int) (sumR * scale_factor) > 255 ? 255 : (int) (sumR * scale_factor);

            sumG += greenHistogram.get(i);
            cumDistGreenHistogramArr[i] = (int) (sumG * scale_factor) > 255 ? 255 : (int) (sumG * scale_factor);

            sumB += blueHistogram.get(i);
            cumDistBlueHistogramArr[i] = (int) (sumB * scale_factor) > 255 ? 255 : (int) (sumB * scale_factor);
        }

        cumulativeDistributionHistogram.redHistogram = new DefaultHistogram(cumDistRedHistogramArr);
        cumulativeDistributionHistogram.greenHistogram = new DefaultHistogram(cumDistGreenHistogramArr);
        cumulativeDistributionHistogram.blueHistogram = new DefaultHistogram(cumDistBlueHistogramArr);
        return cumulativeDistributionHistogram;

    }

}
