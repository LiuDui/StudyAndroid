package pri.lr.Utils;

import java.io.File;

// comman 分为几部分：方法名（get put）,数据mod（str，bny）
// 更多信息（fileinfo或 message）
// fileinfo :文件名\n文件大小
// message string utf8
public class CommandUtil {
    private static final String TAG = "CommandUtil";
    public static final String METHOD_GET = "get";
    public static final String METHOD_PUT = "put";

    private static final String seprater = "&";

    public static final String MOD_STR = "str"; // 直接读，然后byte->string
    public static final String MOD_FILE = "file"; // 写文件,统一用二进制写入

    private String method;
    private String mod;

    private String fileName; // 可选项
    private long dataLength;

    private String message;

    public static CommandUtil parseCommand(String command){
        String[] cmds = command.split(seprater);

        CommandUtil commandUtil = new CommandUtil();
        commandUtil.setMethod(cmds[0]);
        commandUtil.setMod(cmds[1]);
        if (commandUtil.isFile()){
            commandUtil.setFileName(cmds[2]);
            commandUtil.setdataLength(Integer.parseInt(cmds[3]));
        }else{
            commandUtil.setdataLength(Integer.parseInt(cmds[2]));
        }
        MyLogger.logInfo(TAG, "parseComman:" + commandUtil.toString());
        return commandUtil;
    }

    public boolean isFile(){
        return MOD_FILE.equals(this.mod);
    }
    public static CommandUtil prepareCommand(){
        return new CommandUtil();
    }

    public String create(){
        if (this.method == null){
            MyLogger.logError(TAG, "创建command时未传入方法名");
            return null;
        }

        if (this.mod == null){
            MyLogger.logError(TAG, "创建command时未传入mod");
            return null;
        }

        String command = null;

        if (MOD_STR.equals(this.mod)){
            command = this.method + seprater + this.mod + seprater + String.valueOf(this.dataLength);
        }else if(MOD_FILE.equals(this.mod)){
            if (this.fileName == null){
                MyLogger.logError(TAG, "创建文件传输command时未传入文件名，无法进行解析");  // 这里抛异常更合适
                return null;
            }
            command =  this.method + seprater + this.mod + seprater + this.fileName + seprater +  String.valueOf(this.dataLength);
        }else {
            MyLogger.logError(TAG, "创建command时mod未知，无法进行解析");  // 这里抛异常更合适
        }
        MyLogger.logInfo(TAG, "create command:" + this.toString());
        return command;
    }


    public String getMethod() {
        return method;
    }

    public CommandUtil setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getMod() {
        return mod;
    }

    public CommandUtil setMod(String mod) {
        this.mod = mod;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public CommandUtil setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public long getdataLength() {
        return dataLength;
    }

    public CommandUtil setdataLength(int dataLength) {
        this.dataLength = dataLength;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommandUtil setMessage(String message) {
        this.message = message;
        return  this;
    }

    public String toStrString() {
        return "CommandUtil{" +
                "method='" + method + '\'' +
                ", mod='" + mod + '\'' +
                ", dataLength=" + dataLength +
                ", message='" + message + '\'' +
                '}';
    }

    public String toFileString() {
        return "CommandUtil{" +
                "method='" + method + '\'' +
                ", mod='" + mod + '\'' +
                ", fileName='" + fileName + '\'' +
                ", dataLength=" + dataLength +
                '}';
    }

    @Override
    public String toString(){
        if (MOD_FILE.equals(this.mod)){
            return this.toFileString();
        }else {
            return this.toStrString();
        }
    }

}
