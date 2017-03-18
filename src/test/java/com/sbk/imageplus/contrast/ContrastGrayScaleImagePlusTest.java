package com.sbk.imageplus.contrast;

import com.sbk.imageplus.DefaultImagePlus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@DisplayName("Functionality of ContrastGrayScaleImagePlus")
class ContrastGrayScaleImagePlusTest {
    private ContrastGrayScaleImagePlus contrastGrayScaleImagePlus;

    @BeforeEach
    public void initializeImage() throws IOException {
        contrastGrayScaleImagePlus = new ContrastGrayScaleImagePlus(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("inputGrayScaleImage.jpg")
                )
        );
    }

    @Test
    public void save() throws IOException {
        contrastGrayScaleImagePlus.writeToFile("grayScaleContrastImage.jpg");
    }
}