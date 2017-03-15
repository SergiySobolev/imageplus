package com.sbk.imageplus.grayscale;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.grayscale.GrayScaleImagePlus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.awt.image.Raster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Functionality of GrayScaleImagePlus")
public class GrayScaleImagePlusTest {

    private GrayScaleImagePlus grayScaleImagePlus;

    @BeforeEach
    public void initializeImage() throws IOException {
        grayScaleImagePlus = new GrayScaleImagePlus(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("image1.jpg")
                )
        );
    }

    @TestFactory
    @DisplayName("Grayscale image rgb components should have same intensity")
    public Collection<DynamicTest> grayScaleImagesRGBComponentsShouldHaveSameIntensity() {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        ImagePlus imagePlus = grayScaleImagePlus.grayScale();
        assertNotNull(imagePlus);
        Raster raster = imagePlus.raster();
        for(int i=0; i<100; i++){
            for(int j=0; j<200; j++) {
                int[] pixelIntensity = raster.getPixel(100,100, new int[3]);
                Executable exec = () -> assertAll("All intensities are equal", () -> {
                    assertEquals(pixelIntensity[0], pixelIntensity[1]);
                    assertEquals(pixelIntensity[1], pixelIntensity[2]);
                    assertEquals(pixelIntensity[0], pixelIntensity[2]);
                });
                String testName = "Check rgb intensities on pixel [ " + i + "," + j + "]";
                DynamicTest dTest = DynamicTest.dynamicTest(testName, exec);
                dynamicTests.add(dTest);
            }
        }
        return dynamicTests;
    }


}