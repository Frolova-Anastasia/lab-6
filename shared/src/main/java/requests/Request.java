package requests;

import data.Organization;
import data.Product;

import java.io.Serializable;

public class Request implements Serializable {
    private final String[] args;
    private Product product;
    private Organization organization;

    public Request(String... args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public Organization getOrganization(){
        return organization;
    }

    public void setOrganization(Organization organization){
        this.organization = organization;
    }

}
