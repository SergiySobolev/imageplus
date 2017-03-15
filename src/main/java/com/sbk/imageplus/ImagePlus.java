package com.sbk.imageplus;

import java.awt.image.Raster;
import java.io.IOException;

public interface ImagePlus {
    Raster raster();
    void writeToFile(String filePath) throws IOException;
}
