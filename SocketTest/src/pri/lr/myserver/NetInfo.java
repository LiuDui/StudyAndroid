package pri.lr.myserver;

import java.net.Socket;
import java.util.Objects;

public class NetInfo {
    private String IP;
    private int port;

    public NetInfo(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public static NetInfo parseNetInfo(Socket socket){
        //TODO
        return new NetInfo(null, 0);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetInfo)) return false;
        NetInfo netInfo = (NetInfo) o;
        return getPort() == netInfo.getPort() &&
                Objects.equals(getIP(), netInfo.getIP());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIP(), getPort());
    }
}
