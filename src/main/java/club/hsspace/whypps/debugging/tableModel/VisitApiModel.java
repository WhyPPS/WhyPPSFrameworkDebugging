package club.hsspace.whypps.debugging.tableModel;

import javafx.beans.property.SimpleStringProperty;

/**
 * @ClassName: VisitApiModel
 * @CreateTime: 2022/6/8
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
public class VisitApiModel {

    private final SimpleStringProperty dsid;

    private final SimpleStringProperty api;

    private final SimpleStringProperty type;

    private final SimpleStringProperty data;

    private final SimpleStringProperty bin;

    private final SimpleStringProperty code;

    private final SimpleStringProperty msg;

    private final SimpleStringProperty rData;

    private final SimpleStringProperty rBin;

    private final SimpleStringProperty alive;

    public VisitApiModel(String dsid, String api, String type, String data, String bin, String code, String msg, String rData, String rBin, String alive) {
        this.dsid = new SimpleStringProperty(dsid);
        this.api = new SimpleStringProperty(api);
        this.type = new SimpleStringProperty(type);
        this.data = new SimpleStringProperty(data);
        this.bin = new SimpleStringProperty(bin);
        this.code = new SimpleStringProperty(code);
        this.msg = new SimpleStringProperty(msg);
        this.rData = new SimpleStringProperty(rData);
        this.rBin = new SimpleStringProperty(rBin);
        this.alive = new SimpleStringProperty(alive);
    }

    public String getDsid() {
        return dsid.get();
    }

    public SimpleStringProperty dsidProperty() {
        return dsid;
    }

    public void setDsid(String dsid) {
        this.dsid.set(dsid);
    }

    public String getApi() {
        return api.get();
    }

    public SimpleStringProperty apiProperty() {
        return api;
    }

    public void setApi(String api) {
        this.api.set(api);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getData() {
        return data.get();
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public String getBin() {
        return bin.get();
    }

    public SimpleStringProperty binProperty() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin.set(bin);
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getMsg() {
        return msg.get();
    }

    public SimpleStringProperty msgProperty() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg.set(msg);
    }

    public String getrData() {
        return rData.get();
    }

    public SimpleStringProperty rDataProperty() {
        return rData;
    }

    public void setrData(String rData) {
        this.rData.set(rData);
    }

    public String getrBin() {
        return rBin.get();
    }

    public SimpleStringProperty rBinProperty() {
        return rBin;
    }

    public void setrBin(String rBin) {
        this.rBin.set(rBin);
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
