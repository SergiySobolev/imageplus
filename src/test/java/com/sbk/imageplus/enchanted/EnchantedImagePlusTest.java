package com.sbk.imageplus.enchanted;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.contrast.ContrastGrayScaleImagePlus;
import com.sbk.imageplus.contrast.ContrastImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class EnchantedImagePlusTest {

    @Test
    public void testEnchanted() throws IOException {
        ImagePlus enchantedImagePlus = new EnchantedImagePlus(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("inputImage.jpg")
                ), 50
        );
        assertEquals(new PixelPlus(249,193,180,255), enchantedImagePlus.getPixel(0,0));
        assertEquals(new PixelPlus(245,189,176,255), enchantedImagePlus.getPixel(1,1));
        assertEquals(new PixelPlus(65,58,52,255), enchantedImagePlus.getPixel(100,100));
        assertEquals(new PixelPlus(202,236,160,255), enchantedImagePlus.getPixel(200,200));
        assertEquals(new PixelPlus(115,156,96,255), enchantedImagePlus.getPixel(300,300));

    }

}