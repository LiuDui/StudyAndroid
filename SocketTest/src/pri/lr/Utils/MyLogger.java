package pri.lr.Utils;

import java.util.Date;

public class MyLogger {
    public static void log(String tag, String message){
        System.out.printf("%s: <%-16s> : %s\n", TimeUtils.getLogTime(), tag, message);
    }
}
