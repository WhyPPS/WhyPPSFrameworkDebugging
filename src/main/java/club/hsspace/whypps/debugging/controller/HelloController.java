package club.hsspace.whypps.debugging.controller;

import club.hsspace.whypps.action.Init;
import club.hsspace.whypps.action.Injection;
import club.hsspace.whypps.debugging.tableModel.*;
import club.hsspace.whypps.debugging.util.AlertUtil;
import club.hsspace.whypps.framework.app.MethodController;
import club.hsspace.whypps.framework.run.WhyPPSFramework;
import club.hsspace.whypps.handle.DataStream;
import club.hsspace.whypps.handle.EquityHandle;
import club.hsspace.whypps.handle.SpreadHandle;
import club.hsspace.whypps.handle.TcpHandle;
import club.hsspace.whypps.manage.Authentication;
import club.hsspace.whypps.manage.ContainerManage;
import club.hsspace.whypps.manage.FrameManage;
import club.hsspace.whypps.manage.LocalHost;
import club.hsspace.whypps.model.Certificate;
import club.hsspace.whypps.model.senior.BinR;
import club.hsspace.whypps.model.senior.Code;
import club.hsspace.whypps.model.senior.LongR;
import club.hsspace.whypps.util.NumberTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HelloController {


    @FXML
    private TextField inputDSID;

    @FXML
    private TextField inputDSSign;

    @FXML
    private TableView<DataStreamModel> tableDataStream;

    @FXML
    private TextField inputApplyCer;

    @FXML
    private TextField inputApplyMsg;

    @FXML
    private TextField inputApplyTcpID;

    @FXML
    private TextField inputIpPort;

    @FXML
    private TextField inputVisitApi;

    @FXML
    private TextArea inputVisitBin;

    @FXML
    private TextField inputVisitDSID;

    @FXML
    private TextField inputRadioAPI;

    @FXML
    private TextField inputRadioBlackList;

    @FXML
    private TextField inputRadioCer;

    @FXML
    private TextArea inputRadioData;

    @FXML
    private TextField inputRadioList;

    @FXML
    private TextField inputRadioRange;

    @FXML
    private TextField inputSwapAPI;

    @FXML
    private TextField inputSwapCer;

    @FXML
    private TextArea inputSwapData;

    @FXML
    private TextField inputSwapRange;

    @FXML
    private TextField inputSwapValid;

    @FXML
    private TextField inputSwapWait;

    @FXML
    private TextArea inputVisitData;

    @FXML
    private ToggleGroup radioVisitApi;

    @FXML
    private TextFlow systemLog;

    @FXML
    private TableView<CertificateModel> tableCertificate;

    @FXML
    private TableView<TcpConnectModel> tableTcpConnect;

    @FXML
    private TableView<ApiInterfaceModel> tableApiInterface;

    @FXML
    private TableView<VisitApiModel> tableVisitApi;

    @FXML
    private TabPane logTabPane;

    @FXML
    void initialize() throws Exception {

        //系统输出流、错误流重定向
        systemOutReset();

        //TCP链接控制区
        tableCertificate.setRowFactory(tv -> {
            TableRow<CertificateModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CertificateModel certificateModel = row.getItem();
                    inputApplyCer.setText(certificateModel.getSign());
                    inputDSSign.setText(certificateModel.getSign());
                    inputRadioCer.setText(certificateModel.getSign());
                    inputSwapCer.setText(certificateModel.getSign());
                }
            });
            return row;
        });

        tableTcpConnect.setRowFactory(tv -> {
            TableRow<TcpConnectModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TcpConnectModel tcpConnectModel = row.getItem();
                    inputApplyTcpID.setText(tcpConnectModel.getId());
                    inputDSID.setText(tcpConnectModel.getId());
                }
            });
            return row;
        });

        tableDataStream.setRowFactory(tv -> {
            TableRow<DataStreamModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    DataStreamModel dataStreamModel = row.getItem();
                    inputVisitDSID.setText(dataStreamModel.getDsId());
                }
            });
            return row;
        });

    }

    @Injection
    private LocalHost localHost;

    @Injection
    private Authentication authentication;

    //TCP链接按钮
    @FXML
    void buttonTcpConnect(ActionEvent event) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}";
        String text = inputIpPort.getText();
        if (text.matches(regex)) {
            String[] split = text.split(":");

            try {
                TcpHandle connect = localHost.connect(split[0], Integer.parseInt(split[1]));

                AlertUtil.INFORM("连接成功");

                modelTcpConnectData.add(new TcpConnectModel(connect, "主动连接"));

                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AlertUtil.ERROR("输入不合法，请输入格式为\"192.168.0.1:2683\"");
    }


    @FXML
    void buttonApplyCer(ActionEvent event) throws UnknownHostException {
        String cer = inputApplyCer.getText();
        String msg = inputApplyMsg.getText();
        String id = inputApplyTcpID.getText();
        TcpHandle tcpHandle = modelTcpConnectData.stream().filter(n -> n.getId().equals(id)).findFirst().get().getTcpHandle();
        if (tcpHandle != null) {
            Certificate aCer = authentication.getCertificate(cer);
            if (aCer != null) {
                Certificate ce = tcpHandle.applyCertificate(aCer, msg.getBytes(StandardCharsets.UTF_8));
                if (ce != null) {
                    modelCertificateData.add(new CertificateModel(authentication, ce));
                    AlertUtil.INFORM("申请成功");
                } else {
                    AlertUtil.ERROR("申请失败");
                }
            } else {
                AlertUtil.ERROR("权威证书不存在");
            }
        } else {
            AlertUtil.ERROR("TCP链接不存在");
        }
    }

    @FXML
    void buttonDataStream(ActionEvent event) {
        String id = inputDSID.getText();
        String cer = inputDSSign.getText();
        TcpHandle tcpHandle = modelTcpConnectData.stream().filter(n -> n.getId().equals(id)).findFirst().get().getTcpHandle();
        if (tcpHandle != null) {
            Certificate certificate = authentication.getCertificate(cer);
            if (certificate != null) {
                DataStream ds = tcpHandle.buildConnection(certificate);
                if (ds != null) {
                    modelDataStreamData.add(new DataStreamModel(ds, authentication.getLocalCertificate(), id, "主动连接"));
                    AlertUtil.INFORM("申请成功");
                } else {
                    AlertUtil.ERROR("申请失败");
                }
            } else {
                AlertUtil.ERROR("远端证书不存在");
            }
        } else {
            AlertUtil.ERROR("TCP链接不存在");
        }
    }

    final ObservableList<CertificateModel> modelCertificateData = FXCollections.observableArrayList();

    @Init
    void initTableCertificate() throws UnknownHostException {
        ObservableList<TableColumn<CertificateModel, ?>> columns = tableCertificate.getColumns();
        String[] columnsNames = {"sign", "start", "end", "ipv4", "ipv6", "trust", "local", "hasPrivateKey"};
        for (int i = 0; i < columns.size(); i++) {
            TableColumn certificateModelTableColumn = columns.get(i);
            certificateModelTableColumn.setCellValueFactory(new PropertyValueFactory<CertificateModel, String>(columnsNames[i]));
        }

        for (Certificate certificate : authentication.getCertificates()) {
            CertificateModel certificateModel = new CertificateModel(authentication, certificate);
            modelCertificateData.add(certificateModel);
        }

        tableCertificate.setItems(modelCertificateData);
    }

    final ObservableList<TcpConnectModel> modelTcpConnectData = FXCollections.observableArrayList();

    @Init
    void initTableTcpConnect() {
        ObservableList<TableColumn<TcpConnectModel, ?>> columns = tableTcpConnect.getColumns();
        String[] columnsNames = {"id", "localPort", "remoteAddress", "remotePort", "time", "mode", "alive"};
        for (int i = 0; i < columns.size(); i++) {
            TableColumn certificateModelTableColumn = columns.get(i);
            certificateModelTableColumn.setCellValueFactory(new PropertyValueFactory<TcpConnectModel, String>(columnsNames[i]));
        }

        tableTcpConnect.setItems(modelTcpConnectData);
    }

    final ObservableList<DataStreamModel> modelDataStreamData = FXCollections.observableArrayList();

    @Init
    void initTableDataStream() {
        ObservableList<TableColumn<DataStreamModel, ?>> columns = tableDataStream.getColumns();
        String[] columnsNames = {"dsId", "localCer", "remoteCer", "tcpHandle", "mode", "alive"};
        for (int i = 0; i < columns.size(); i++) {
            TableColumn dataStreamModelTableColumn = columns.get(i);
            dataStreamModelTableColumn.setCellValueFactory(new PropertyValueFactory<DataStreamModel, String>(columnsNames[i]));
        }

        tableDataStream.setItems(modelDataStreamData);
    }


    final ObservableList<ApiInterfaceModel> modelApiInterfaceData = FXCollections.observableArrayList();

    /**
     * 监听接口表格初始化
     */
    @Init
    void initTableApiInterface(MethodController methodController) throws NoSuchFieldException, IllegalAccessException {
        ObservableList<TableColumn<ApiInterfaceModel, ?>> columns = tableApiInterface.getColumns();
        String[] columnsNames = {"api", "type", "clazz", "method", "mode"};
        for (int i = 0; i < columns.size(); i++) {
            TableColumn apiInterfaceModelTableColumn = columns.get(i);
            apiInterfaceModelTableColumn.setCellValueFactory(new PropertyValueFactory<ApiInterfaceModel, String>(columnsNames[i]));
        }

        /** !!!!!! Framework官方API不提供读取方法，也不提供修改方法，
         * 由于该应用特殊性，以下通过反射破坏封装性完成，不建议其他软件这样实现 !!!!!! */
        Class<MethodController> mcc = MethodController.class;
        Field dataMsgMap = mcc.getDeclaredField("dataMsgMap");
        dataMsgMap.setAccessible(true);
        Map<String, Method> dataMsg = (Map<String, Method>) dataMsgMap.get(methodController);

        Field binMsgMap = mcc.getDeclaredField("binMsgMap");
        binMsgMap.setAccessible(true);
        Map<String, Method> binMsg = (Map<String, Method>) binMsgMap.get(methodController);

        Field longMsgMap = mcc.getDeclaredField("longMsgMap");
        longMsgMap.setAccessible(true);
        Map<String, Method> longMsg = (Map<String, Method>) longMsgMap.get(methodController);

        Field runObject = mcc.getDeclaredField("runObject");
        runObject.setAccessible(true);
        Map<Method, Object> runMap = (Map<Method, Object>) runObject.get(methodController);

        dataMsg.keySet().stream()
                .filter(n -> runMap.get(dataMsg.get(n)) != null)
                .map(n -> new ApiInterfaceModel(n, "DATA", dataMsg.get(n), "原始载入"))
                .forEach(modelApiInterfaceData::add);
        binMsg.keySet().stream()
                .filter(n -> runMap.get(dataMsg.get(n)) != null)
                .map(n -> new ApiInterfaceModel(n, "BIN", dataMsg.get(n), "原始载入"))
                .forEach(modelApiInterfaceData::add);
        longMsg.keySet().stream()
                .filter(n -> runMap.get(dataMsg.get(n)) != null)
                .map(n -> new ApiInterfaceModel(n, "LONG", dataMsg.get(n), "原始载入"))
                .forEach(modelApiInterfaceData::add);

        tableApiInterface.setItems(modelApiInterfaceData);
    }

    final ObservableList<VisitApiModel> modelVisitApiData = FXCollections.observableArrayList();

    @Init
    void initTableVisitApi() {
        ObservableList<TableColumn<VisitApiModel, ?>> columns = tableVisitApi.getColumns();
        String[] columnsNames = {"dsid", "api", "type", "data", "bin", "code", "msg", "rData", "rBin", "alive"};
        for (int i = 0; i < columns.size(); i++) {
            TableColumn visitApiModelTableColumn = columns.get(i);
            visitApiModelTableColumn.setCellValueFactory(new PropertyValueFactory<VisitApiModel, String>(columnsNames[i]));
        }

        tableVisitApi.setItems(modelVisitApiData);
    }


    @FXML
    void buttonVisitApi(ActionEvent event) {
        String select = ((RadioButton) radioVisitApi.getSelectedToggle()).getText();
        String dsid = inputVisitDSID.getText();
        String api = inputVisitApi.getText();
        String data = inputVisitData.getText();
        String bin = inputVisitBin.getText();

        DataStream dataStream = modelDataStreamData.stream().filter(n -> n.getDsId().equals(dsid)).findFirst().get().getDataStream();
        if (dataStream == null) {
            AlertUtil.ERROR("DS数据流不存在");
            return;
        }

        JSONObject jo;
        try {
            jo = JSONObject.parseObject(data);
        } catch (Exception e) {
            AlertUtil.ERROR("数据区格式错误");
            return;
        }

        if (!bin.matches("[\\da-eA-E]*")) {
            AlertUtil.ERROR("二进制区格式错误");
            return;
        }

        EquityHandle eh = dataStream.getEquityHandle();
        if ("数据".equals(select)) {
            eh.sendData(dataR -> {
                VisitApiModel visitApiModel = new VisitApiModel(dsid, api, "DATA", data, "", dataR.code.code() + "", dataR.code.msg(), String.valueOf(dataR.data), "", "");
                modelVisitApiData.add(visitApiModel);
            }, api, jo);
            AlertUtil.INFORM("数据已发送");
        }

        if ("二进制".equals(select)) {
            byte[] sendBin = NumberTools.hexString2Bytes(bin);
            eh.sendBin(dataLink -> {
                BinR binR = dataLink.getData();
                VisitApiModel visitApiModel = new VisitApiModel(dsid, api, "BIN", data, bin, binR.code.code() + "", binR.code.msg(), String.valueOf(binR.data), NumberTools.bytes2HexString(dataLink.getExtraData()), "");
                modelVisitApiData.add(visitApiModel);
            }, api, jo, sendBin);
            AlertUtil.INFORM("数据已发送");
        }

        if ("长链路".equals(select)) {
            eh.sendLong(op -> {
                LongR longR = op.key();
                VisitApiModel visitApiModel = new VisitApiModel(dsid, api, "DATA", data, "", longR.code.code() + "", longR.code.msg(), String.valueOf(longR.data), "", "");
                modelVisitApiData.add(visitApiModel);

                Platform.runLater(() -> {
                    FXMLLoader fxmlLoader = new FXMLLoader(LongMsgTabController.class.getResource("LongMsgTab.fxml"));
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                    try {
                        BorderPane borderPane = fxmlLoader.load();
                        Tab tab = new Tab(longR.requestId);
                        tab.setContent(borderPane);
                        logTabPane.getTabs().add(tab);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    LongMsgTabController controller = fxmlLoader.getController();
                    controller.init(op.value());
                });

            }, api, jo, 30000);
            AlertUtil.INFORM("数据已发送");
        }

    }

    @Injection
    private FrameManage frameManage;

    @FXML
    void buttonRadio(ActionEvent event) {
        String api = inputRadioAPI.getText();
        int range = Integer.parseInt(inputRadioRange.getText());
        boolean blackList = Boolean.parseBoolean(inputRadioBlackList.getText());
        String[] list = inputRadioList.getText().split("\\|");

        JSONObject data;
        try {
            data = JSON.parseObject(inputRadioData.getText());
        } catch (Exception e) {
            AlertUtil.ERROR("数据区格式错误");
            return;
        }

        DataStream dataStream = frameManage.getDataStream(inputRadioCer.getText());
        if (dataStream == null) {
            AlertUtil.ERROR("DS链接不存在");
            return;
        }

        SpreadHandle spreadHandle = dataStream.getSpreadHandle();
        boolean send = spreadHandle.sendRadio(api, data, blackList, list, range);

        if (send) {
            modelVisitApiData.add(new VisitApiModel("-", api, "RADIO", "" + data, "-", "", "", "-", "-", ""));
        }

    }

    @FXML
    void buttonSwap(ActionEvent event) {
        String api = inputSwapAPI.getText();
        int range = Integer.parseInt(inputSwapRange.getText());
        boolean wait = Boolean.parseBoolean(inputSwapWait.getText());
        int valid = Integer.parseInt(inputSwapValid.getText());

        JSONObject data;
        try {
            data = JSON.parseObject(inputRadioData.getText());
        } catch (Exception e) {
            AlertUtil.ERROR("数据区格式错误");
            return;
        }

        DataStream dataStream = frameManage.getDataStream(inputRadioCer.getText());
        if (dataStream == null) {
            AlertUtil.ERROR("DS链接不存在");
            return;
        }

        SpreadHandle spreadHandle = dataStream.getSpreadHandle();
        spreadHandle.sendSwap(swapR -> modelVisitApiData.add(new VisitApiModel("-", api, "SWAP", "" + data, "", swapR.code.code() + "", swapR.code.msg(), swapR.data.toJSONString(), "", "")), api, data, 1, range, wait, valid);

    }

    private void systemOutReset() throws IOException {

        PrintStream systemOut = System.out;

        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();

        pos.connect(pis);

        BufferedReader br = new BufferedReader(new InputStreamReader(pis));

        PrintStream ps = new PrintStream(pos);
        System.setOut(ps);
        System.setErr(ps);

        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String s;
                    if ((s = br.readLine()) != null) {
                        systemOut.println(s);
                        Platform.runLater(() -> {
                            systemLog.getChildren().add(new Text(s + "\n"));
                        });
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setName("out");
        thread.start();
    }

}
