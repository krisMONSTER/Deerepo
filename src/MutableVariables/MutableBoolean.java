package MutableVariables;

public class MutableBoolean {
    private boolean aBoolean;
    public MutableBoolean(){}
    public MutableBoolean(boolean aBoolean){
        this.aBoolean = aBoolean;
    }
    public void set(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
    public boolean get() {
        return aBoolean;
    }
}
