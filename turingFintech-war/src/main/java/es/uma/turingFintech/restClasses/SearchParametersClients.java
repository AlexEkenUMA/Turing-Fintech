package es.uma.turingFintech.restClasses;

public class SearchParametersClients {
    private ClientName name;
    private String startPeriod;
    private String endPeriod;

    public ClientName getName() {
        return name;
    }

    public void setName(ClientName name) {
        this.name = name;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }


}
