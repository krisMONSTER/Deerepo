package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


import static GUI.MainStage.*;

public class AdditionsToSecondScene {
    static Label netgamelabel=new Label("Gra Sieciowa");
    static Label offlinegamelabel=new Label("Gra Offline");
    static Label status = new Label("Status gry:");
    static Label statusinfo = new Label ("Status info");
    static HBox statuspanel = new HBox();

    public static void setNetgameLabel(){
        netgamelabel.setMinSize(570,20);
        netgamelabel.setStyle(whitebutton);
        netgamelabel.setStyle(fontstyle);
        netgamelabel.setAlignment(Pos.CENTER);
        layout2.getChildren().add(netgamelabel);
        layout2.setSpacing(20);
    }

    public static void setOfflineGameLabel(){
        offlinegamelabel.setMinSize(570,20);
        offlinegamelabel.setStyle(fontstyle);
        offlinegamelabel.setAlignment(Pos.CENTER);
        layout2.getChildren().add(offlinegamelabel);
        layout2.setSpacing(20);
    }

    public static void readGameState()
    {
        statuspanel.getChildren().addAll(status,statusinfo);
        statuspanel.setSpacing(5);
        border.setBottom(statuspanel);
        status.setAlignment(Pos.CENTER);
        statusinfo.setStyle(blackbutton);
        statusinfo.setAlignment(Pos.CENTER);
        statusinfo.setMinSize(250,20);
        statusinfo.setPadding(new Insets(5,5,5,5));
        statuspanel.setPadding(new Insets(20,20,20,20));

    }




}
