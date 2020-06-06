package GUI;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message){

        Stage alertstage;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.initModality(Modality.APPLICATION_MODAL);
        //blokuje mozliwosc interakcji z innymi oknami, dopoki nie zostanie obsluzone to konkretne okno
        alertstage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertstage.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png")));
        alert.showAndWait();

    }
}
