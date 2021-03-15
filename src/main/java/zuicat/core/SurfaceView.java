package zuicat.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import zuicat.log.ZLogger;

import java.awt.image.BufferStrategy;

import zuicat.core.type.StaticCircleQueueFloat;

public class SurfaceView {
    private IRender render;

    private Canvas canvas;
    private BufferStrategy bufferStrategy;

    private int width;
    private int height;

    private long startTime;
    private long previousTime;
    private long deltaTime;
    private float delta;
    private long fixedTime;
    private long totalTime;
    private boolean running;
    private boolean showFPS;
    private Color fpsColor;
    private StaticCircleQueueFloat fpsQueue;
    private IGL gl;

    public SurfaceView(int width, int height) {
        this.width = width;
        this.height = height;

        running = false;
        showFPS = false;
        fpsColor = Color.RED;
        setFrameRate(30);

        canvas = new Canvas();
        Dimension size = new Dimension(width, height);
        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);
        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    public void start() {
        if (render == null) {
            ZLogger.ins().error("The render is null!");
            return;
        }
        if (gl == null) {
            ZLogger.ins().error("The gl is null!");
            return;
        }

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        running = true;
        startTime = System.nanoTime();
        previousTime = System.nanoTime();
        render.onSurfaceCreated(gl, width, height);
        while (running) {
            deltaTime = System.nanoTime() - previousTime;
            if (deltaTime < fixedTime) {
                try {
                    long waitTime = fixedTime - deltaTime;
                    long millis = waitTime / 1000000;
                    long nanos = waitTime - millis * 1000000;
                    Thread.sleep(millis, (int) nanos);
                } catch (InterruptedException e) {
                    ZLogger.ins().error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
                deltaTime = System.nanoTime() - previousTime;
            }
            previousTime = System.nanoTime();
            delta = deltaTime / 1000000000.0f;
            update();
        }
        totalTime = System.nanoTime() - startTime;
        ZLogger.ins().info("The totalTime is %.2fs", totalTime / 1000000000.0f);
    }

    private void update() {
        Graphics graphics = bufferStrategy.getDrawGraphics();
        render.onDrawFrame(gl);
        gl.refreshGLData();
        graphics.drawImage(gl.getBufferedImage(), 0, 0, width, height, null);
        if (showFPS) {
            Color preColor = graphics.getColor();
            graphics.setColor(fpsColor);
            fpsQueue.add(delta);
            graphics.drawString(String.format("FPS: %.2f", 1.0f / fpsQueue.getAverage()), 10, 20);
            graphics.setColor(preColor);
        }
        graphics.dispose();
        bufferStrategy.show();
    }

    public void stop() {
        running = false;
    }

    // getter and setter -----------------------------------

    public void setRender(IRender render) {
        this.render = render;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isShowFPS() {
        return showFPS;
    }

    public void setShowFPS(boolean showFPS) {
        this.showFPS = showFPS;
    }

    public Color getFpsColor() {
        return fpsColor;
    }

    public void setFpsColor(Color fpsColor) {
        this.fpsColor = fpsColor;
    }

    public void setFrameRate(int framerate) {
        this.fixedTime = 1000000000 / framerate;
        fpsQueue = new StaticCircleQueueFloat(framerate);
    }

    public void setGLVersion(int version) {
        switch (version) {
            case 1:
                gl = new GL10(width, height);
                break;
            case 2:
                ZLogger.ins().error("GL version 2 has not yet been implemented.");
                break;
            default:
                ZLogger.ins().error("Unknown GL version %d.", version);
                break;
        }
    }

    public IGL getGL() {
        return gl;
    }
}
