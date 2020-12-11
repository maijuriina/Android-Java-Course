package com.example.testapplication.ui.search;

import static org.junit.jupiter.api.Assertions.*;

class CompanyItemTest {
    CompanyItem companyItem = new CompanyItem("","","","");

    @org.junit.jupiter.api.Test
    void getCompanyId() {
        companyItem.setCompanyId("TEST000000-222");
        assertEquals("TEST000000-222", companyItem.getId());
    }

    @org.junit.jupiter.api.Test
    void getCompanyName() {
        companyItem.setCompanyName("TESTABC");
        assertEquals("TESTABC", companyItem.getCompanyName());
    }

    @org.junit.jupiter.api.Test
    void getCompanyRegistrationDate() {
        companyItem.setDateOfRegistration("TEST01-01-01");
        assertEquals("TEST01-01-01", companyItem.getDateOfRegistration());
    }

    @org.junit.jupiter.api.Test
    void getCompanyForm() {
        companyItem.setCompanyForm("TEST-OY");
        assertEquals("TEST-OY", companyItem.getCompanyForm());
    }

    @org.junit.jupiter.api.Test
    void getOneCompany() {
        assertNotNull(companyItem);
    }
}