package com.sbk.imageplus.grayscale;


class GrayScaleColor {
    private int[] colorBuffer;

    GrayScaleColor(int[] colorBuffer) {
        this.colorBuffer = colorBuffer;
    }

    int grayScaleColorValue() {
        int grayIntensity = (int) (0.2989 * colorBuffer[0] + 0.5870 * colorBuffer[1] + 0.1140 * colorBuffer[2]);
        int grayScaleColorValue = ((255 & 0xFF) << 24) |
                                ((grayIntensity & 0xFF) << 16) |
                                ((grayIntensity & 0xFF) << 8) |
                                ((grayIntensity & 0xFF) << 0);
        return grayScaleColorValue;
    }
}
