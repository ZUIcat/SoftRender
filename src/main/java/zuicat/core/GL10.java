package zuicat.core;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import zuicat.core.type.ColorRGBA;
import zuicat.log.ZLogger;

public class GL10 implements IGL {
    // public static final int GL_DEPTH_TEST = 0;
    // public static final int GL_CULL_FACE = 0;

    public static final int GL_COLOR_BUFFER = 0x00000001;

    private BufferedImage bufferedImage;
    private byte[] glData;
    private byte[] glColorBuffer;
    private int width;
    private int height;

    private int glConfig;
    private ColorRGBA clearColor;

    public GL10(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        glData = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        glColorBuffer = new byte[width * height * 4];
        this.width = width;
        this.height = height;

        glConfig = 0;
        clearColor = ColorRGBA.BLACK;
    }

    @Override
    public void glEnable(int flag) {
        glConfig |= flag;
    }

    @Override
    public void glDisable(int flag) {
        glConfig |= (~flag);
    }

    @Override
    public void glClear(int flag) {
        if ((flag & GL10.GL_COLOR_BUFFER) == GL10.GL_COLOR_BUFFER) {
            for (int i = 0; i < width * height; i++) {
                glColorBuffer[i * 4 + 0] = clearColor.r;
                glColorBuffer[i * 4 + 1] = clearColor.g;
                glColorBuffer[i * 4 + 2] = clearColor.b;
                glColorBuffer[i * 4 + 3] = clearColor.a;
            }
        }
    }

    @Override
    public void glClearColor(ColorRGBA colorRGBA) {
        clearColor = colorRGBA; // TODO clone?
    }

    @Override
    public void glClearColor(byte r, byte g, byte b, byte a) {
        clearColor = new ColorRGBA(r, g, b, a);
    }

    @Override
    public void glClearColor(float r, float g, float b, float a) {
        clearColor = new ColorRGBA((byte) (r * 0xFF), (byte) (g * 0xFF), (byte) (b * 0xFF), (byte) (a * 0xFF));
    }

    @Override
    public void glDrawPoint(ColorRGBA colorRGBA, int x, int y) {
        glDrawPoint(colorRGBA.r, colorRGBA.g, colorRGBA.b, colorRGBA.a, x, y);
    }

    @Override
    public void glDrawPoint(byte r, byte g, byte b, byte a, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }
        int startPos = (x + y * width) * 4;
        glColorBuffer[startPos + 0] = r;
        glColorBuffer[startPos + 1] = g;
        glColorBuffer[startPos + 2] = b;
        glColorBuffer[startPos + 3] = a;
    }

    @Override
    public void glDrawPoint(float r, float g, float b, float a, int x, int y) {
        glDrawPoint((byte) (r * 0xFF), (byte) (g * 0xFF), (byte) (b * 0xFF), (byte) (a * 0xFF), x, y);
    }

    @Override
    public void glDrawLine(ColorRGBA colorRGBA, int x1, int y1, int x2, int y2) {
        glDrawLine(colorRGBA.r, colorRGBA.g, colorRGBA.b, colorRGBA.a, x1, y1, x2, y2);
    }

    @Override
    public void glDrawLine(byte r, byte g, byte b, byte a, int x1, int y1, int x2, int y2) {
        int nowX = x1;
        int nowY = y1;

        int delta = 0;
        int dx = x2 - x1;
        int dy = y2 - y1;
        int k1 = dx << 1;
        int k2 = dy << 1;
        int xStep = 1;
        int yStep = 1;

        if (dx < 0) {
            dx = -dx;
            k1 = -k1;
            xStep = -xStep;
        }

        if (dy < 0) {
            dy = -dy;
            k2 = -k2;
            yStep = -yStep;
        }

        glDrawPoint(r, g, b, a, nowX, nowY);
        if (dx < dy) {
            while (nowY != y2) {
                nowY += yStep;
                delta += k1;
                if (delta >= dy) {
                    nowX += xStep;
                    dy += k2;
                }
                glDrawPoint(r, g, b, a, nowX, nowY);
            }
        } else {
            while (nowX != x2) {
                nowX += xStep;
                delta += k2;
                if (delta >= dx) {
                    nowY += yStep;
                    dx += k1;
                }
                glDrawPoint(r, g, b, a, nowX, nowY);
            }
        }
    }

    @Override
    public void glDrawLine(float r, float g, float b, float a, int x1, int y1, int x2, int y2) {
        glDrawLine((byte) (r * 0xFF), (byte) (g * 0xFF), (byte) (b * 0xFF), (byte) (a * 0xFF), x1, y1, x2, y2);
    }

    @Override
    public void glDrawArrays() {

    }

    @Override
    public void refreshGLData() {
        for (int i = 0; i < width * height; i++) {
            glData[i * 3 + 0] = (byte) ((glColorBuffer[i * 4 + 2] & 0xFF) * (glColorBuffer[i * 4 + 3] & 0xFF) / 0xFF); // B
            glData[i * 3 + 1] = (byte) ((glColorBuffer[i * 4 + 1] & 0xFF) * (glColorBuffer[i * 4 + 3] & 0xFF) / 0xFF); // G
            glData[i * 3 + 2] = (byte) ((glColorBuffer[i * 4 + 0] & 0xFF) * (glColorBuffer[i * 4 + 3] & 0xFF) / 0xFF); // R
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
