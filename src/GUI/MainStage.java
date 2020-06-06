package GUI;

import MutableVariables.MutableBoolean;
import MutableVariables.MutableInteger;
import MutableVariables.MutableString;
import Structure.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;


public class MainStage extends Application{

    static Stage window;
    static Scene scene1, scene2;

    //Rzeczy do okienka z plansza
    public static BorderPane border = new BorderPane(); //Okno z plansza
    public static GridPane gridPane =new GridPane();
    public static GridPane markings = new GridPane();
    public static Label[][] board = new Label[8][8];
    public static HBox layout2;

    //Obrazek dla okna glownego
    public static BackgroundImage scene1_background = new BackgroundImage(new Image(MainStage.class.getResourceAsStream("img/scene1_background.png")),BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(400, 400, false, false, false, false));
    //Tło dla sceny 2
    public static BackgroundFill scene2_background = new BackgroundFill(Color.web("#C5C6C7"),
            CornerRadii.EMPTY, Insets.EMPTY);
    //#9bb6b8
    //#c1bca0

    //Do mechanizmu
    private ArrayBlockingQueue<int[]> clickCommand;
    private ArrayBlockingQueue<ToDisplay> display;
    private ArrayBlockingQueue<GameState> gameState;
    private Move move;
    private Semaphore clickSemaphore;
    private MutableBoolean activeThread;
    private StructureTask structureTask;

    //CSS dla przyciskow
    static String whitebutton="-fx-background-color: white;-fx-border-color: black;-fx-font-family: FreeMono, monospace;";
    static String blackbutton="-fx-background-color: black;-fx-border-color: white;-fx-text-fill:white;-fx-font-family: FreeMono, monospace;";
    static String fontstyle="-fx-font-family:FreeMono, monospace;-fx-font-size: 30;";


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage){

        window=primaryStage;
        Label label1 = new Label("Zagrajmy w szachy!");
        label1.setStyle(fontstyle);
        window.setTitle("Chess_Project");
        window.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png")));
        window.setResizable(false);

        Button [] firstSceneButton = new Button[6];
        Button [] secondSceneButton = new Button[2];


        //Button Rozpoczynanie gry
        firstSceneButton[0]=new Button("Rozpocznij gre");
        firstSceneButton[0].setOnAction(e-> {
            window.setScene(scene2);
            initExchangeTools();
            structureTask = new StructureTaskOffline(clickCommand, display, gameState, clickSemaphore, activeThread);
            BoardInitialization.BlankSpace(8);
            BoardInitialization.InitChessBoard();
            AdditionsToSecondScene.netgamelabel.setText("Gra Offline");
            //move.execute_move(t,clickSemaphore);
            structureTask.start();  //tu tak zrobilem bo executeMove chyba bedziemy wykorzystywac do innych typow watkow (host, klient)
            move.executeMove(clickSemaphore,clickCommand);
            move.addCheckStateHandler();
            move.addProcessHandler();
        });

        //Button samouczek
        firstSceneButton[1]=new Button("Samouczek");
        firstSceneButton[1].setOnAction(e -> Help.display("black"));

        //Button AlertBox test
        firstSceneButton[2]=new Button("Czy można oszukiwać w tej grze?");
        firstSceneButton[2].setOnAction(e -> AlertBox.display("Oszukiwanie","Nie można oszukiwać!"));


        //Button Zamykanie programu
        firstSceneButton[3]=new Button( "Opuść grę");
        firstSceneButton[3].setOnAction(e -> closeProgram());
        window.setOnCloseRequest(e->
        {
            e.consume(); // sama metoda setOnCloseRequest zamyka program niezależnie jakiej odpowiedzi użytkownik udzieli
            closeProgram(); // uzywajac e.consume() przejmujemy kontrole nad tym co sie stanie np. uzywajac okreslonej metody
        });

        //Button Sieci - hostuj gre
        firstSceneButton[4] = new Button("Hostuj grę");
        firstSceneButton[4].setOnAction(e -> {
            MutableInteger port = new MutableInteger();
            ConnectionMenuHost.display(port);
            if(port.isSet()){
                window.setScene(scene2);
                initExchangeTools();
                structureTask = new StructureTaskHost(port.getAnInt(), clickCommand, display, gameState, clickSemaphore, activeThread);
                BoardInitialization.BlankSpace(8);
                BoardInitialization.InitChessBoard();
                AdditionsToSecondScene.netgamelabel.setText("Gra Sieciowa");
                structureTask.start();
                move.executeMove(clickSemaphore,clickCommand);
                move.addCheckStateHandler();
                move.addProcessHandler();
            }
        });

        //Button Sieci - dolacz do gry
        firstSceneButton[5]= new Button("Dołącz do gry");
        firstSceneButton[5].setOnAction(e -> {
            MutableString address = new MutableString();
            MutableInteger port = new MutableInteger();
            ConnectionMenuClient.display(address,port);
            if(address.isSet()&&port.isSet()){
                window.setScene(scene2);
                initExchangeTools();
                structureTask = new StructureTaskClient(address.getString(), port.getAnInt(), clickCommand, display, gameState, clickSemaphore, activeThread);
                BoardInitialization.BlankSpace(8);
                BoardInitialization.InitChessBoard();
                AdditionsToSecondScene.netgamelabel.setText("Gra Sieciowa");
                structureTask.start();
                move.executeMove(clickSemaphore,clickCommand);
                move.addCheckStateHandler();
                move.addProcessHandler();
            }
        });



        //Dekorowanie buttonow
        for(int i=0; i<firstSceneButton.length;i++) {
            if (i % 2 == 0) firstSceneButton[i].setStyle(whitebutton);
            else firstSceneButton[i].setStyle(blackbutton);
        }


        //Layout 1 - scena okna glownego
        VBox layout1 = new VBox(20); // układa obiekty "w kolumnie"
        layout1.getChildren().add(label1);

        for(int i=0; i<firstSceneButton.length;i++)
        { layout1.getChildren().add(firstSceneButton[i]); }


        layout1.setAlignment(Pos.CENTER);
        layout1.setBackground(new Background(scene1_background));
        scene1 = new Scene(layout1, 400,400);


        //Button2 Przycisk powrotu do glownego menu
        secondSceneButton[0]=new Button("Menu");
        secondSceneButton[0].setOnAction(e -> {
            boolean choice;
            choice=ConfirmBox.display("Powrot do menu","Powrót do menu oznacza przerwanie gry i \nwygraną przeciwnika.\nCzy potwierdzasz swój wybór?");
            if(choice==true) {
                activeThread.set(false);
                structureTask.interrupt();
                try {
                    structureTask.join();
                }catch (InterruptedException exception){
                    exception.printStackTrace();
                }
                window.setScene(scene1);
                ShelvesForPawns.resetShelves();
            }
        });


        //Button5 Przycisk do restartu szachownicy
        secondSceneButton[1]=new Button("Restart");
        secondSceneButton[1].setOnAction(e->
        {
            BoardInitialization.BlankSpace(8);
            BoardInitialization.InitChessBoard();
            BoardMarkings.Add_Fields_Markings();
            window.setScene(scene2);
        });

        for(int i=0; i<secondSceneButton.length;i++) {
            if (i % 2 == 0) secondSceneButton[i].setStyle(whitebutton);
            else secondSceneButton[i].setStyle(blackbutton);
        }

        //Layout 2 tam gdzie jest plansza
        layout2=new HBox(595);
        layout2.getChildren().addAll(secondSceneButton[0]);
        secondSceneButton[0].setAlignment(Pos.BASELINE_LEFT);
        layout2.setSpacing(0);
        BorderPane.setMargin(layout2,new Insets(0,25,0,25));
        border.setTop(layout2);


        //Plansza
        BoardInitialization.BlankSpace(8);
        BoardInitialization.InitChessBoard();
        BoardMarkings.Add_Fields_Markings();
        markings.add(gridPane,1,1,8,8);
        border.setCenter(markings);
        AdditionsToSecondScene.readGameState();
        ShelvesForPawns.addShelves();
        AdditionsToSecondScene.setNetgameLabel();
        BoardInitialization.CreateListOfPawns();
        BorderPane.setMargin(markings,new Insets(20,25,20,25));
        border.setBackground(new Background(scene2_background));

        scene2 = new Scene(border,Paint.valueOf("#ead5a0")); //TODO

        window.setScene(scene1);
        window.show();
    }

    private void initExchangeTools(){
        clickCommand = new ArrayBlockingQueue<>(1);
        display = new ArrayBlockingQueue<>(1);
        gameState = new ArrayBlockingQueue<>(1);
        move = new Move(clickCommand, display , gameState);
        clickSemaphore = new Semaphore(0);
        activeThread = new MutableBoolean(true);
    }

    private void closeProgram()
    {
        Boolean answer;
        answer=ConfirmBox.display("Wyjscie z gry", "Czy na pewno chcesz opuscic gre?\nPrzecież jest super!");
        if(answer==true) window.close();
    }

    public static void endGame()
    {
        window.setScene(scene1);
    }

}
