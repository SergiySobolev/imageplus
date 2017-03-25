package com.sbk.imageplus.contrast;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


class ContrastImagePlusTest {

    @Test
    public void testContrast() throws IOException {
        ImagePlus contrastImagePlus = new ContrastImagePlus(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("inputImage.jpg")
                )
        );
        contrastImagePlus.writeToFile("outputContrastImage.jpg");
        assertEquals(new PixelPlus(159,110,150,255), contrastImagePlus.getPixel(0,0));
        assertEquals(new PixelPlus(156,104,146,255), contrastImagePlus.getPixel(1,1));
        assertEquals(new PixelPlus(2,0,1,255), contrastImagePlus.getPixel(100,100));
        assertEquals(new PixelPlus(126,185,131,255), contrastImagePlus.getPixel(200,200));
        assertEquals(new PixelPlus(52,67,47,255), contrastImagePlus.getPixel(300,300));
    }

    @Test
    public void testContrastGray() throws IOException {
        ImagePlus contrastImagePlus = new ContrastImagePlus(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("inputGrayScaleImage.jpg")
                )
        );
        ImagePlus contrastGrayImagePlus = new ContrastGrayScaleImagePlus(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("inputGrayScaleImage.jpg")
                )
        );
        assertTrue(contrastImagePlus.equals(contrastGrayImagePlus));
    }

}