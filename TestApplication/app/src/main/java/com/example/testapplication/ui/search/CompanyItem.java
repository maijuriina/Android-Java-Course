package com.example.testapplication.ui.search;

import java.util.ArrayList;

public class CompanyItem { // class for the company JSON-data that is returned from the search url

    public CompanyItem c;
    private String companyId;
    private String companyName;
    private String dateOfRegistration;
    private String companyForm;

    // constructor for the class attributes
    public CompanyItem(String companyId, String companyName, String dateOfRegistration, String companyForm) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.dateOfRegistration = dateOfRegistration;
        this.companyForm = companyForm;
    }

    public String getId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public String getCompanyForm() {
        return companyForm;
    }

    // FOLLOWING FUNCTIONS ARE FOR TESTING PURPOSES
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public void setCompanyForm(String companyForm) {
        this.companyForm = companyForm;
    }

    public CompanyItem getOneCompany() {return c;}

}
