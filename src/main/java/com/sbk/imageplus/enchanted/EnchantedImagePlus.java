package com.sbk.imageplus.enchanted;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;


public class EnchantedImagePlus implements ImagePlus {

    private final ImagePlus enchantedImage;
    private final int brightness;

    EnchantedImagePlus(ImagePlus imagePlus, int brightness) throws IOException {
        this.brightness  = brightness;
        this.enchantedImage = makeEnchanted(imagePlus);
    }

    private ImagePlus makeEnchanted(ImagePlus imagePlus) throws IOException {
        int width = imagePlus.getWidth();
        int height = imagePlus.getHeight();
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
    public int getWidth() {
        return enchantedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return enchantedImage.getHeight();
    }

    @Override
    public int type() {
        return enchantedImage.type();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        enchantedImage.writeToFile(filePath);
    }

}
