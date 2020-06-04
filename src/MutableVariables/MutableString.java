package MutableVariables;

public class MutableString {
    private String string;
    private boolean isSet = false;
    public MutableString(){}
    public MutableString(String string){
        this.string = string;
    }
    public void set(String string) {
        this.string = string;
        isSet = true;
    }
    public String getString() {
        return string;
    }
    public boolean isSet(){
        return isSet;
    }
}
