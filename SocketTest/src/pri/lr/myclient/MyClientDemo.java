package pri.lr.myclient;

import pri.lr.congfigs.NetConfig;
import pri.lr.myserver.SocketManger;

import java.io.IOException;
import java.net.Socket;

public class MyClientDemo {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(NetConfig.IP, NetConfig.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
