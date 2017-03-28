package com.sbk.imageplus.filters;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.benchmark.Benchmarked;
import com.sbk.imageplus.convolve.ConvolveFilteredImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@Benchmarked
class ConvolveFilteredImagePlusTest {
    @Test
    public void filterWithOneNinthKernel() throws IOException {
        ConvolveFilteredImagePlus image = new ConvolveFilteredImagePlus(new double[][]{{0.111f,0.111f,0.111f},{0.111f,0.111f,0.111f},{0.111f,0.111f,0.111f}},
                new DefaultImagePlus( getClass().getClassLoader().getResource("inputImage.jpg")));
        assertEquals(new PixelPlus(17,17,17,255), image.getPixel(0,0));
        assertEquals(new PixelPlus(68,68,68,255), image.getPixel(1,1));
        assertEquals(new PixelPlus(15,15,15,255), image.getPixel(100,100));
        assertEquals(new PixelPlus(163,163,163,255), image.getPixel(200,200));
        assertEquals(new PixelPlus(80,80,80,255), image.getPixel(300,300));
        assertEquals(new PixelPlus(167,167,167,255), image.getPixel(300,200));
        assertEquals(new PixelPlus(79,79,79,255), image.getPixel(200,300));
        assertEquals(new PixelPlus(65,65,65,255), image.getPixel(100,300));
        assertEquals(new PixelPlus(193,193,193,255), image.getPixel(300,100));
    }

    @Test
    public void edgeDetection_1() throws IOException {
        ConvolveFilteredImagePlus image = new ConvolveFilteredImagePlus(new double[][]{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}},
                new DefaultImagePlus( getClass().getClassLoader().getResource("inputImage.jpg")));
        assertEquals(new PixelPlus(113,113,113,255), image.getPixel(136,141));
        assertEquals(new PixelPlus(22,22,22,255), image.getPixel(137,141));
        assertEquals(new PixelPlus(17,17,17,255), image.getPixel(138,141));
        assertEquals(new PixelPlus(14,14,14,255), image.getPixel(139,141));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(140,141));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(141,141));
        assertEquals(new PixelPlus(13,13,13,255), image.getPixel(138,142));
        assertEquals(new PixelPlus(27,27,27,255), image.getPixel(138,143));
    }

    @Test
    public void edgeDetection_2() throws IOException {
        ConvolveFilteredImagePlus image = new ConvolveFilteredImagePlus(new double[][]{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}},
                new DefaultImagePlus( getClass().getClassLoader().getResource("inputImage2.jpg")));
        image.writeToFile("edge.jpg");
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(100,100));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(90,90));
        assertEquals(new PixelPlus(2,2,2,255), image.getPixel(80,80));
        assertEquals(new PixelPlus(12,12,12,255), image.getPixel(70,70));
        assertEquals(new PixelPlus(44,44,44,255), image.getPixel(60,60));
        assertEquals(new PixelPlus(31,31,31,255), image.getPixel(61,61));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(50,50));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(40,40));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(30,30));
        assertEquals(new PixelPlus(0,0,0,255), image.getPixel(20,20));
        assertEquals(new PixelPlus(15,15,15,255), image.getPixel(10,10));

    }
}