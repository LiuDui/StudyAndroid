package pri.lr.myserver;

import pri.lr.abandon.SocketConnection;

import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class SocketManger {
    private static String TAG = "SocketManger";

    private Map<NetInfo, SocketConnect> socketsHolder;

    public SocketManger() {
        this.socketsHolder = new LinkedHashMap<>();
    }

    public boolean exits(NetInfo netInfo){
        return socketsHolder.get(netInfo) == null ? false :true;
    }
    public boolean exits(Socket socket){
        return socketsHolder.get(NetInfo.parseNetInfo(socket)) == null ? false :true;
    }

    public boolean addSocket(Socket socket){
        if (this.exits(socket)){
            //TODO 添加了一个已经有的socket
            // 1. 移除的没有清理，所以在SocketConnect里面，如果任务执行完，需要主动调用移除方法
            // 2. 重复的添加
        }
        NetInfo netInfo = new NetInfo(socket.getInetAddress().toString(), socket.getPort());
        SocketConnect connection = new SocketConnect(socket);

        socketsHolder.put(netInfo, connection);
        connection.startWork();
        //TODO
//        if (socketsHolder.get());
        return false;
    }

    public void removeAndClose(){

    }

    public SocketConnection remove(Socket socket){
        return null;
    }

    public void removeAndCloseAll() {
        socketsHolder.forEach((k,v)->{

        });
    }
}
