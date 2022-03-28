package br.com.project.testing.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import br.com.project.testing.repository.SaleRepository;
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
public class SaleServiceTest {

  @Mock
  private SaleRepository saleRepository;

  @Mock
  private SalesmanRepository salesmanRepository;

  private SaleService saleService;

  @BeforeEach
  void init() {
    saleService = new SaleService(saleRepository, salesmanRepository);
  }


  @Test
  void findSalesBySalesmanShouldReturnASalesList() throws Exception {
    Sale sale = constructSale(1L, new Salesman(), "table", 1, 300.00);
    Sale sale2 = constructSale(2L, new Salesman(), "lamp", 1, 50.00);
    List<Sale> sales = Arrays.asList(sale, sale2);

    Mockito.when(saleRepository.findBySalesman(ArgumentMatchers.any(Salesman.class)))
        .thenReturn(sales);

    List<Sale> salesResponse = saleService.findSalesBySalesman(new Salesman());

    Assertions.assertFalse(salesResponse.isEmpty());
    Assertions.assertEquals(salesResponse.get(0).getId(), 1L);
  }

  @Test
  void findSalesBySalesmanWithoutSalesmanShouldReturnException() {
    Assertions.assertThrows(Exception.class, () -> saleService.findSalesBySalesman(null));
  }

  @Test
  void countBySalesmanShouldReturnAnInteger() throws Exception {
    Mockito.when(salesmanRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(new Salesman()));

    Mockito.when(saleRepository.countBySalesman(ArgumentMatchers.any(Salesman.class)))
        .thenReturn(1);

    int countResponse = saleService.countBySalesman(ArgumentMatchers.anyLong());

    Assertions.assertInstanceOf(Integer.class, countResponse);
  }

  @Test
  void countBySalesmanWithoutSalesmanIdShouldReturnAnException() throws Exception {
    Assertions.assertThrows(Exception.class, () -> saleService.countBySalesman(null));
  }

  @Test
  void findWhoSoldMoreShouldReturnAnSalesman() {
    Salesman salesman = new Salesman();
    salesman.setId(1L);
    salesman.setName("Jim");

    Mockito.when(salesmanRepository.findWhoSoldMore())
        .thenReturn(salesman);

    Salesman salesmanResponse = salesmanRepository.findWhoSoldMore();

    Assertions.assertNotNull(salesmanResponse);
    Assertions.assertEquals(salesmanResponse.getName(), salesman.getName());
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
