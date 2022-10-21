package club.hsspace.whypps.debugging.controller;

import club.hsspace.whypps.handle.LongMsgStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: LongMsgTabController
 * @CreateTime: 2022/6/10
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
public class LongMsgTabController implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(LongMsgTabController.class);

    @FXML
    private TextField dataInput;

    @FXML
    private ToggleGroup dataReceiveMode;

    @FXML
    private ToggleGroup dataSendMode;

    @FXML
    private TextFlow dataShow;

    private LongMsgStream longMsgStream;

    @FXML
    void send(ActionEvent event) {
        String text = dataInput.getText() + "\n";
        if (text.trim().length() != 0) {
            longMsgStream.sendData(text.getBytes(StandardCharsets.UTF_8));
            dataShow.getChildren().add(new Text("<<<" + text));
        }
    }

    public void init(LongMsgStream longMsgStream) {
        this.longMsgStream = longMsgStream;
        new Thread(this).start();
    }

    @Override
    public void run() {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(longMsgStream.getInputStream()));
        String read;
        try {
            while ((read = inputStream.readLine()).length() != 0) {
                final String fRead = read;
                Platform.runLater(() -> dataShow.getChildren().add(new Text(">>>" + fRead + "\n")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
