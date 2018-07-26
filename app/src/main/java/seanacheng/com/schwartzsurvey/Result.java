package seanacheng.com.schwartzsurvey;

public class Result {

    private String valueDimension;
    private double personalResults;
    private double employerResults;

    public Result() {}

    public String getValueDimension() {
        return valueDimension;
    }

    public void setValueDimension(String valueDimension) {
        this.valueDimension = valueDimension;
    }

    public double getPersonalResults() {
        return personalResults;
    }

    public void setPersonalResults(double personalResults) {
        this.personalResults = personalResults;
    }

    public double getEmployerResults() {
        return employerResults;
    }

    public void setEmployerResults(double employerResults) {
        this.employerResults = employerResults;
    }
}
