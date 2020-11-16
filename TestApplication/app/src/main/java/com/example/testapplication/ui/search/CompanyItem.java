package com.example.testapplication.ui.search;

public class CompanyItem { // class for the company JSON-data that is returned from the search url
    String companyId;
    String companyName;
    String dateOfRegistration;
    String companyForm;

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

}
