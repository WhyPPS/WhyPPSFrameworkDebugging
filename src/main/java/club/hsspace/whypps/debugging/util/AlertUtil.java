package club.hsspace.whypps.debugging.util;

import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: AlertUtil
 * @CreateTime: 2022/6/8
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
public class AlertUtil {

    public static final void ERROR(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public static final void INFORM(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public static final void WARNING(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

}
