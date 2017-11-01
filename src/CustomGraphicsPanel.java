import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class CustomGraphicsPanel extends JPanel {

    int width, height;
    BufferedImage image;
    int[] pixels;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        int currentWidth = getWidth();
        int currentHeight = getHeight();
        if (currentWidth != width || currentHeight != height) {
            width = currentWidth;
            height = currentHeight;

            GraphicsConfiguration gc = GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getDefaultScreenDevice()
                    .getDefaultConfiguration();

            image = gc.createCompatibleImage(width, height, Transparency.OPAQUE);
            pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

            drawMandelbrotSet(pixels, width, height);
            //drawMandelbrotSetAlternate(pixels, width, height);
        }

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(image, 0, 0, width, height, null);

        repaint();
    }

    public void drawMandelbrotSetAlternate(int[] pixels, int width, int height) {
        double dWidth = (double) width;
        double dHeight = (double) height;

        double cWidth, cHeight;

        if (width > height) {
            cHeight = 2;
            cWidth = cHeight / dHeight * dWidth;
        } else {
            cWidth = 2;
            cHeight = cWidth / dWidth * dHeight;
        }

        double cMinX = -cWidth / 2 - 0.5;
        double cMinY = -cHeight / 2;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = x + y * width;
                double cx = cMinX + (x / dWidth) * cWidth;
                double cy = cMinY + (y / dHeight) * cHeight;

                ComplexNumber zn = MandelbrotSet.iterate(cx, cy, 100);
                if (MandelbrotSet.contains(zn)) {
                    pixels[index] = 0x000000;
                } else {
                    pixels[index] = 0xffffff; //(int) ((zn.x + zn.y) / 256);
                }
            }
        }
    }

    public void drawMandelbrotSet(int[] pixels, int width, int height) {
        double dWidth = (double) width;
        double dHeight = (double) height;

        double cWidth, cHeight;

        if (width > height) {
            cHeight = 2;
            cWidth = cHeight / dHeight * dWidth;
        } else {
            cWidth = 2;
            cHeight = cWidth / dWidth * dHeight;
        }

        double cMinX = -cWidth / 2 - 0.5;
        double cMinY = -cHeight / 2;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                double cx = cMinX + (x / dWidth) * cWidth;
                double cy = cMinY + (y / dHeight) * cHeight;

                double zx, zy, nzx, nzy;
                zx = 0;
                zy = 0;

                boolean inMandelbrotSet = true;
                int i;
                int maxIterations = 1000;
                int infinity = 200;
                for (i = 0; i < maxIterations; ++i) {
                    nzx = Math.pow(zx, 2) - Math.pow(zy, 2) + cx;
                    nzy = 2 * zx * zy + cy;
                    zx = nzx;
                    zy = nzy;
                    if (Math.abs(zx) > infinity || Math.abs(zy) > infinity) {
                        inMandelbrotSet = false;
                        break;
                    }
                }

                int index = x + y * width;
                if (inMandelbrotSet) {
                    pixels[index] = 0x000000;
                } else {
                    float z = (float) (zx + zy);
                    float iterationRatio = (i / (float) maxIterations);
                    float iterationRatioBoom = iterationRatio * 500 % 1;
                    float hue = iterationRatio;//0.2f;
                    float saturation = iterationRatioBoom * z % 1;
                    float brightness = iterationRatioBoom;
                    pixels[index] = Color.HSBtoRGB(hue, saturation, brightness);
                }
            }
        }
    }

}
