package club.hsspace.whypps.debugging.tableModel;


import club.hsspace.whypps.handle.TcpHandle;
import javafx.beans.property.SimpleStringProperty;

import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * @ClassName: TcpConnectModel
 * @CreateTime: 2022/6/7
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
public class TcpConnectModel {

    private final SimpleStringProperty id;

    private final SimpleStringProperty localPort;

    private final SimpleStringProperty remoteAddress;

    private final SimpleStringProperty remotePort;

    private final SimpleStringProperty time;

    private final SimpleStringProperty mode;

    private final SimpleStringProperty alive;

    private static int idInc = 1;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TcpHandle tcpHandle;

    public TcpHandle getTcpHandle() {
        return tcpHandle;
    }

    public TcpConnectModel(TcpHandle tcpHandle, String mode) {

        this.tcpHandle = tcpHandle;

        Socket socket = tcpHandle.getSocket();

        id = new SimpleStringProperty("" + idInc++);

        localPort = new SimpleStringProperty(socket.getLocalPort() + "");

        remoteAddress = new SimpleStringProperty(socket.getInetAddress().getHostAddress());

        remotePort = new SimpleStringProperty(socket.getPort() + "");

        time = new SimpleStringProperty(dateFormat.format(System.currentTimeMillis()));

        this.mode = new SimpleStringProperty(mode);

        alive = new SimpleStringProperty(tcpHandle.alive() ? "存活" : "失效");

    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getLocalPort() {
        return localPort.get();
    }

    public SimpleStringProperty localPortProperty() {
        return localPort;
    }

    public void setLocalPort(String localPort) {
        this.localPort.set(localPort);
    }

    public String getRemoteAddress() {
        return remoteAddress.get();
    }

    public SimpleStringProperty remoteAddressProperty() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress.set(remoteAddress);
    }

    public String getRemotePort() {
        return remotePort.get();
    }

    public SimpleStringProperty remotePortProperty() {
        return remotePort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort.set(remotePort);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getMode() {
        return mode.get();
    }

    public SimpleStringProperty modeProperty() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode.set(mode);
    }

    public String getAlive() {
        return alive.get();
    }

    public SimpleStringProperty aliveProperty() {
        return alive;
    }

    public void setAlive(String alive) {
        this.alive.set(alive);
    }
}
