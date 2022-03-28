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
public class SaleRepositoryTest {

  @Autowired
  private SaleRepository saleRepository;

  @Autowired
  private SalesmanRepository salesmanRepository;

  @Test
  void findBySalesmanShouldReturnAListSales() {
    Salesman salesman = constructSalesman("Clark", 2000.0, 2, new ArrayList<>());
    salesman = salesmanRepository.save(salesman);

    Sale sale = constructSale(salesman, "chair", 2, 350.0);
    sale = saleRepository.save(sale);

    List<Sale> salesListResponse = saleRepository.findBySalesman(salesman);

    Assertions.assertFalse(salesListResponse.isEmpty());
    Assertions.assertEquals(1, salesListResponse.size());
    Assertions.assertEquals(sale, salesListResponse.get(0));
  }

  @Test
  void countBySalesmanShouldReturnAnInt() {
    Salesman salesman = constructSalesman("Clark", 2000.0, 2, new ArrayList<>());
    salesman = salesmanRepository.save(salesman);

    Sale sale = constructSale(salesman, "chair", 2, 350.0);
    sale = saleRepository.save(sale);

    int countResponse = saleRepository.countBySalesman(salesman);

    Assertions.assertInstanceOf(Integer.class, countResponse);
    Assertions.assertEquals(1, countResponse);
  }

  @Test
  void countSalesByProductShouldReturnAnInt() {
    Salesman salesman = constructSalesman("Clark", 2000.0, 2, new ArrayList<>());
    salesman = salesmanRepository.save(salesman);

    Sale sale = constructSale(salesman, "chair", 2, 350.0);
    sale = saleRepository.save(sale);

    int countResponse = saleRepository.countSalesByProduct(sale.getProduct());

    Assertions.assertInstanceOf(Integer.class, countResponse);
    Assertions.assertEquals(sale.getQuantity(), countResponse);
  }

  @Test
  void findBiggestSaleShouldReturnAnDouble() {
    Salesman salesman = constructSalesman("Clark", 2000.0, 2, new ArrayList<>());
    salesman = salesmanRepository.save(salesman);

    Sale sale = constructSale(salesman, "Fridge", 1, 12000.0);
    sale = saleRepository.save(sale);

    Double biggestSaleResponse = saleRepository.findBiggestSale();

    Assertions.assertNotNull(biggestSaleResponse);
    Assertions.assertInstanceOf(Double.class, biggestSaleResponse);
    Assertions.assertEquals(12000.0, biggestSaleResponse);
  }

  private Salesman constructSalesman(String name, Double salary, Integer commission, List<Sale> sales) {
    Salesman salesman = new Salesman();
    salesman.setName(name);
    salesman.setSalary(salary);
    salesman.setCommission(commission);
    salesman.setSales(sales);

    return salesman;
  }

  private Sale constructSale(Salesman salesman, String product, Integer quantity, Double value) {
    Sale sale = new Sale();
    sale.setSalesman(salesman);
    sale.setProduct(product);
    sale.setQuantity(quantity);
    sale.setValue(value);

    return sale;
  }

}
