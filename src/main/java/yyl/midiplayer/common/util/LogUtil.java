package yyl.midiplayer.common.util;

public class LogUtil {

    public static void info(String message) {
        System.out.println(message);
    }

    public static void error(String message) {
        System.err.println(message);
    }

    public static void error(String message, Throwable throwable) {
        System.err.println(message);
        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }
}
