package com.sbk.imageplus.histogram;

import com.sbk.imageplus.pixel.PixelPlus;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import static com.sbk.imageplus.Constants.INTENSITY_RANGE;

public class DefaultHistogram implements Histogram {

    private final int[] histogramValues;

    DefaultHistogram() {
        histogramValues = new int[INTENSITY_RANGE];
    }

    DefaultHistogram(int[] histogramValues) {
        this.histogramValues = histogramValues;
    }

    @Override
    public void draw() {
        JFrame frame = new JFrame("Histogram");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(new Graph(histogramValues)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public int get(int i) {
        return histogramValues[i];
    }

    protected class Graph extends JPanel {

        static final int MIN_BAR_WIDTH = 4;
        private int[] histogramValues;

        Graph(int[] histogramValues) {
            this.histogramValues = histogramValues;
            int width = (histogramValues.length * MIN_BAR_WIDTH) + 11;
            Dimension minSize = new Dimension(width, 128);
            Dimension prefSize = new Dimension(width, 256);
            setMinimumSize(minSize);
            setPreferredSize(prefSize);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
                int xOffset = 5;
                int yOffset = 5;
                int width = getWidth() - 1 - (xOffset * 2);
                int height = getHeight() - 1 - (yOffset * 2);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(xOffset, yOffset, width, height);
                int barWidth = Math.max(MIN_BAR_WIDTH, (int) Math.floor(width / histogramValues.length));

                int maxValue = Arrays.stream(histogramValues).reduce(0, Integer::max);

                for (int value: histogramValues) {
                    maxValue = Math.max(maxValue, value);
                }
                int xPos = xOffset;
                for (int i=0; i<histogramValues.length; i++) {
                    int value = histogramValues[i];
                    int barHeight = Math.round(((float) value  /  maxValue) * height);
                    g2d.setColor(new Color(i, i, i));
                    int yPos = height + yOffset - barHeight;
                    Rectangle2D bar = new Rectangle2D.Float(xPos, yPos, barWidth, barHeight);
                    g2d.fill(bar);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.draw(bar);
                    xPos += barWidth;
                }
                g2d.dispose();
        }
    }
}
