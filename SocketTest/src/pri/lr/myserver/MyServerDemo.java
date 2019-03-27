package pri.lr.myserver;

import pri.lr.congfigs.NetConfig;

import java.io.IOException;

public class MyServerDemo {
    public static void main(String[] args) {
        MyServer myServer = null;

        try {
            myServer = new MyServer(NetConfig.PORT);
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (myServer != null){
                myServer.stopAndremoveAllTask();
            }
        }

    }
}
