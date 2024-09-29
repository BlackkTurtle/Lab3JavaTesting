package org.example.Services;

import org.example.Entities.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceTest {
    private CompanyService service = new CompanyService();

    @Test
    void whenCompanyHasManyParents_thenReturnTopParent() {
        var topParent=new Company(null,1);
        var secondParent=new Company(topParent,2);
        var thirdParent=new Company(secondParent,3);
        var fourthParent=new Company(thirdParent,4);

        assertEquals(topParent, service.getTopLevelParent(fourthParent));
        assertEquals(topParent, service.getTopLevelParent(thirdParent));
        assertEquals(topParent, service.getTopLevelParent(secondParent));
    }

    @Test
    void whenCompanyHasNoParents_thenReturnItself() {
        var topParent=new Company(null,1);

        assertEquals(topParent, service.getTopLevelParent(topParent));
    }

    @Test
    void whenCompanyIsNull_thenReturnException() {
        Exception e = Assertions.assertThrows(NullPointerException.class, () -> {
            service.getTopLevelParent(null);
        });
        assertEquals("child is null", e.getMessage());
    }

    @Test
    void whenCompanyIsNull_thenReturnExceptionEmployeeCount() {
        Exception e = Assertions.assertThrows(NullPointerException.class, () -> {
            service.getEmployeeCountForCompanyAndChildren(null, new ArrayList<Company>());
        });
        assertEquals("company is null", e.getMessage());
    }

    @Test
    void whenCompaniesIsNull_thenReturnException() {
        var company =new Company(null,1);
        Exception e = Assertions.assertThrows(NullPointerException.class, () -> {
            service.getEmployeeCountForCompanyAndChildren(company, null);
        });
        assertEquals("companies is null", e.getMessage());
    }

    @Test
    void whenCompanyEmployeesLessThan0_thenReturnException() {
        var company =new Company(null,-1);
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.getEmployeeCountForCompanyAndChildren(company, new ArrayList<>());
        });
        assertEquals("company employee count is less than 0", e.getMessage());
    }

    @Test
    void whenCompanyChildIsNull_thenReturnException() {
        var company =new Company(null,1);

        var companies = new ArrayList<Company>();
        companies.add(null);
        Exception e = Assertions.assertThrows(NullPointerException.class, () -> {
            service.getEmployeeCountForCompanyAndChildren(company, companies);
        });
        assertEquals("company child is null", e.getMessage());
    }

    @Test
    void whenCompanyChildEmployeesLessThan0_thenReturnException() {
        var company =new Company(null,1);

        var companies = new ArrayList<Company>();
        companies.add(new Company(company,-1));
        Exception e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.getEmployeeCountForCompanyAndChildren(company, companies);
        });
        assertEquals("company child employee count is less than 0", e.getMessage());
    }

    @Test
    void whenCompaniesParentsAreNotCompany_thenReturnCompanyEmployeesCount() {
        var topParent=new Company(null,1);
        var otherChild=new Company(null,2);
        var companies=new ArrayList<Company>();
        companies.add(otherChild);

        assertEquals(1, service.getEmployeeCountForCompanyAndChildren(topParent,companies));
    }

    @Test
    void whenCompaniesParentsAreCompany_thenReturnAllCompaniesEmployeesCount() {
        var topParent=new Company(null,1);
        var otherChild=new Company(topParent,2);
        var companies=new ArrayList<Company>();
        companies.add(otherChild);

        assertEquals(3, service.getEmployeeCountForCompanyAndChildren(topParent,companies));
    }

    @Test
    void whenCompaniesParentsAreDifferent_thenReturnAllCompaniesEmployeesCount() {
        var topParent=new Company(null,1);
        var otherParent=new Company(null,1);
        var otherChild=new Company(topParent,2);
        var secondChild=new Company(otherParent,3);
        var companies=new ArrayList<Company>();
        companies.add(secondChild);
        companies.add(otherChild);

        assertEquals(3, service.getEmployeeCountForCompanyAndChildren(topParent,companies));
    }

}