package GUI;
import javafx.animation.PathTransition;


import javafx.scene.Node;


import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class MoveAnimation {

    static Node pawn;
    static Path path = new Path();
    static MoveTo move_to;
    static LineTo line_to= new LineTo();
    static PathTransition pathTransition = new PathTransition();

    public static void move_animation(Node piece, double move_fromx, double move_fromy, double move_tox, double move_toy)
    {
        MoveAnimation.pawn=piece;
        MoveAnimation.move_to =new MoveTo(move_fromx,move_fromy);
        line_to.setX(move_tox);
        line_to.setY(move_toy);
        path.getElements().add(MoveAnimation.move_to);
        path.getElements().add(line_to);
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setNode(pawn);
        pathTransition.setAutoReverse(false);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.play();

    }
}
