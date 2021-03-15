package zuicat.app;

import zuicat.core.type.ColorRGBA;
import zuicat.core.GL10;
import zuicat.core.IGL;
import zuicat.core.IRender;
import zuicat.core.SurfaceView;
import zuicat.log.ZLogger;

public class MySurfaceView extends SurfaceView {
    private MyRender myRender;

    private float tempValue = 0.0f;

    public MySurfaceView(int width, int height) {
        super(width, height);

        myRender = new MyRender();
        setRender(myRender);
        setGLVersion(1);
        setShowFPS(true);
    }

    private class MyRender implements IRender {
        @Override
        public void onSurfaceCreated(IGL gl, int width, int height) {
            ZLogger.ins().info("onSurfaceCreated");
            gl.glClearColor(ColorRGBA.BLACK);
        }

        @Override
        public void onDrawFrame(IGL gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER);

            int tempValueI = (int) (tempValue += 0.3f);

            for (int i = 0; i < 361; i++) {
                int x = (int) (Math.sin(Math.toRadians(i)) * 100);
                int y = (int) (Math.cos(Math.toRadians(i)) * 100);
                gl.glDrawLine(new ColorRGBA(1.0f * (i / 360.0f), 1.0f * ((360 - i) / 360.0f), 0, 1), 300 + tempValueI,
                        300 + tempValueI, 300 + tempValueI + x, 300 + tempValueI + y);
            }

            for (int i = 0; i < 200; i++) {
                gl.glDrawLine(ColorRGBA.BLUE, 200 + tempValueI, i + tempValueI, 400 + tempValueI, i + tempValueI);
            }
        }
    }
}
