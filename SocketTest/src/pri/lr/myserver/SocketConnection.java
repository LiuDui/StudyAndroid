package pri.lr.myserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// 这一层可以对serversocket透明，只让socketManger知道，其实作为一个内部类也可以
public class SocketConnection implements Runnable{
    // socket 一定要有，dataoutputStream 要有  ,dataInputSrtream要有，终止线程要有，清理资源要有
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    
    Thread thisThread;

    private boolean isCoonectTionKeepOn = false;


    public SocketConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }

    public void startCommunication(){
        thisThread = new Thread(this);
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
