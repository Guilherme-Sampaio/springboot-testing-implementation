package br.com.project.testing.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.project.testing.Enum.ValidEmails;
import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import br.com.project.testing.repository.SalesmanRepository;
import org.springframework.stereotype.Service;

@Service
public class SalesmanService {

  private final SalesmanRepository repository;
  private final SaleService saleService;

  public SalesmanService(SalesmanRepository salesmanRepository, SaleService saleService) {
    this.repository = salesmanRepository;
    this.saleService = saleService;
  }

  public Double payEmployee(Long salesmanId) throws Exception {
    Salesman salesman = findAndValidateById(salesmanId);
    List<Sale> sales = saleService.findSalesBySalesman(salesman);
    Double totalSalary = calculateSalaryWithCommission(salesman.getSalary(), salesman.getCommission(), sales);

    System.out.println(salesman.getName() + " you'll receive $" + totalSalary + " this month.");
    return totalSalary;
  }

  public Double calculateSalaryWithCommission(Double salary, int commission, List<Sale> sales) {
    List<Double> salesValues = sales.stream().map(Sale::getValue).collect(Collectors.toList());
    Double moneyOfSales = salesValues.stream().reduce(0.0, (total, actual) -> total + actual);
    Double commissionFromSales = moneyOfSales * (commission / 100);

    return commissionFromSales + salary;
  }

  public void validateEmail(String email) throws Exception {
    boolean isValidHotmail = email.contains(ValidEmails.HOTMAIL.getDescription());
    boolean isValidGmail = email.contains(ValidEmails.GMAIL.getDescription());
    boolean isValidOutlook = email.contains(ValidEmails.OUTLOOK.getDescription());
    boolean isEmailNotBlank = !email.isBlank();
    boolean isValidEmail = isEmailNotBlank && (isValidHotmail || isValidGmail || isValidOutlook);

    if (!isValidEmail) {
      throw new Exception();
    }
  }

  public int improveEmployeeCommission(Long salesmanId, int amountOfIncrease) throws Exception {
    Salesman salesman = findAndValidateById(salesmanId);

    if (Objects.isNull(amountOfIncrease)) {
      throw new Exception();
    }

    int newCommission = salesman.getCommission() + amountOfIncrease;

    repository.updateSalesmanCommission(newCommission, salesmanId);

    return newCommission;
  }

  public Salesman findAndValidateById(Long salesmanId) throws Exception {
    Optional<Salesman> salesmanOptional = repository.findById(salesmanId);

    if (!salesmanOptional.isPresent()) {
      throw new Exception();
    }

    return salesmanOptional.get();
  }
}
