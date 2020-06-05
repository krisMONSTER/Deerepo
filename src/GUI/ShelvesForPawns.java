package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import static GUI.BoardMarkings.BROWN_FIELD;
import static GUI.BoardMarkings.MARKINGS_COLOR;
import static GUI.MainStage.border;
import static GUI.MainStage.whitebutton;

public class ShelvesForPawns {

    static HBox leftshelf = new HBox(50);
    static HBox rightshelf = new HBox(50);
    static VBox [] vleftshelf = new VBox[2];
    static VBox [] vrightshelf = new VBox[2];
    static Label [] lleftshelf = new Label [15];
    static Label [] lrightshelf = new Label [15];
    static int whitecounter=0;
    static int blackcounter=0;
   /* static Label blackpawns1=new Label("  Figury");
    static Label blackpawns2=new Label("dla czarnych");
    static Label whitepawns1=new Label("  Figury");
    static Label whitepawns2=new Label("dla białych"); */


    public static void addShelves()
    {
        leftshelf.setSpacing(0);
        rightshelf.setSpacing(0);
        BorderPane.setMargin(leftshelf,new Insets(20,25,20,25));
        BorderPane.setMargin(rightshelf,new Insets(20,25,20,25));


        for(int i=0;i<2;i++){
            vleftshelf[i] = new VBox();
            leftshelf.getChildren().add(vleftshelf[i]);
            vrightshelf[i] = new VBox();
            rightshelf.getChildren().add(vrightshelf[i]);
        }


       /* vleftshelf[0].getChildren().add(blackpawns1);
        vleftshelf[1].getChildren().add(blackpawns2);
        vrightshelf[0].getChildren().add(whitepawns1);
        vrightshelf[1].getChildren().add(whitepawns2); */


       for(int i=0;i<15;i++){
            lleftshelf[i]=new Label();
            lleftshelf[i].setMinSize(50,50);
            lleftshelf[i].setStyle(BROWN_FIELD);

            lrightshelf[i]=new Label();
            lrightshelf[i].setMinSize(50,50);
            lrightshelf[i].setStyle(BROWN_FIELD);


           if(i==14){
               vleftshelf[0].getChildren().add(lleftshelf[i]);
               vrightshelf[1].getChildren().add(lrightshelf[i]);
           }
           else {
               vleftshelf[i%2].getChildren().add(lleftshelf[i]);
               vrightshelf[i%2].getChildren().add(lrightshelf[i]);
           }
        }

        border.setLeft(leftshelf);
        border.setRight(rightshelf);

    }

    public static void resetShelves(){
        whitecounter=0;
        whitecounter=0;

        for(int i=0;i<15;i++){
            lleftshelf[i].setGraphic(null);
            lrightshelf[i].setGraphic(null);
        }
    }

    public static void addWhitePawn(ImageView whitepawn)
    {
        lleftshelf[whitecounter].setGraphic(whitepawn);
        whitecounter++;
    }

    public static void addBlackPawn(ImageView blackpawn)
    {
        lrightshelf[blackcounter].setGraphic(blackpawn);
        blackcounter++;
    }
}
