package com.example.testapplication.ui.search;

import static org.junit.jupiter.api.Assertions.*;

class CompanyItemTest {

    @org.junit.jupiter.api.Test
    void getCompanyName() {
        CompanyItem companyItem = new CompanyItem("","","","");
        companyItem.setCompanyName("ABC");
        assertEquals("ABC", companyItem.getCompanyName());
    }
}