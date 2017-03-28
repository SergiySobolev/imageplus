/*
** Copyright 2005 Huxtable.com. All rights reserved.
*/

package com.sbk.imageplus.convolve;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;

import java.io.IOException;

public class ConvolveFilteredImagePlus implements ImagePlus {

	private ImagePlus convolvedImage;
	private ConvolveFilter convolveFilter;

	public ConvolveFilteredImagePlus(double[][] filterMatrix, ImagePlus image) {
		this.convolveFilter = new ConvolveFilter(filterMatrix);
		this.convolvedImage = convolve(image);
	}

    private ImagePlus convolve(ImagePlus image) {
        int[][] output2d = convolution2D(image.getRGBDataElements2D(), convolveFilter);
        return new DefaultImagePlus(output2d);
    }

    private int singlePixelConvolution(int[][] input, int x, int y, ConvolveFilter filter) {
        int width = input.length;
        int height = input[0].length;
        int output = 0;
        for(int i = 0; i<width; i++) {
            for(int j = 0; j<height; j++) {
                if(!filter.inRangeWithShift(x, i, y, j)) continue;
                int fx = x - i;
                int fy = y - j;
                output += input[i][j] * filter.get(fx,fy);
            }
        }
		return new PixelPlus(output).toRgb();
	}

    private int[][] convolution2D(int[][] input, ConvolveFilter filter){
        int width = input.length;
        int height = input[0].length;
        int[][] output = new int[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                output[i][j] = singlePixelConvolution(input, i, j, filter);
            }
        }
        return output;
    }

	@Override
	public PixelPlus getPixel(int x, int y) {
		return convolvedImage.getPixel(x,y);
	}

	@Override
	public int width() {
		return convolvedImage.width();
	}

	@Override
	public int height() {
		return convolvedImage.height();
	}

	@Override
	public int type() {
		return convolvedImage.type();
	}

	@Override
	public boolean isAlphaPremultiplied() {
		return convolvedImage.isAlphaPremultiplied();
	}

    @Override
    public int[] getRGBDataElements() {
        return convolvedImage.getRGBDataElements();
    }

    @Override
    public int[][] getRGBDataElements2D() {
        return convolvedImage.getRGBDataElements2D();
    }

    @Override
	public void writeToFile(String filePath) throws IOException {
		convolvedImage.writeToFile(filePath);
	}
}
