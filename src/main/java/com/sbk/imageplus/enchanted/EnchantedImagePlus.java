package com.sbk.imageplus.enchanted;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;

import java.io.IOException;


public class EnchantedImagePlus implements ImagePlus {

    private final ImagePlus enchantedImage;
    private final int brightness;

    EnchantedImagePlus(ImagePlus imagePlus, int brightness) throws IOException {
        this.brightness  = brightness;
        this.enchantedImage = makeEnchanted(imagePlus);
    }

    private ImagePlus makeEnchanted(ImagePlus imagePlus) throws IOException {
        int width = imagePlus.width();
        int height = imagePlus.height();
        int[] matrix = new int[width * height];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                PixelPlus pixel = imagePlus.getPixel(j, i);
                matrix[j + i*width] = pixel.makeBrighter(brightness).toRgb();
            }
        }
        return new DefaultImagePlus(matrix, width, height);
    }

    @Override
    public PixelPlus getPixel(int x, int y) {
        return enchantedImage.getPixel(x,y);
    }

    @Override
    public int width() {
        return enchantedImage.width();
    }

    @Override
    public int height() {
        return enchantedImage.height();
    }

    @Override
    public int type() {
        return enchantedImage.type();
    }

    @Override
    public boolean isAlphaPremultiplied() {
        return enchantedImage.isAlphaPremultiplied();
    }

    @Override
    public int[] getRGBDataElements() {
        return enchantedImage.getRGBDataElements();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        enchantedImage.writeToFile(filePath);
    }

}
