package br.com.project.testing.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import br.com.project.testing.repository.SalesmanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SalesmanServiceTest {

  @Mock
  private SalesmanRepository salesmanRepository;

  @Mock
  private SaleService saleService;

  private SalesmanService salesmanService;

  @BeforeEach
  void init() {
    salesmanService = new SalesmanService(salesmanRepository, saleService);
  }

  @Test
  void payEmployeeShouldReturnTotalSalary() throws Exception {
    Sale sale = constructSale(1L, new Salesman(), "table", 1, 1500.00);
    Sale sale2 = constructSale(2L, new Salesman(), "chair", 2, 500.00);
    List<Sale> sales = Arrays.asList(sale, sale2);
    Salesman salesman = constructSalesman(1L, "Jim", "test@gmail.com", 1800.00, 2,  sales);


    Mockito.when(salesmanRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(salesman));

    Mockito.when(saleService.findSalesBySalesman(ArgumentMatchers.any(Salesman.class)))
        .thenReturn(sales);

    Double totalSalaryResponse = salesmanService.payEmployee(1L);

    Assertions.assertNotNull(totalSalaryResponse);
    Assertions.assertInstanceOf(Double.class, totalSalaryResponse);
    Assertions.assertTrue(totalSalaryResponse >= salesman.getSalary());
  }

  @Test
  void payEmployeeWithoutSalesmanShouldThrowException() {
    Assertions.assertThrows(Exception.class, () -> salesmanService.payEmployee(null));
  }

  @Test
  void validateEmailShouldOnlyAcceptValidEmails() {
    Assertions.assertDoesNotThrow(() -> salesmanService.validateEmail("aaa@hotmail.com"));
    Assertions.assertDoesNotThrow(() -> salesmanService.validateEmail("aaa@gmail.com"));
    Assertions.assertDoesNotThrow(() -> salesmanService.validateEmail("aaa@outlook.com"));
  }

  @Test
  void validateEmailWithInvalidEmailShouldThrowException() {
    Assertions.assertThrows(Exception.class, () -> salesmanService.validateEmail("3232@gmaiu.com"));
    Assertions.assertThrows(Exception.class, () -> salesmanService.validateEmail("    "));
  }

  @Test
  void improveEmployeeCommissionShouldUpdateCommission() throws Exception {
    Salesman salesman = constructSalesman(1L, "Jim", "test@gmail.com", 1800.00, 2,  new ArrayList<>());

    Mockito.when(salesmanRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(salesman));

    int newCommissionResponse = salesmanService.improveEmployeeCommission(1L, 1);

    Assertions.assertNotNull(newCommissionResponse);
    Assertions.assertEquals(newCommissionResponse, 3);
  }

  private Salesman constructSalesman(Long id, String name, String email, Double salary, Integer commission,
      List<Sale> sales) {
    Salesman salesman = new Salesman();
    salesman.setId(id);
    salesman.setName(name);
    salesman.setEmail(email);
    salesman.setSalary(salary);
    salesman.setCommission(commission);
    salesman.setSales(sales);

    return salesman;
  }

  private Sale constructSale(Long id, Salesman salesman, String product, Integer quantity, Double value) {
    Sale sale = new Sale();
    sale.setId(id);
    sale.setSalesman(salesman);
    sale.setProduct(product);
    sale.setQuantity(quantity);
    sale.setValue(value);

    return sale;
  }
}
