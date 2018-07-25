package seanacheng.com.schwartzsurvey;

public class Value {

    private int questionID;
    private String value;
    private int selfRank;
    private int employerRank;

    public Value() {}

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

    public int getRank(String column) {
        int rank;
        if (column.startsWith("personal")) {
            rank = selfRank;
        } else if (column.startsWith("employer")) {
            rank = employerRank;
        } else rank=-1;
        return rank;
    }

    public void setRank(int rank, String column) {
        if (column.startsWith("personal")) {
            this.selfRank = rank;
        } else if (column.startsWith("employer")) {
            this.employerRank = rank;
        }
    }
}
