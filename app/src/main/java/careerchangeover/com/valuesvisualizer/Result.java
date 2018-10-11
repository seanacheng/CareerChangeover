package careerchangeover.com.valuesvisualizer;

public class Result {

    private String dimension;
    private double personalScore;
    private double employerScore;

    // Constructor
    public Result() {}

    // Getters
    public String getValueDimension() {
        return dimension;
    }

    public double getPersonalScore() {
        return personalScore;
    }

    public double getEmployerScore() {
        return employerScore;
    }

    public double getScore(String column) {
        double result;
        if (column.toLowerCase().startsWith("personal")) {
            result = personalScore;
        } else if (column.toLowerCase().startsWith("employer")) {
            result = employerScore;
        } else result = -1;
        return result;
    }

    // Setters
    public void setValueDimension(String valueDimension) {
        this.dimension = valueDimension;
    }

    public void setPersonalScore(double personalScore) {
        this.personalScore = personalScore;
    }

    public void setEmployerScore(double employerScore) {
        this.employerScore = employerScore;
    }

    public void setScore(double result, String column) {
        if (column.toLowerCase().startsWith("personal")) {
            this.personalScore = result;
        } else if (column.toLowerCase().startsWith("employer")) {
            this.employerScore = result;
        }
    }
}
