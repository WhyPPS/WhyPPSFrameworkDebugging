package club.hsspace.whypps.debugging;

import club.hsspace.whypps.debugging.controller.HelloController;
import club.hsspace.whypps.debugging.controller.LongMsgTabController;
import club.hsspace.whypps.framework.run.WhyPPSFramework;
import club.hsspace.whypps.manage.ContainerManage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class HelloApplication extends Application {

    public static ContainerManage containerManage = null;

    @Override
    public void start(Stage stage) throws IOException, InvocationTargetException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        HelloController controller = fxmlLoader.getController();
        containerManage.injection(controller);

        stage.setTitle("WhyPPSFrameworkDebugging - WhyPPS框架调试器");
        stage.setScene(scene);
        stage.show();
    }

    public static void run(ContainerManage containerManage) {
        HelloApplication.containerManage = containerManage;
        launch();
    }
}