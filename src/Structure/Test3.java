package Structure;

import GUI.ConnectionMenuClient;
import MutableVariables.MutableInteger;
import MutableVariables.MutableString;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class Test3 extends Application implements EventHandler<ActionEvent> {
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        stage.setTitle("TEST");
        button = new Button();
        button.setText("tescik");
        button.setOnAction(this);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(button);

        Scene scene = new Scene(stackPane,300,300);
        stage.setScene(scene);
        stage.show();
        MutableInteger a = new MutableInteger();
        MutableString b = new MutableString();
        ConnectionMenuClient.display(b,a);
    }

    public void handle(ActionEvent actionEvent) {
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("aaaa");
    }
}
