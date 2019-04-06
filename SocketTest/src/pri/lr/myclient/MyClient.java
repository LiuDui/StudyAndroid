package pri.lr.myclient;

import pri.lr.Utils.CommandUtil;
import pri.lr.Utils.CommondMessageQueue;
import pri.lr.Utils.MyLogger;
import pri.lr.congfigs.RoleConfig;

import java.io.*;
import java.net.Socket;

public class MyClient implements Runnable{
    private static final String TAG = "MyClient";
    public String dataDir;

    protected final int BUFFER_SIZE = 1024;
    protected Socket socket;

    private int role = 0;

    protected byte[] byteBuffer = new byte[BUFFER_SIZE];
    protected boolean RUN_ON = false;

    protected Thread TaskHolderThread = null;
    protected CommondMessageQueue<CommandUtil> messageQueue = null;

    protected DataOutputStream socketOutputStream = null;
    protected DataInputStream socketInputStream = null;

    protected boolean doneWork = false;


    public MyClient(Socket socket) {
        this.socket = socket;
        this.setDataDir(RoleConfig.ClientDataDir);
        messageQueue = new CommondMessageQueue<>(5);
    }

    public void startWork(){
        TaskHolderThread = new Thread(this);
        RUN_ON = true;
        doneWork = false;
        TaskHolderThread.start();
    }

    public void stopWorkWithWorkDown(){
        messageQueue.refuseAll();
        doneWork = true;
        TaskHolderThread.interrupt();
    }
    public void stopWorkWithAllTaskAbandoned(){
        TaskHolderThread.interrupt();
    }

    @Override
    public void run() {
        // 取任务，做任务
        CommandUtil commandUtil = null;
        try {
            socketInputStream = new DataInputStream(socket.getInputStream());
            socketOutputStream = new DataOutputStream(socket.getOutputStream());
            while (RUN_ON){
                if(doneWork){
                    try {
                        commandUtil = messageQueue.remove();
                        MyLogger.logInfo(TAG, " fetch commandUtil: " + commandUtil);
                        dispatchTask(commandUtil);
                        if(messageQueue.isEmpty()){
                            stopWorkWithAllTaskAbandoned();
                        }
                    } catch (InterruptedException e) {
                        MyLogger.logException(TAG, "interrupt the thread, and done all the work");
                        RUN_ON = false;
                        doneWork = false;
                    }
                }else{
                    try {
                        commandUtil = messageQueue.remove();
                        dispatchTask(commandUtil);
                    } catch (InterruptedException e) {
                        MyLogger.logException(TAG, "interrupt the thread, and abandoned all the work");
                        RUN_ON = false;
                        doneWork = false;
                    }

                }
            }
        } catch (IOException e) {
            MyLogger.logException(TAG, "inputstream or outputstream from socket is wrong in <run>");
        } finally {
            MyLogger.logInfo(TAG, "clear the connection");
            try {
                socketOutputStream.writeInt(0);
                System.out.println("最后一个0也发了：");
            } catch (IOException e) {
                e.printStackTrace();
            }
            clear();
        }
    }

    protected void clear(){
        if(socketInputStream != null){
            try {
                socketInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socketOutputStream != null){
            try {
                socketOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatchTask(CommandUtil commandUtil) {
        if(CommandUtil.MOD_FILE.equals(commandUtil.getMod())){
            // 这是文件处理
            if(CommandUtil.METHOD_GET.equals(commandUtil.getMethod())){
                getFile(commandUtil);
            }else if(CommandUtil.METHOD_PUT.equals(commandUtil.getMethod())){
                putFile(commandUtil);
            }else{
                MyLogger.logError(TAG, "mod can not deal in <dispatchTask>");
            }
        }else{
            sendMessage(commandUtil);   // 消息发送
        }
    }

    public void getFile(CommandUtil commandUtil){
        String commond = commandUtil.create();
        System.out.println(123);
        // 这里发送一个get命令，首先要看下有没有文件，所以对方要发一个message命令回复
        try {
            byte[] commondBytes = commond.getBytes("utf-8");
            socketOutputStream.writeInt(commondBytes.length);
            socketOutputStream.write(commondBytes); // 把命令传过去
            // 发送后，如果对方有该文件，就返回一个putfile指令，判断其中file名称是否相同，若不相同，则认为没有该文件，则loagger不存在

            int commandLen = socketInputStream.readInt();
            if(commandLen == 0){
                stopWorkWithAllTaskAbandoned();
            }
            byte[] commandBuffer = new byte[commandLen];
            socketInputStream.read(commandBuffer);
            String line = new String(commandBuffer, "utf-8");
            CommandUtil commandUtilFromServer = CommandUtil.parseCommand(line);
            if (commandUtilFromServer.getFileName() != null && commandUtilFromServer.getFileName().equals(commandUtil.getFileName())
            && CommandUtil.METHOD_PUT.equals(commandUtil.getMethod())){
                downLoadFileFromRemote(commandUtil.getFileName());
            }else{
                MyLogger.logInfo(TAG, "the file which is found is not exists");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String readMessage(CommandUtil commandUtil){
        int datalen = (int) commandUtil.getdataLength();
        byte[] messageBytes = new byte[datalen];
        int readLen = 0;
        try {
            while (readLen != datalen && RUN_ON){
                readLen += socketInputStream.read(messageBytes, readLen, datalen - readLen);
            }
            return new String(messageBytes, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void downLoadFileFromRemote(String fileName) {
        File file = new File(getDataDir() + fileName);
        DataOutputStream fos = null;
        try {
             fos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

             int oneReadLen = socketInputStream.read(byteBuffer);
             while (oneReadLen != -1){
                 fos.write(byteBuffer, 0, oneReadLen);
                 oneReadLen = socketInputStream.read(byteBuffer);
             }
             MyLogger.logInfo(TAG, "the file  " + fileName + " received");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // commandUtil中仅仅保存着filepath
    public void putFile(CommandUtil commandUtil){
        String filePath = commandUtil.getFileName();
        File theFile = new File(filePath);

        if(!theFile.exists()) {
            MyLogger.logError(TAG, "the file" + theFile.getName() + "is not exists!");
            return;
        }
        MyLogger.logInfo(TAG, "the file" + theFile.getName() + "is found");

        // has find file, then create command
        DataInputStream fin = null;
        try {
            byte[] commandyte  = commandUtil.setFileName(theFile.getName())
                    .setdataLength((int) theFile.length()).create()
                    .getBytes("utf-8");
            socketOutputStream.writeInt(commandyte.length);
            socketOutputStream.write(commandyte);

            fin = new DataInputStream(new BufferedInputStream(new FileInputStream(theFile)));
            int onceReadLen = fin.read(byteBuffer);
            while (onceReadLen != -1){
                socketOutputStream.write(byteBuffer, 0, onceReadLen);
                onceReadLen = fin.read(byteBuffer);
            }
            socketOutputStream.writeInt(0);
            MyLogger.logInfo(TAG, "the file send done");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null){
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMessage(CommandUtil commandUtil){
        String message = commandUtil.getMessage();
        try {
            byte[] messageBytes = message.getBytes("utf-8");
            commandUtil.setdataLength(messageBytes.length);
            byte[] commdByte = commandUtil.create().getBytes("utf-8");

            socketOutputStream.writeInt(commdByte.length);
            socketOutputStream.write(commdByte);
            socketOutputStream.write(messageBytes);
            MyLogger.logInfo(TAG, "send message" + commandUtil.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean getFile(String srcFileName){
        CommandUtil commandUtil = CommandUtil.prepareCommand()
                .setMethod(CommandUtil.METHOD_GET)
                .setMod(CommandUtil.MOD_FILE)
                .setFileName(srcFileName);
        try {
            messageQueue.add(commandUtil);
        } catch (InterruptedException e) {
            MyLogger.logException(TAG, "the thread interupt in <getFile>");
        } finally {
            // 线程被退出了
        }
        return true;
    }

    public void putFile(String filePath) {
        CommandUtil commandUtil = CommandUtil.prepareCommand()
                .setMethod(CommandUtil.METHOD_PUT)
                .setMod(CommandUtil.MOD_FILE)
                .setFileName(filePath);

        try {
            messageQueue.add(commandUtil);
        } catch (InterruptedException e) {
            MyLogger.logException(TAG, "the thread interupt in <putFile>");
        }
    }

    public void sendMessage(String theMessage) {
        CommandUtil commandUtil = CommandUtil.prepareCommand()
                .setMethod(CommandUtil.METHOD_PUT)
                .setMod(CommandUtil.MOD_STR)
                .setMessage(theMessage);
        try {
            messageQueue.add(commandUtil);
            MyLogger.logInfo(TAG, "Add mesasge to MessageQueue in <sendMessage>");
        } catch (InterruptedException e) {
            MyLogger.logException(TAG, "the thread interupt in <sendMessage>");
        } finally {
        }
    }
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getSocketOutputStream() {
        return socketOutputStream;
    }

    public void setSocketOutputStream(DataOutputStream socketOutputStream) {
        this.socketOutputStream = socketOutputStream;
    }

    public DataInputStream getSocketInputStream() {
        return socketInputStream;
    }

    public void setSocketInputStream(DataInputStream socketInputStream) {
        this.socketInputStream = socketInputStream;
    }

    public String getDataDir() {
        return this.dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }
}


