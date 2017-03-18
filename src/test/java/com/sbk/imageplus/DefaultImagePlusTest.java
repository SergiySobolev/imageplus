package com.sbk.imageplus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@DisplayName("Base functionality of ImagePlus")
class DefaultImagePlusTest {

    private ImagePlus imagePlus;

    @BeforeEach
    public void initializeImage() throws IOException {
         imagePlus = new DefaultImagePlus(getClass().getClassLoader().getResource("inputImage.jpg"))  ;
    }

    @Test
    @Tag("base functionality")
    @DisplayName("Raster shouldn't be null")
    void rasterShouldntBeNull() {
        assertNotNull(imagePlus);
    }



}