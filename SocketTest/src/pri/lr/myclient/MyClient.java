package pri.lr.myclient;

import pri.lr.Utils.CommandUtil;
import pri.lr.Utils.CommondMessageQueue;
import pri.lr.Utils.MyLogger;

import java.io.*;
import java.net.Socket;

public class MyClient implements Runnable{
    private static final String TAG = "MyClient";
    private Socket socket;

    private boolean RUN_ON = false;

    private Thread TaskHolderThread = null;
    private CommondMessageQueue<CommandUtil> messageQueue = null;

    private DataOutputStream socketOutputStream = null;
    private DataInputStream socketInputStream = null;

    public MyClient(Socket socket) {
        this.socket = socket;
        messageQueue = new CommondMessageQueue<>(5);
    }

    public void startWork(){
        TaskHolderThread = new Thread(this);
        RUN_ON = true;
        TaskHolderThread.start();
    }

    @Override
    public void run() {
        // 取任务，做任务
        CommandUtil commandUtil = null;
        try {
            socketInputStream = new DataInputStream(socket.getInputStream());
            socketOutputStream = new DataOutputStream(socket.getOutputStream());

            while (RUN_ON){
                try {
                    commandUtil = messageQueue.remove();
                    dispatchTask(commandUtil);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            MyLogger.log(TAG, "inputstream or outputstream from socket is wrong in <run>");
        } finally {
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
            if(socket != null && !socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


//
//        File theFile = new File(filePath);
//
//        if(!theFile.exists()){
//            MyLogger.log(TAG, "the file" + theFile.getName() + "is not exists!");
//            return;
//        }
        // 做相应的工作
    }

    private void dispatchTask(CommandUtil commandUtil) {
        if(CommandUtil.MOD_FILE.equals(commandUtil.getMod())){
            // 这是文件处理
            if(CommandUtil.METHOD_GET.equals(commandUtil.getMethod())){

            }else if(CommandUtil.METHOD_PUT.equals(commandUtil.getMethod())){

            }else{
                MyLogger.log(TAG, "mod can not deal in <dispatchTask>");
            }
        }else{
            // 是字符串处理
            sendMessage(commandUtil);
        }

    }

    private void sendMessage(CommandUtil commandUtil){
        String message = commandUtil.getMessage();
        try {
            byte[] messageBytes = message.getBytes("utf-8");
            commandUtil.setdataLength(messageBytes.length);
            String commd = commandUtil.create();
            socketOutputStream.writeInt(commd.length());
            socketOutputStream.write(messageBytes);
            MyLogger.log(TAG, "send message" + commandUtil.toString());
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
            MyLogger.log(TAG, "the thread interupt in <getFile>");
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
            MyLogger.log(TAG, "the thread interupt in <putFile>");
        } finally {
            // 线程被退出了
        }
    }

    public void sendMessage(String theMessage) {
        CommandUtil commandUtil = CommandUtil.prepareCommand()
                .setMethod(CommandUtil.METHOD_PUT)
                .setMod(CommandUtil.MOD_STR)
                .setMessage(theMessage);
        try {
            messageQueue.add(commandUtil);
            MyLogger.log(TAG, "Add mesasge to MessageQueue in <sendMessage>");
        } catch (InterruptedException e) {
            MyLogger.log(TAG, "the thread interupt in <sendMessage>");
        } finally {
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
