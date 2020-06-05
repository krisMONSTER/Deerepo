package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import static GUI.MainStage.*;

public class AdditionsToSecondScene {
    static Label netgamelabel=new Label("Gra Sieciowa");
    static Label offlinegamelabel=new Label("Gra Offline");

    public static void setNetgameLabel(){
        netgamelabel.setMinSize(450,20);
        netgamelabel.setStyle(whitebutton);
        netgamelabel.setStyle(fontstyle);
        netgamelabel.setAlignment(Pos.CENTER);
        layout2.getChildren().add(netgamelabel);
        layout2.setSpacing(75);
    }

    public static void setOfflineGameLabel(){
        offlinegamelabel.setMinSize(450,20);
        offlinegamelabel.setStyle(whitebutton);
        offlinegamelabel.setStyle(fontstyle);
        offlinegamelabel.setAlignment(Pos.CENTER);
        layout2.getChildren().add(offlinegamelabel);
        layout2.setSpacing(75);
    }

    public static void readGameState()
    {

    }




}
