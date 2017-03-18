package com.sbk.imageplus.histogram;

import com.sbk.imageplus.DefaultImagePlus;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class RedGreedBlueHistogramTest {

    @Test
    public void t() throws IOException, InterruptedException {
        RedGreedBlueHistogram h = new RedGreedBlueHistogram(
                new DefaultImagePlus(
                        getClass().getClassLoader().getResource("inputImage.jpg")
                )
        );
    }

}