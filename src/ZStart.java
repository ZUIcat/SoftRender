import app.MyApplication;
import log.ZLogger;

class ZStart {
    public static void main(String[] args) {
        ZLogger.ins().info("ZStart", "-- START --");
        new MyApplication(1024, 768, "SoftRender");
        ZLogger.ins().info("ZStart", "-- END --");
    }
}
