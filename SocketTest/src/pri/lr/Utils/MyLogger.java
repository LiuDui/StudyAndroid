package pri.lr.Utils;

import java.util.Date;

public class MyLogger {
    public static void log(String tag, String message){
        System.out.printf("%s: Id by tag <%s> -- %s\n", TimeUtils.getLogTime(), tag, message);
    }
}
