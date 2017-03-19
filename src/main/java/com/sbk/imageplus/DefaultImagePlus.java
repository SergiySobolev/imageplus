package com.sbk.imageplus;

import com.sbk.imageplus.pixel.PixelPlus;
import com.sbk.imageplus.raster.DefaultRasterPlus;
import com.sbk.imageplus.raster.RasterPlus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
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

    @Override
    public PixelPlus getPixel(int x, int y) {
        return new PixelPlus(new Color(image.getRGB (x, y)));
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public int type() {
        return image.getType();
    }

    @Override
    public void writeToFile(String filePath) throws IOException {
        ImageIO.write(image, "jpg", new File(filePath));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImagePlus)) return false;
        ImagePlus that = (ImagePlus) o;
        boolean ret = (that.getWidth() == getWidth()) && (that.getHeight() == getHeight());
        for(int i=0; i<that.getWidth(); i++){
            for(int j=0; j<that.getHeight(); j++){
                ret = ret && getPixel(i,j).equals(that.getPixel(i,j));
            }
        }
        return ret;
    }

    @Override
    public int hashCode() {
        return image.hashCode();
    }
}
