package org.example.Services.Contracts;

import org.example.Entities.Company;

import java.util.List;

public interface ICompanyService {
    Company getTopLevelParent(Company child);

    long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies);
}
