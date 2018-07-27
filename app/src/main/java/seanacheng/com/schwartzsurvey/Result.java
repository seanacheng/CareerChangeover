package seanacheng.com.schwartzsurvey;

public class Result {

    private String dimension;
    private double personalScore;
    private double employerScore;

    public Result() {}

    public String getValueDimension() {
        return dimension;
    }

    public void setValueDimension(String valueDimension) {
        this.dimension = valueDimension;
    }

    public double getPersonalScore() {
        return personalScore;
    }

    public void setPersonalScore(double personalScore) {
        this.personalScore = personalScore;
    }

    public double getEmployerScore() {
        return employerScore;
    }

    public void setEmployerScore(double employerScore) {
        this.employerScore = employerScore;
    }

    public double getScore(String column) {
        double result;
        if (column.startsWith("personal")) {
            result = personalScore;
        } else if (column.startsWith("employer")) {
            result = employerScore;
        } else result = -1;
        return result;
    }

    public void setScore(double result, String column) {
        if (column.startsWith("personal")) {
            this.personalScore = result;
        } else if (column.startsWith("employer")) {
            this.employerScore = result;
        }
    }
}
