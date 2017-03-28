package com.sbk.imageplus.pixel;


import java.awt.*;
import java.util.Objects;

public class PixelPlus {

    public int red;
    public int green;
    public int blue;
    public int alpha;

    public PixelPlus(Color c) {
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();
        alpha = c.getAlpha();
    }

    public PixelPlus(int intensity) {
        this(intensity ,intensity, intensity, 255);
    }

    public PixelPlus(int red, int green, int blue) {
        this(red ,green, blue, 255);
    }

    public PixelPlus(int red, int green, int blue, int alpha) {
        this.red = red < 0 ? 0 : (red > 255 ? 255 : red);
        this.green = green < 0 ? 0 : (green > 255 ? 255 : green);;
        this.blue = blue < 0 ? 0 : (blue > 255 ? 255 : blue);
        this.alpha = alpha;
    }

    public int grayScaleIntensity() {
        int grayIntensity = intensity();
        int grayScaleColorValue = ((255 & 0xFF) << 24) |
                                ((grayIntensity & 0xFF) << 16) |
                                ((grayIntensity & 0xFF) << 8) |
                                ((grayIntensity & 0xFF) << 0);
        return grayScaleColorValue;
    }

    public int intensity() {
        return (int) (0.2989 * red + 0.5870 * green + 0.1140 * blue);
    }

    public PixelPlus makeBrighter(int brightness) {
        return new PixelPlus(red + brightness > 255 ? 255 : red + brightness,
                      green + brightness > 255 ? 255 : green + brightness,
                      blue + brightness > 255 ? 255 : blue + brightness,
                      alpha
                );
    }

    public int toRgb() {
        int newPixel = 0;
        newPixel += alpha; newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red,green,blue,alpha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PixelPlus pixelPlus = (PixelPlus) o;

        return red == pixelPlus.red
                && green == pixelPlus.green
                && blue == pixelPlus.blue
                && alpha == pixelPlus.alpha;
    }

    @Override
    public String toString() {
        return "PixelPlus{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", alpha=" + alpha +
                '}';
    }
}
