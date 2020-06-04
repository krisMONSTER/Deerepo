package GUI;

import MutableVariables.MutableInteger;
import MutableVariables.MutableString;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnectionMenuClient {
    private static boolean isInt(String string){
        try{
            Integer.parseInt(string);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public static void display(MutableString address, MutableInteger port){
        Stage window = new Stage();
        window.setTitle("Próba połączenia");
        window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png")));

        Label addressLabel = new Label("Adres serwera:");
        TextField addressField = new TextField();
        addressField.setPrefWidth(160);
        addressField.setMaxWidth(160);

        Label portLabel = new Label("Port:");
        TextField portField = new TextField();
        portField.setPrefWidth(80);
        portField.setMaxWidth(80);

        Label portValidationInfo = new Label("");
        portValidationInfo.setTextFill(Color.web("#0076a3"));

        Button submit = new Button("Połącz");
        submit.setOnAction(actionEvent -> {
            String portInput = portField.getText();
            if(isInt(portInput)){
                address.set(addressField.getText());
                port.set(Integer.parseInt(portInput));
                window.close();
            }
            else {
                portValidationInfo.setText("Niepoprawny format portu");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(addressLabel,addressField,portLabel,portField,portValidationInfo,submit);
        layout.setAlignment(Pos.CENTER);
        StackPane outerLayout = new StackPane();
        outerLayout.getChildren().add(layout);
        Scene scene = new Scene(outerLayout,300,250);
        window.setScene(scene);
        window.showAndWait();
    }
}
