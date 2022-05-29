package es.uma.turingFintech.restClasses;

public class AccountHolder {
    private String activeCustomer;
    private String accounttype;
    private ClientName name;
    private Adress adress;

    public String getActiveCustomer() {
        return activeCustomer;
    }

    public void setActiveCustomer(String activeCustomer) {
        this.activeCustomer = activeCustomer;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public ClientName getName() {
        return name;
    }

    public void setName(ClientName name) {
        this.name = name;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
}
