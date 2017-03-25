package com.sbk.imageplus.filters;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.blur.ConvolveFilteredImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


class ConvolveFilteredImagePlusTest {

    @Test
    public void filterWithOneNinthKernel() throws IOException {
        ConvolveFilteredImagePlus image = new ConvolveFilteredImagePlus(new float[]{0.111f,0.111f,0.111f,0.111f,0.111f,0.111f,0.111f,0.111f,0.111f},
                new DefaultImagePlus( getClass().getClassLoader().getResource("inputImage.jpg")));
        image.writeToFile("outputOneNinthKernel.jpg");
        assertEquals(new PixelPlus(198,142,129,255), image.getPixel(0,0));
        assertEquals(new PixelPlus(194,139,125,255), image.getPixel(1,1));
        assertEquals(new PixelPlus(17,10,5,255), image.getPixel(100,100));
        assertEquals(new PixelPlus(154,187,112,255), image.getPixel(200,200));
        assertEquals(new PixelPlus(65,106,47,255), image.getPixel(300,300));
        assertEquals(new PixelPlus(160,193,116,255), image.getPixel(300,200));
        assertEquals(new PixelPlus(65,99,49,255), image.getPixel(200,300));
        assertEquals(new PixelPlus(45,80,37,255), image.getPixel(100,300));
        assertEquals(new PixelPlus(222,186,118,255), image.getPixel(300,100));
    }

    @Test
    public void edgeDetection() throws IOException {
        ConvolveFilteredImagePlus image = new ConvolveFilteredImagePlus(new float[]{-1,-1,-1,-1,8,-1,-1,-1,-1},
                new DefaultImagePlus( getClass().getClassLoader().getResource("inputImage.jpg")));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(136,141));
        assertEquals(new PixelPlus(33,7,21,255), image.getPixel(137,141));
        assertEquals(new PixelPlus(7,31,38,255), image.getPixel(138,141));
        assertEquals(new PixelPlus(3,0,1,255), image.getPixel(139,141));
        assertEquals(new PixelPlus(3,0,0,255), image.getPixel(140,141));
        assertEquals(new PixelPlus(3,30,19,255), image.getPixel(141,141));
        assertEquals(new PixelPlus(11,57,58,255), image.getPixel(138,142));
        assertEquals(new PixelPlus(22,98,119,255), image.getPixel(138,143));

    }
}