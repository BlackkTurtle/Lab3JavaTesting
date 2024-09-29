package org.example.Services;

import org.example.Entities.Company;
import org.example.Services.Contracts.ICompanyService;

import java.util.List;

public class CompanyService implements ICompanyService {
    @Override
    public Company getTopLevelParent(Company child) {

        if (child == null) {
            throw new NullPointerException("child is null");
        }

        while(true) {
            if(child.getParent() == null) {
                return child;
            }
            else {
                child = child.getParent();
            }
        }
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        long result = 0;
        if (company == null) {
            throw new NullPointerException("company is null");
        }

        if (companies == null) {
            throw new NullPointerException("companies is null");
        }

        if (company.getEmployeeCount()<0) {
            throw new IllegalArgumentException("company employee count is less than 0");
        }

        result += company.getEmployeeCount();

        for(Company child : companies) {
            if (child==null){
                throw new NullPointerException("company child is null");
            }

            if (child.getEmployeeCount()<0) {
                throw new IllegalArgumentException("company child employee count is less than 0");
            }

            if(child.getParent()==company){
                result+=child.getEmployeeCount();
            }
        }

        return result;
    }
}
