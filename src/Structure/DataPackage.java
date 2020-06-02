package Structure;

import java.io.Serializable;

public class DataPackage implements Serializable {
    private final DataChanges dataChanges;
    private final ToDisplay toDisplay;

    public DataPackage(DataChanges dataChanges, ToDisplay toDisplay){
        this.dataChanges = dataChanges;
        this.toDisplay = toDisplay;
    }

    public DataChanges getDataChanges() {
        return dataChanges;
    }

    public ToDisplay getToDisplay() {
        return toDisplay;
    }
}
