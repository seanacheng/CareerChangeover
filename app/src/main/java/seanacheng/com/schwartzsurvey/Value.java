package seanacheng.com.schwartzsurvey;

public class Value {

    private int questionID;
    private String value;
    private int selfRank;
    private int employerRank;

    public Value() {}

//    public Value(int ID, String value) {
//        this.questionID = ID;
//        this.value = value;
//    }

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

    public int getSelfRank() {
        return selfRank;
    }

    public int getEmployerRank() {
        return employerRank;
    }

    public void setSelfRank(int selfRank) {
        this.selfRank = selfRank;
    }

    public void setEmployerRank(int employerRank) {
        this.employerRank = employerRank;
    }
}
