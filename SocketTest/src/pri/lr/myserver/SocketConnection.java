package pri.lr.myserver;

import pri.lr.Utils.CommandUtil;
import pri.lr.Utils.MyLogger;

import java.io.*;
import java.net.Socket;

// 这一层可以对serversocket透明，只让socketManger知道，其实作为一个内部类也可以
public class SocketConnection implements Runnable{
    private static final String TAG = "SocketConnection";
    private static final int BUFF_SIZE = 1024;
    // socket 一定要有，dataoutputStream 要有  ,dataInputSrtream要有，终止线程要有，清理资源要有
    private Socket socket;

    Thread thisThread;

    private boolean isCoonectTionKeepOn = false;


    public SocketConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // socket
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        // 文件
        InputStream in = null;
        OutputStream out = null;

        try {
            // 初始化各种流，用line发指令，用binary发数据
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int commandLen = inputStream.readInt();
            byte[] commandBuffer = new byte[commandLen];
            inputStream.read(commandBuffer);
            String line = new String(commandBuffer, "utf-8");
            System.out.println("11111111111" + line);
            CommandUtil commandUtil = CommandUtil.parseCommand(line);

            byte[] buffer = new byte[BUFF_SIZE];

            long datalength = commandUtil.getdataLength(); // 可以直接计算出来，读几个完整的，
            int times = (int)datalength/BUFF_SIZE;
            int last = (int)datalength%BUFF_SIZE;

            // 输入都是一样的，只是输出不一样
            if(commandUtil.isFile()){ // 处理文件
                File file = new File("src/data_server/" + commandUtil.getFileName());
                out = new DataOutputStream(new FileOutputStream(file));

                // 网络不好的时候也可能读不到，所以应该知道读够了为止

                for(int i = 0; i < times; i++){
                    inputStream.read(buffer); // 阻塞方法
                    out.write(buffer);
                }
                System.out.println(inputStream.read(buffer,0,last) == last);
                out.write(buffer, 0, last);
            }else{ // 处理字符串
                byte[] strByte = new byte[(int)datalength];
                for(int i = 0; i < times; i++){
                    inputStream.read(strByte, i * BUFF_SIZE, BUFF_SIZE);
                }

                inputStream.read(strByte, times * BUFF_SIZE, last);
                System.out.println(new String(strByte, "utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void startCommunication(){
        thisThread = new Thread(this);
        isCoonectTionKeepOn = true;
        thisThread.start();
    }

    // 等待任务完成
    public void stopWithTaskDown(){
        try {
            thisThread.join();// 让他运行完
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 清理资源
            //TODO
        }
    }

    // 强行停止
    public void stopIgnoreTask(){
        thisThread.interrupt();

    }

    public void clear(){

    }

    public boolean isClosed() {
        return false;
    }

    public void close() {
    }
}
