package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import static GUI.MainStage.*;


public class AdditionsToSecondScene {
    static Label netgamelabel=new Label("Gra Sieciowa");
    static Label status = new Label("Status gry:");
    static Label statusinfo = new Label ("Status info");
    static HBox statuspanel = new HBox();

    //Napis o trybie gry
    public static void setNetgameLabel(){
        netgamelabel.setMinSize(590,20);
        netgamelabel.setStyle(whitebutton);
        netgamelabel.setStyle("-fx-font-family:FreeMono, monospace;-fx-text-fill: black;-fx-font-size: 35;");
        netgamelabel.setAlignment(Pos.CENTER);
        layout2.getChildren().add(netgamelabel);
        layout2.setSpacing(20);
    }

    //Część sceny z statusem gry
    public static void readGameState()
    {
        statuspanel.getChildren().addAll(status,statusinfo);
        status.setStyle("-fx-font-family:FreeMono, monospace;-fx-text-fill:black;-fx-font-size: 15;");
        statuspanel.setSpacing(5);
        border.setBottom(statuspanel);
        status.setAlignment(Pos.CENTER);
        statusinfo.setStyle("-fx-background-color: black;-fx-border-color: white;-fx-text-fill:white;-fx-font-family: FreeMono, monospace;-fx-font-size: 12");
        statusinfo.setAlignment(Pos.CENTER);
        statusinfo.setMinSize(250,20);
        statusinfo.setPadding(new Insets(5,5,5,5));
        statuspanel.setPadding(new Insets(20,20,20,20));

    }

    public static void setGameState()
    {
        statusinfo.setText("Status info");
    }

}
