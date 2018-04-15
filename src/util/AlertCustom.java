/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Luxury
 */
public class AlertCustom {

    /**
     * @see EX: Optional<ButtonType> result = setAlertConf("Thông báo", "Bạn có
     * muốn xóa không"); if (result.get() == ButtonType.OK) { Something; }
     * @param header
     * @param content
     * @return Optional<ButtonType>
     *
     */
    public static Optional<ButtonType> setAlertConf(String content,
            String header) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                content, ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(header);
        alert.setTitle("Thông báo");
        return alert.showAndWait();
    }

    public static void setAlertInfo(String thongbao, String header,
            String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(thongbao);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
