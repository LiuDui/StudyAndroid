package pri.lr.Utils;

import java.util.Date;

public class MyLogger {

    public static void logInfo(String tag, String message){
        System.out.printf("%s: [%-10s]  <%-16s> : %s\n", TimeUtils.getLogTime(),"INFO",tag, message);
    }

    public static void logException(String tag, String message){
        System.out.printf("%s: [%-10s]  <%-16s> : %s\n", TimeUtils.getLogTime(), "EXCEPTION", tag, message);
    }

    public static void logError(String tag, String message){
        System.out.printf("%s: [%-10s]  <%-16s> : %s\n", TimeUtils.getLogTime(), "ERROR", tag, message);
    }

}
