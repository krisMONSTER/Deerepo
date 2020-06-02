package Structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToDisplay implements Serializable {
    List<int[]> coordinates = new ArrayList<>();
    private TypeOfAction typeOfAction;

    public ToDisplay(){}

    public ToDisplay(TypeOfAction typeOfAction){
        this.typeOfAction = typeOfAction;
    }

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
}