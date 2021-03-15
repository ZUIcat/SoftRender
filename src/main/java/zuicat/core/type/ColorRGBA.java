package zuicat.core.type;

public class ColorRGBA {
    public static final ColorRGBA WHITE = new ColorRGBA(0xFFFFFFFF);
    public static final ColorRGBA BLACK = new ColorRGBA(0x000000FF);
    public static final ColorRGBA RED = new ColorRGBA(0xFF0000FF);
    public static final ColorRGBA GREEN = new ColorRGBA(0x00FF00FF);
    public static final ColorRGBA BLUE = new ColorRGBA(0x0000FFFF);
    public static final ColorRGBA DARKGRAY = new ColorRGBA(0x666666FF);

    public byte r;
    public byte g;
    public byte b;
    public byte a;

    public ColorRGBA() {
       r = g = b = a = (byte) 0xFF;
    }

    public ColorRGBA(int color) {
        r = (byte) ((color >> 24) & 0xFF);
        g = (byte) ((color >> 16) & 0xFF);
        b = (byte) ((color >> 8) & 0xFF);
        a = (byte) (color & 0xFF);
    }

    public ColorRGBA(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColorRGBA(int r, int g, int b, int a) {
        this.r = (byte) r;
        this.g = (byte) g;
        this.b = (byte) b;
        this.a = (byte) a;
    }

    public ColorRGBA(float r, float g, float b, float a) {
        this.r = (byte) (r * 0xFF);
        this.g = (byte) (g * 0xFF);
        this.b = (byte) (b * 0xFF);
        this.a = (byte) (a * 0xFF);
    }

    public int getColorRGBA() {
        return (r << 24) & (g << 16) & (b << 8) & a;
    }
}
