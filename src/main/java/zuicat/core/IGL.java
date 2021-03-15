package zuicat.core;

import java.awt.image.BufferedImage;

import zuicat.core.type.ColorRGBA;

public interface IGL {
    public void glEnable(int flag);
    public void glDisable(int flag);

    public void glClear(int flag);

    public void glClearColor(ColorRGBA colorRGBA);
    public void glClearColor(byte r, byte g, byte b, byte a);
    public void glClearColor(float r, float g, float b, float a);

    public void glDrawPoint(ColorRGBA colorRGBA, int x, int y);
    public void glDrawPoint(byte r, byte g, byte b, byte a, int x, int y);
    public void glDrawPoint(float r, float g, float b, float a, int x, int y);

    public void glDrawLine(ColorRGBA colorRGBA, int x1, int y1, int x2, int y2);
    public void glDrawLine(byte r, byte g, byte b, byte a, int x1, int y1, int x2, int y2);
    public void glDrawLine(float r, float g, float b, float a, int x1, int y1, int x2, int y2);
    
    public void glDrawArrays();

    public void refreshGLData();
    public BufferedImage getBufferedImage();
}
