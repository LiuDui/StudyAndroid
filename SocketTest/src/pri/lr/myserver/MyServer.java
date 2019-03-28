package pri.lr.myserver;

import pri.lr.Utils.MyLogger;
import pri.lr.congfigs.NetConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 每个MyServer实例，一个Serversocket实例
 */
public class MyServer {
    private static String TAG = "MyServer";
    private ServerSocket serverSocketInstence = null; // 直接用静态单例
    private SocketManger socketManger = null;



    private boolean listening = false;

    public MyServer(int port, SocketManger socketManger) throws IOException {
        serverSocketInstence = new ServerSocket(port);
        this.socketManger = socketManger;
    }

    public boolean start(){
        if (listening == true){
            if (!serverSocketInstence.isClosed()){
                MyLogger.log(TAG, "重复开启serversocket");
                return true;
            }
        }
        listening = true;
        while (listening){
            MyLogger.log(TAG, "阻塞侦听...");
            try {
                Socket socket = serverSocketInstence.accept();
                MyLogger.log(TAG, "接收来自IP:" + socket.getInetAddress().toString() + "，端口:" + socket.getPort() + "的连接");
                socketManger.addSocket(socket);
            } catch (IOException e) {
                MyLogger.log(TAG, "监听新连接出错");
                if (listening == true){
                    //TODO 重启
                }
                e.printStackTrace();
            } finally {
                if(listening == false){
                    // TODO 关闭清理
                }
            }
        }

        return true;
    }

    public SocketManger getSocketManger() {
        return socketManger;
    }

    public void setSocketManger(SocketManger socketManger) {
        if(socketManger != null){
            //TODO 抛异常
            return;
        }
        System.out.println(socketManger);
        this.socketManger = socketManger;
    }

    public boolean stopLitening(){
        //TODO 停止侦听，线程任务要不要执行完？
        return true;
    }

    public boolean stopAndremoveAllTask(){
        //TODO 先停止侦听，再废弃所有任务，所以要对所有的任务进行管理，不仅仅是管理socket
        return true;
    }

    public void clear(){
        socketManger.removeAndCloseAll();
    }


}
