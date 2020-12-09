package com.example.testapplication.ui.search;

public class CompanyItem { // class for the company JSON-data that is returned from the search url

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

// --Commented out by Inspection START (9.12.2020 11.39):
//    public void setCompanyId(String companyId) {
//        this.companyId = companyId;
//    }
// --Commented out by Inspection START (9.12.2020 11.39):
//// --Commented out by Inspection STOP (9.12.2020 11.39)
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//
//    public void setDateOfRegistration(String dateOfRegistration) {
// --Commented out by Inspection STOP (9.12.2020 11.39)
        //this.dateOfRegistration = dateOfRegistration;
    //}

// --Commented out by Inspection START (9.12.2020 11.39):
//    public void setCompanyForm(String companyForm) {
//        this.companyForm = companyForm;
//    }
// --Commented out by Inspection STOP (9.12.2020 11.39)
}
