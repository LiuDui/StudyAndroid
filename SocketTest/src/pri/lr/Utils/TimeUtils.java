package pri.lr.Utils;

import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static SimpleDateFormat dataFormatInLog = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    public static String getLogTime(){
        return dataFormatInLog.format(new Date());
    }

}
