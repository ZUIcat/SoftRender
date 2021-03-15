package zuicat.core;

public interface IRender {
    public void onSurfaceCreated(IGL gl, int width, int height);
    public void onDrawFrame(IGL gl);
}
