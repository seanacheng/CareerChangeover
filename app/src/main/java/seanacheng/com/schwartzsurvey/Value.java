package seanacheng.com.schwartzsurvey;

public class Value {

    private int questionID;
    private String value;

    public Value() {}

    public Value(int ID, String value) {
        this.questionID = ID;
        this.value = value;
    }

    public int getID() {
        return questionID;
    }

    public String getValue() {
        return value;
    }

    public void setID(int ID) {
        this.questionID = ID;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
