package pri.lr.myclient;

import pri.lr.Utils.CommandUtil;
import pri.lr.Utils.MyLogger;
import pri.lr.congfigs.NetConfig;

import java.io.*;
import java.net.Socket;

public class MyClientDemo {
    private static final String TAG = "MyClientDemo";
    private static final String filePath = "src/data_client/test.txt";

    public static void main(String[] args) throws IOException {
        MyClient myClient = new MyClient(new Socket(NetConfig.IP, NetConfig.PORT)); // 创建一个客户端
        myClient.startWork();

        myClient.sendMessage("this is a message");


//        myClient.putFile(filePath);
//        myClient.getFile("test.txt"); // 从服务器取一个文件,放到本地

//
//        Socket socket = null;
//        DataOutputStream outputStream = null;
//        DataInputStream inputStream = null;
//        BufferedReader bufferedReader = null;
//
//        DataInputStream in = null;
//
//        try {
//            // 以发送为例，先看文件有没有
//            File theFile = new File(filePath);
//
//            if(!theFile.exists()){
//                MyLogger.log(TAG, "要发送的文件不存在");
//                return;
//            }
//
//            String message = "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n" +
//                    "哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或\n";
//            byte[] strbytes = message.getBytes("utf-8");
//            String command = CommandUtil.prepareCommand()
//                    .putMod(CommandUtil.MOD_STR)
//                    .putMethod(CommandUtil.METHOD_PUT)
//                    .putMessage(message)
//                    .putdataLength(strbytes.length).create();
//
//            if(command == null){
//                MyLogger.log(TAG, "创建command失败");
//                return;
//            }
//
//            // 有的话再建立连接
//            socket = new Socket(NetConfig.IP, NetConfig.PORT);// 先建立连接
//
//            // 初始化各种流，用line发指令，用binary发数据
//            outputStream = new DataOutputStream(socket.getOutputStream());
//            inputStream = new DataInputStream(socket.getInputStream());
//
//            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            //bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
//            byte[] commandbyte = command.getBytes("utf-8");
//            outputStream.writeInt(commandbyte.length);
//            outputStream.write(commandbyte); // 发送指令
//            outputStream.write(strbytes);
////
////            in = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
////            byte[] buffer = new byte[1024];
////            while((in.read(buffer)) != -1){
////                outputStream.write(buffer);
////                MyLogger.log(TAG, "发送字节流");
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(in != null){
//                in.close();
//            }
//            if(bufferedReader != null){
//                bufferedReader.close();
//            }
//            if(outputStream != null){
//                outputStream.close();
//            }
//            if(socket != null){
//                socket.close();
//            }
//        }
    }
}
