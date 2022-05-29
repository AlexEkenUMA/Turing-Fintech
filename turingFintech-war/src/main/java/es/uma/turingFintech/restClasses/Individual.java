package es.uma.turingFintech.restClasses;

public class Individual {
    private ProductsClients productsClients;
    private String activeCustomer;
    private String identificationNumber;
    private String dateOfBirth;
    private ClientName name;
    private Adress adress;

    public ProductsClients getProductsClients() {
        return productsClients;
    }

    public void setProductsClients(ProductsClients productsClients) {
        this.productsClients = productsClients;
    }

    public String getActiveCustomer() {
        return activeCustomer;
    }

    public void setActiveCustomer(String activeCustomer) {
        this.activeCustomer = activeCustomer;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
