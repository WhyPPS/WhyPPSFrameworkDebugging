package club.hsspace.whypps.debugging.tableModel;

import club.hsspace.whypps.handle.DataStream;
import club.hsspace.whypps.model.Certificate;
import javafx.beans.property.SimpleStringProperty;

/**
 * @ClassName: DataStreamModel
 * @CreateTime: 2022/6/8
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
public class DataStreamModel {

    private final SimpleStringProperty dsId;

    private final SimpleStringProperty localCer;

    private final SimpleStringProperty remoteCer;

    private final SimpleStringProperty tcpHandle;

    private final SimpleStringProperty mode;

    private final SimpleStringProperty alive;

    private static int idInc = 1;

    private final DataStream dataStream;

    public DataStream getDataStream() {
        return dataStream;
    }

    public DataStreamModel(DataStream dataStream, Certificate localCer, String tcpHandle, String mode) {
        this.dataStream = dataStream;

        dsId = new SimpleStringProperty(idInc++ + "");
        this.localCer = new SimpleStringProperty(localCer.getSign());
        remoteCer = new SimpleStringProperty(dataStream.getSrcCertificate().getSign());
        this.tcpHandle = new SimpleStringProperty(tcpHandle);
        this.mode = new SimpleStringProperty(mode);
        alive = new SimpleStringProperty(dataStream.alive()?"存活":"失效");

    }

    public String getDsId() {
        return dsId.get();
    }

    public SimpleStringProperty dsIdProperty() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId.set(dsId);
    }

    public String getLocalCer() {
        return localCer.get();
    }

    public SimpleStringProperty localCerProperty() {
        return localCer;
    }

    public void setLocalCer(String localCer) {
        this.localCer.set(localCer);
    }

    public String getRemoteCer() {
        return remoteCer.get();
    }

    public SimpleStringProperty remoteCerProperty() {
        return remoteCer;
    }

    public void setRemoteCer(String remoteCer) {
        this.remoteCer.set(remoteCer);
    }

    public String getTcpHandle() {
        return tcpHandle.get();
    }

    public SimpleStringProperty tcpHandleProperty() {
        return tcpHandle;
    }

    public void setTcpHandle(String tcpHandle) {
        this.tcpHandle.set(tcpHandle);
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
