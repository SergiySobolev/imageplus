package com.sbk.imageplus.convolve;


class ConvolveFilter {
    private final double[][] filterMatrix;
    private final int height;
    private final int width;

    ConvolveFilter(double[][] filterMatrix) {
        assert filterMatrix != null;
        assert filterMatrix.length > 0;
        assert filterMatrix[0].length > 0;
        this.filterMatrix = filterMatrix;
        this.height = filterMatrix.length;
        this.width = filterMatrix[0].length;
    }

    double get(int i, int j){
        boolean outOfBounds = (i >= width) || (j >= height) || (i<0) || (j<0);
        return outOfBounds ? 0 : filterMatrix[i][j];
    }

    boolean inRangeWithShift(int xFrom, int xTo, int yFrom, int yTo) {
        return isInRangeWidth(xFrom, xTo) && isInRangeHeight(yFrom, yTo);
    }

    private boolean isInRangeWidth(int x, int x0) {
        return Math.abs(x - x0) < 3*width;
    }

    private boolean isInRangeHeight(int y, int y0) {
        return Math.abs(y - y0) < 3*height;
    }
}
