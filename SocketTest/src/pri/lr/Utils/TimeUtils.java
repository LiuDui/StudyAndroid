package pri.lr.Utils;

import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtils {

    private static final DateTimeFormatter dataFormatInLog = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");

    public static String getLogTime(){
        return dataFormatInLog.format(LocalDateTime.now());
    }

}
