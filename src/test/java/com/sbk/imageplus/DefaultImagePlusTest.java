package com.sbk.imageplus;

import com.sbk.imageplus.grayscale.GrayScaleImagePlus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Base functionality of ImagePlus")
class DefaultImagePlusTest {

    private ImagePlus imagePlus;

    @BeforeEach
    public void initializeImage() throws IOException {
         imagePlus = new DefaultImagePlus(getClass().getClassLoader().getResource("image1.jpg"))  ;
    }

    @Test
    @Tag("base functionality")
    @DisplayName("Raster shouldn't be null")
    void rasterShouldntBeNull() {
        Raster raster = imagePlus.raster();
        assertNotNull(raster);
    }

}