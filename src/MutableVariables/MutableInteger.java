package MutableVariables;

public class MutableInteger {
    private int anInt;
    private boolean isSet = false;
    public MutableInteger(){}
    public MutableInteger(int anInt){
        this.anInt = anInt;
    }
    public void set(int anInt) {
        this.anInt = anInt;
        isSet = true;
    }
    public int getAnInt() {
        return anInt;
    }
    public boolean isSet(){
        return isSet;
    }
}
