package pri.lr.myclient;

import pri.lr.congfigs.NetConfig;

import java.io.*;
import java.net.Socket;

public class MyClientDemo {
    private static final String TAG = "MyClientDemo";

    public static void main(String[] args) throws IOException {
        MyClient myClient = new MyClient(new Socket(NetConfig.IP, NetConfig.PORT)); // 创建一个客户端
        myClient.startWork();

        myClient.sendMessage("this is a message");


 //      myClient.putFile("src/data_client/这是一个中文文件.txt");

//        myClient.getFile("accept.txt");
        myClient.stopWorkWithWorkDown();



//        myClient.putFile("src/data_client/这是一个中文文件.txt");

//        myClient.putFile(filePath);
//        myClient.getFile("test.txt"); // 从服务器取一个文件,放到本地

    }
}
