/*
** Copyright 2005 Huxtable.com. All rights reserved.
*/

package com.sbk.imageplus.blur;

import com.sbk.imageplus.DefaultImagePlus;
import com.sbk.imageplus.ImagePlus;
import com.sbk.imageplus.pixel.PixelPlus;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.io.IOException;

public class ConvolveFilteredImagePlus implements ImagePlus {
	
	private static int CLAMP_EDGES = 1;
	private static int WRAP_EDGES = 2;

	private final Kernel kernel;
	private int edgeAction = CLAMP_EDGES;
	private final ImagePlus convolvedImage;

	public ConvolveFilteredImagePlus(float[] matrix, ImagePlus image) {
		this(new Kernel(3, 3, matrix), image);
	}

	private ConvolveFilteredImagePlus(Kernel kernel, ImagePlus image) {
		this.kernel = kernel;
		this.convolvedImage = convolve(image);
	}

    private ImagePlus convolve(ImagePlus image) {
        int width = image.width();
        int height = image.height();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );
        int[] inPixels = image.getRGBDataElements();
		boolean alpha = true;
        int[] outPixels = convolve(kernel, inPixels, width, height, alpha, edgeAction);
        bi.getRaster().setDataElements( 0, 0, width, height, outPixels );
        return new DefaultImagePlus(bi);
    }

	private  int[] convolve(Kernel kernel, int[] inPixels, int width, int height, boolean alpha, int edgeAction) {
		int[] outPixels;
		if (kernel.getHeight() == 1)
            outPixels = convolveH(kernel, inPixels, width, height, alpha, edgeAction);
		else if (kernel.getWidth() == 1)
            outPixels = convolveV(kernel, inPixels, width, height, alpha, edgeAction);
		else
            outPixels = convolveHV(kernel, inPixels,  width, height, alpha, edgeAction);
		return outPixels;
	}
	
	/**
	 * Convolve with a 2D kernel
	 */
	private int[] convolveHV(Kernel kernel, int[] inPixels, int width, int height, boolean alpha, int edgeAction) {
        int[] outPixels = new int[inPixels.length];
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int rows = kernel.getHeight();
		int cols = kernel.getWidth();
		int rows2 = rows/2;
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++) {
					int iy = y+row;
					int ioffset;
					if (0 <= iy && iy < height)
						ioffset = iy*width;
					else if ( edgeAction == CLAMP_EDGES )
						ioffset = y*width;
					else if ( edgeAction == WRAP_EDGES )
						ioffset = ((iy+height) % height) * width;
					else
						continue;
					int moffset = cols*(row+rows2)+cols2;
					for (int col = -cols2; col <= cols2; col++) {
						float f = matrix[moffset+col];

						if (f != 0) {
							int ix = x+col;
							if (!(0 <= ix && ix < width)) {
								if ( edgeAction == CLAMP_EDGES )
									ix = x;
								else if ( edgeAction == WRAP_EDGES )
									ix = (x+width) % width;
								else
									continue;
							}
							int rgb = inPixels[ioffset+ix];
							a += f * ((rgb >> 24) & 0xff);
							r += f * ((rgb >> 16) & 0xff);
							g += f * ((rgb >> 8) & 0xff);
							b += f * (rgb & 0xff);
						}
					}
				}
				int ia = alpha ? clamp((int)(a+0.5)) : 0xff;
				int ir = clamp((int)(r+0.5));
				int ig = clamp((int)(g+0.5));
				int ib = clamp((int)(b+0.5));
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
		return outPixels;
	}

	/**
	 * Convolve with a kernel consisting of one row
	 */
	private int[] convolveH(Kernel kernel, int[] inPixels, int width, int height, boolean alpha, int edgeAction) {
	    int[] outPixels = new int[inPixels.length];
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int cols = kernel.getWidth();
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			int ioffset = y*width;
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;
				int moffset = cols2;
				for (int col = -cols2; col <= cols2; col++) {
					float f = matrix[moffset+col];

					if (f != 0) {
						int ix = x+col;
						if ( ix < 0 ) {
							if ( edgeAction == CLAMP_EDGES )
								ix = 0;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						} else if ( ix >= width) {
							if ( edgeAction == CLAMP_EDGES )
								ix = width-1;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						}
						int rgb = inPixels[ioffset+ix];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				int ia = alpha ? clamp((int)(a+0.5)) : 0xff;
				int ir = clamp((int)(r+0.5));
				int ig = clamp((int)(g+0.5));
				int ib = clamp((int)(b+0.5));
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
		return outPixels;
	}

	/**
	 * Convolve with a kernel consisting of one column
	 */
	private int[] convolveV(Kernel kernel, int[] inPixels, int width, int height, boolean alpha, int edgeAction) {
        int[] outPixels = new int[inPixels.length];
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int rows = kernel.getHeight();
		int rows2 = rows/2;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++) {
					int iy = y+row;
					int ioffset;
					if ( iy < 0 ) {
						if ( edgeAction == CLAMP_EDGES )
							ioffset = 0;
						else if ( edgeAction == WRAP_EDGES )
							ioffset = ((y+height) % height)*width;
						else
							ioffset = iy*width;
					} else if ( iy >= height) {
						if ( edgeAction == CLAMP_EDGES )
							ioffset = (height-1)*width;
						else if ( edgeAction == WRAP_EDGES )
							ioffset = ((y+height) % height)*width;
						else
							ioffset = iy*width;
					} else
						ioffset = iy*width;

					float f = matrix[row+rows2];

					if (f != 0) {
						int rgb = inPixels[ioffset+x];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				int ia = alpha ? clamp((int)(a+0.5)) : 0xff;
				int ir = clamp((int)(r+0.5));
				int ig = clamp((int)(g+0.5));
				int ib = clamp((int)(b+0.5));
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
		return outPixels;
	}

    public int clamp(int c) {
        if (c < 0)
            return 0;
        if (c > 255)
            return 255;
        return c;
    }

	public String toString() {
		return "Blur/Convolve...";
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
	public void writeToFile(String filePath) throws IOException {
		convolvedImage.writeToFile(filePath);
	}
}
