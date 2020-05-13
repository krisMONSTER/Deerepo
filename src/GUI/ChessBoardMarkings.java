package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

import static GUI.MainStage.gridPane;
import static GUI.MainStage.markings;

public class ChessBoardMarkings {

    public static final String MARKINGS_COLOR="#6c5545";

    public static void Add_Fields_Markings()
    {

        for(int i=0; i<8;i++) {
            markings.add(newRowLabel(i),0, i + 1, 1, 1);
            markings.add(newRowLabel(i),9, i + 1, 1, 1);
            markings.add(newColLabel(i),i+1, 0, 1, 1);
            markings.add(newColLabel(i),i+1, 9, 1, 1);
        }

    }

    private static Label newRowLabel(int i) {
        Label l = new Label((8-i) + "");
        l.setStyle("-fx-background-color:" + MARKINGS_COLOR +";-fx-padding: 0;-fx-border-color: black;-fx-text-fill: black");
        l.setMinSize(20, 50);
        l.setAlignment(Pos.CENTER);
        return l;
    }

    private static Label newColLabel(int i) {
        Label l = new Label((char) (i + 65) + "");
        l.setStyle("-fx-background-color:" + MARKINGS_COLOR +"; -fx-padding: 0;-fx-border-color: black;-fx-text-fill: black");
        l.setMinSize(50, 20);
        l.setAlignment(Pos.CENTER);
        return l;
    }
}
