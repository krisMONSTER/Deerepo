package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.*;

public class ConfirmBox {

    static boolean answer;

    public static Boolean display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        //blokuje mozliwosc interakcji z innymi oknami, dopoki nie zostanie obsluzone to konkretne okno
        window.setTitle(title);
        window.setMinWidth(250);
        window.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png")));
        Label label1 = new Label();
        label1.setText(message);

        // Dwa przyciski
        Button yesButton = new Button("Tak");
        Button noButton = new Button("Nie");

        yesButton.setOnAction(e -> {
            answer=true;
            window.close();
        } );
        noButton.setOnAction(e -> {
            answer=false;
            window.close();
        } );

        BorderPane border = new BorderPane();
        HBox layout = new HBox(50); // układa obiekty obok siebie
        border.setTop(label1);
        border.setMargin(label1, new Insets(10,10,20,10));
        layout.getChildren().addAll( yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        border.setBottom(layout);
        border.setPadding(new Insets(20,10,20,10));
        Scene scene = new Scene(border);
        window.setScene(scene);
        window.showAndWait();

        return answer;

    }
}
