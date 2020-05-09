package Structure;

import java.util.ArrayList;
import java.util.List;

public class ToDisplay {
    List<int[]> coordinates = new ArrayList<>();
    private TypeOfAction typeOfAction;

    public void setTypeOfAction(TypeOfAction typeOfAction){
        this.typeOfAction = typeOfAction;
    }

    public void addCoordinates(int[] coordinates){
        this.coordinates.add(coordinates);
    }

    public List<int[]> getCoordinates(){
        return coordinates;
    }

    public TypeOfAction getTypeOfAction(){
        return typeOfAction;
    }
    /*private final TypeOfAction typeOfAction;
    private final int x0;
    private int x1,x2,x3;
    private final int y0;
    private int y1,y2,y3;

    public ToDisplay(TypeOfAction typeOfAction, int x0, int y0){
        this.typeOfAction = typeOfAction;
        this.x0 = x0;
        this.y0 = y0;
    }

    public ToDisplay(TypeOfAction typeOfAction, int x0, int y0, int x1, int y1){
        this(typeOfAction, x0, y0);
        this.x1 = x1;
        this.y1 = y1;
    }

    public ToDisplay(TypeOfAction typeOfAction, int x0, int y0, int x1, int y1, int x2, int y2){
        this(typeOfAction, x0, y0, x1, y1);
        this.x2 = x2;
        this.y2 = y2;
    }

    public ToDisplay(TypeOfAction typeOfAction, int x0, int y0, int x1, int y1, int x2, int y2){
        this(typeOfAction, x0, y0, x1, y1);
        this.x2 = x2;
        this.y2 = y2;
    }*/
}