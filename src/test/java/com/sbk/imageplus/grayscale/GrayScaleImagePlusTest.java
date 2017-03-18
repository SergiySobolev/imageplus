package com.sbk.imageplus.grayscale;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Functionality of GrayScaleImagePlus")
public class GrayScaleImagePlusTest {

    private GrayScaleImagePlus grayScaleImagePlus;

    @BeforeEach
    public void initializeImage() throws IOException {
        grayScaleImagePlus = new GrayScaleImagePlus(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("inputImage.jpg")
                )
        );
    }

    @Test
    public void saveTest() throws IOException {
        grayScaleImagePlus.writeToFile("inputGrayScaleImage.jpg");
    }

    @TestFactory
    @DisplayName("GrayScale image rgb components should have same intensity")
    public Collection<DynamicTest> grayScaleImagesRGBComponentsShouldHaveSameIntensity() {
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for(int i=0; i<100; i++){
            for(int j=0; j<200; j++) {
                PixelPlus p  = grayScaleImagePlus.getPixel(i,j);
                Executable exec = () -> assertAll("All intensities are equal", () -> {
                    assertEquals(p.red, p.green);
                    assertEquals(p.green, p.blue);
                    assertEquals(p.red, p.blue);
                });
                String testName = new StringJoiner(",", "Check rgb intensities on pixel [", "]")
                        .add(""+i).add(""+j).toString();
                DynamicTest dTest = DynamicTest.dynamicTest(testName, exec);
                dynamicTests.add(dTest);
            }
        }
        return dynamicTests;
    }


}