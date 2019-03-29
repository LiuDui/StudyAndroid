package pri.lr.myserver;


import pri.lr.Utils.CommandUtil;
import pri.lr.Utils.MyLogger;
import pri.lr.congfigs.RoleConfig;
import pri.lr.myclient.MyClient;

import java.io.*;
import java.net.Socket;

public class SocketConnect extends MyClient {
    private static final String TAG = "SocketConnect";

    public SocketConnect(Socket socket) {
        super(socket);
        this.setDataDir(RoleConfig.ServerDataDir);
    }

    @Override
    public void run() {
        try {
            socketInputStream = (new DataInputStream(socket.getInputStream()));
            socketOutputStream = (new DataOutputStream(socket.getOutputStream()));

            while (RUN_ON) {
                int commandLen = socketInputStream.readInt();
                System.out.println("get command length:" + commandLen);
                if(commandLen == 0){
                    RUN_ON = false;
                    continue;
                }

                byte[] commandBytes = new byte[commandLen];
                int readLen = 0;
                while (readLen != commandLen && RUN_ON){
                    readLen += socketInputStream.read(commandBytes, readLen, commandLen - readLen);
                }

                String command = new String(commandBytes, "utf-8");
                CommandUtil commandUtil = CommandUtil.parseCommand(command);

                if(CommandUtil.MOD_FILE.equals(commandUtil.getMod())){
                    if(CommandUtil.METHOD_PUT.equals(commandUtil.getMethod())){
                        downLoadFileFromRemote(commandUtil.getFileName());
                    }else if (CommandUtil.METHOD_GET.equals(commandUtil.getMethod())){
                        CommandUtil commandUtilRebck = CommandUtil.prepareCommand()
                                .setMethod(CommandUtil.METHOD_PUT)
                                .setMod(CommandUtil.MOD_FILE)
                                .setFileName(getDataDir() + commandUtil.getFileName());
                        putFile(commandUtil);
                    }else {
                        MyLogger.logError(TAG, "cant match method");
                    }
                }else if(CommandUtil.MOD_STR.equals(commandUtil.getMod())){
                    MyLogger.logInfo(TAG, "Receive message from " + getSocket().getInetAddress() + " : " + readMessage(commandUtil));
                }else {
                    MyLogger.logError(TAG, "the mod cant be match in Server Receive");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            MyLogger.logException(TAG, "get in/out stream exception");
        } finally {
            clear();
        }
    }
}
