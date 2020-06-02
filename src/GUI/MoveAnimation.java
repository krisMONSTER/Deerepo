package GUI;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;


import javafx.animation.Timeline;
import javafx.scene.Node;


import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class MoveAnimation{

    static Node pawn;
    static Path path = new Path();
    static MoveTo move_to;
    static LineTo line_to= new LineTo();
    static PathTransition pathTransition = new PathTransition();

    public static void move_animation(Node piece, double move_fromx, double move_fromy, double move_tox, double move_toy)
    {
        pawn=piece;
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        int stepx=(int)(move_tox-move_fromx);
        int stepy=(int)(move_toy-move_toy);
        //final KeyValue kv = new KeyValue(pawn.getLayoutX(),(double)stepx*50 );
        //final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        //timeline.getKeyFrames().add(kf);
        timeline.play();



    }
}
