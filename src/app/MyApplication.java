package app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import core.SurfaceView;
import log.ZLogger;

public class MyApplication extends WindowAdapter {
    private JFrame frame;
    private SurfaceView surfaceView;

    private Thread svThread;

    public MyApplication(int width, int height, String title) {
        surfaceView = new MySurfaceView(width, height);

        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setTitle(title);
        frame.add(surfaceView.getCanvas());
        frame.pack();
        frame.addWindowListener(this);
        frame.setVisible(true);

        centerFrame();
    }

    private void centerFrame() {
        Dimension size = frame.getSize();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - size.width) / 2;
        int y = (screen.height - size.height) / 2;
        frame.setLocation(x, y);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        svThread = new Thread(() -> surfaceView.start(), "Drawing surfaceView");
        svThread.start();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        surfaceView.stop();
        try {
            svThread.join();
        } catch (InterruptedException e1) {
            ZLogger.ins().error(e1.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
