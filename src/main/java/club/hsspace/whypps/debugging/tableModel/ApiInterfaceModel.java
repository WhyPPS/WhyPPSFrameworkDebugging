package club.hsspace.whypps.debugging.tableModel;

import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Method;

/**
 * @ClassName: ApiInterfaceModel
 * @CreateTime: 2022/6/8
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
public class ApiInterfaceModel {

    private final SimpleStringProperty api;

    private final SimpleStringProperty type;

    private final SimpleStringProperty clazz;

    private final SimpleStringProperty method;

    private final SimpleStringProperty mode;

    public ApiInterfaceModel(String api, String type, Method method, String mode) {

        this.api = new SimpleStringProperty(api);

        this.type = new SimpleStringProperty(type);

        this.clazz = new SimpleStringProperty(method.getDeclaringClass().getName());

        this.method = new SimpleStringProperty(method.getName());

        this.mode = new SimpleStringProperty(mode);

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

    public String getClazz() {
        return clazz.get();
    }

    public SimpleStringProperty clazzProperty() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz.set(clazz);
    }

    public String getMethod() {
        return method.get();
    }

    public SimpleStringProperty methodProperty() {
        return method;
    }

    public void setMethod(String method) {
        this.method.set(method);
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
}
