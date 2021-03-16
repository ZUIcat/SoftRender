package zuicat.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZLogger {
    private static ZLogger instance;

    private SimpleDateFormat sDTFormat;
    private SimpleDateFormat sTimeFormat;
    private SimpleDateFormat sDateTimeFormat;

    private int maxStackDepth;

    private ZLogger() {
        sTimeFormat = new SimpleDateFormat("hh:mm:ss.SSS");
        sDateTimeFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS");

        setMaxStackDepth(20);
        showDate(false);
    }

    public static ZLogger ins() {
        if (instance == null) {
            instance = new ZLogger();
        }
        return instance;
    }

    public void showDate(boolean show) {
        if (show) {
            sDTFormat = sDateTimeFormat;
        } else {
            sDTFormat = sTimeFormat;
        }
    }

    public void print(String tag, String content) {
        System.out.format("%s : %s%n", tag, content);
    }

    public void print(String content) {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        ZLogger.ins().print(ste[2].getClassName(), content);
    }

    public void print(String content, Object... args) {
        ZLogger.ins().print(String.format(content, args));
    }

    public void info(String tag, String content) {
        System.out.format("\033[37;1m[%s] %s : %s\033[m%n", sDTFormat.format(new Date()), tag, content);
    }

    public void info(String content) {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        ZLogger.ins().info(ste[2].getClassName(), content);
    }

    public void info(String content, Object... args) {
        ZLogger.ins().info(String.format(content, args));
    }

    public void warning(String tag, String content) {
        System.out.format("\033[33;1m[%s] %s : %s\033[m%n", sDTFormat.format(new Date()), tag, content);
    }

    public void warning(String content) {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        ZLogger.ins().warning(ste[2].getClassName(), content);
    }

    public void warning(String content, Object... args) {
        ZLogger.ins().warning(String.format(content, args));
    }

    public void error(String tag, String content) {
        System.out.format("\033[31;1m[%s] %s : %s\033[m%n", sDTFormat.format(new Date()), tag, content);
    }

    public void error(String content) {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        ZLogger.ins().error(ste[2].getClassName(), content);
        if (maxStackDepth != 0) {
            int nowDepth = Math.min(maxStackDepth + 2, ste.length);
            for (int i = 2; i < nowDepth; i++) {
                System.out.format("\033[31;1m    at %s.%s(%s:%d)\033[m%n", ste[i].getClassName(), ste[i].getMethodName(), ste[i].getFileName(), ste[i].getLineNumber());
            }
            if (maxStackDepth < ste.length) {
                System.out.format("\033[31;1m    ......\033[m%n");
            }
        }
    }

    public void error(String content, Object... args) {
        ZLogger.ins().error(String.format(content, args));
    }

    // getter and setter

    public int getMaxStackDepth() {
        return maxStackDepth;
    }

    public void setMaxStackDepth(int maxStackDepth) {
        if (maxStackDepth >= 0) {
            this.maxStackDepth = maxStackDepth;
        } else {
            ZLogger.ins().error("maxStackDepth cannot be less than 0! The value has not changed!");
        }
    }
}
