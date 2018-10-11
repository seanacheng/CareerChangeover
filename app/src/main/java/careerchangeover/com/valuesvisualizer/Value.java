package careerchangeover.com.valuesvisualizer;

public class Value {

    private int questionID;
    private String value;
    private String dimension;
    private int selfRank;
    private int employerRank;

    // Constructor
    public Value() {}

    // Getters
    public int getID() {
        return questionID;
    }

    public String getValue() {
        return value;
    }

    public String getDimension() {
        return dimension;
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

    // Setters
    public void setID(int ID) {
        this.questionID = ID;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setSelfRank(int selfRank) {
        this.selfRank = selfRank;
    }

    public void setEmployerRank(int employerRank) {
        this.employerRank = employerRank;
    }

    public void setRank(int rank, String column) {
        if (column.startsWith("personal")) {
            this.selfRank = rank;
        } else if (column.startsWith("employer")) {
            this.employerRank = rank;
        }
    }
}
