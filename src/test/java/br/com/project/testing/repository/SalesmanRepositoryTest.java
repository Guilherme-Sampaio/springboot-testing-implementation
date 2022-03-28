package br.com.project.testing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5435/salesman_project",
})
public class SalesmanRepositoryTest {

  @Autowired
  private SalesmanRepository salesmanRepository;

  @Autowired
  private SaleRepository saleRepository;


  @Test
  void updateSalesmanCommissionShouldUpdateOneRow() {
    Salesman salesman = constructSalesman("Bob", 2500.0, 1, new ArrayList<>());
    salesman = salesmanRepository.save(salesman);

    int rowsUpdated = salesmanRepository.updateSalesmanCommission(5,  salesman.getId());
    Assertions.assertEquals(1, rowsUpdated);

    Optional<Salesman> salesmanResponse = salesmanRepository.findById(salesman.getId());
    Assertions.assertTrue(salesmanResponse.isPresent());
  }

  @Test
  void findWhoSoldMoreShouldReturnTheBetterSalesman() {
    Salesman salesman = constructSalesman("Clark", 2000.0, 2, new ArrayList<>());
    salesman = salesmanRepository.save(salesman);
    Salesman salesman2 = constructSalesman("Ben", 2500.0, 2, new ArrayList<>());
    salesman2 = salesmanRepository.save(salesman2);

    Sale sale = constructSale(salesman, "chair", 2, 350.0);
    sale = saleRepository.save(sale);
    Sale sale2 = constructSale(salesman2, "TVs", 4, 5500.0);
    sale2 = saleRepository.save(sale2);

    Salesman salesmanResponse = salesmanRepository.findWhoSoldMore();

    Assertions.assertNotNull(salesmanResponse);
    Assertions.assertEquals(salesmanResponse.getId(), salesman2.getId());
  }

  private Sale constructSale(Salesman salesman, String product, Integer quantity, Double value) {
    Sale sale = new Sale();
    sale.setSalesman(salesman);
    sale.setProduct(product);
    sale.setQuantity(quantity);
    sale.setValue(value);

    return sale;
  }

  private Salesman constructSalesman(String name, Double salary, Integer commission, List<Sale> sales) {
    Salesman salesman = new Salesman();
    salesman.setName(name);
    salesman.setSalary(salary);
    salesman.setCommission(commission);
    salesman.setSales(sales);

    return salesman;
  }
}
