package com.sbk.imageplus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DefaultImagePlus implements ImagePlus {
    private BufferedImage image;
    public DefaultImagePlus(URL imageUrl) throws IOException {
        this(ImageIO.read(imageUrl));
    }
    private DefaultImagePlus(BufferedImage image) {
        this.image = image;
    }
    public DefaultImagePlus(int[] data, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );
        final int[] a = ((DataBufferInt) bi.getRaster().getDataBuffer()).getData();
        System.arraycopy(data, 0, a, 0, data.length);
        this.image = bi;
    }
    public Raster raster() {
        return image.getRaster();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        ImageIO.write(image, "jpg", new File(filePath));
    }
}
