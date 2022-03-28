package br.com.project.testing.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import br.com.project.testing.repository.SaleRepository;
import br.com.project.testing.service.SaleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = SaleController.class)
public class SaleControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private SaleRepository saleRepository;

  @MockBean
  private SaleService saleService;

  @Test
  void findByIdShouldReturnAnSale() throws Exception {
    Sale sale = constructSale(1L, new Salesman(), "cellphone", 1, 1800.0);
    String expectedResult = objectMapper.writeValueAsString(sale);

    Mockito.when(saleRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(sale));

    mockMvc.perform(MockMvcRequestBuilders.get("/sale/{id}", 1L)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));
  }

  @Test
  void findAllShouldReturnAnSaleList() throws Exception {
    Sale sale = constructSale(1L, new Salesman(), "cellphone", 1, 1800.0);
    List<Sale> salesmanList = Arrays.asList(sale);
    String expectedResult = objectMapper.writeValueAsString(salesmanList);

    Mockito.when(saleRepository.findAll())
        .thenReturn(salesmanList);

    mockMvc.perform(MockMvcRequestBuilders.get("/sale/all")
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));
  }

  @Test
  void findBiggestShouldReturnOk() throws Exception {
    Double biggestSaleValue = 15000.0;
    String expectedResult = objectMapper.writeValueAsString(biggestSaleValue);

    Mockito.when(saleRepository.findBiggestSale())
        .thenReturn(biggestSaleValue);

    mockMvc.perform(MockMvcRequestBuilders.get("/sale/biggest")
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));

    Mockito.verify(saleRepository, Mockito.times(1))
        .findBiggestSale();
  }

  @Test
  void countSalesByProductShouldReturnANumber() throws Exception {
    Mockito.when(saleRepository.countSalesByProduct(ArgumentMatchers.anyString()))
        .thenReturn(1);

    mockMvc.perform(MockMvcRequestBuilders.get("/sale/count/product/{product}", "table")
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), "1"));

    Mockito.verify(saleRepository, Mockito.times(1))
        .countSalesByProduct(ArgumentMatchers.anyString());
  }

  @Test
  void countSalesBySalesmanShouldReturnANumber() throws Exception {
    Mockito.when(saleService.countBySalesman(ArgumentMatchers.anyLong()))
        .thenReturn(1);

    mockMvc.perform(MockMvcRequestBuilders.get("/sale/count/salesman/{id}", 1L)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), "1"));

    Mockito.verify(saleService, Mockito.times(1))
        .countBySalesman(ArgumentMatchers.anyLong());
  }

  @Test
  void findWhoSoldMoreShouldReturnASalesman() throws Exception {
    Salesman salesman = constructSalesman(1L, "Bob", "test@gmail.com", 1800.0, 1, new ArrayList<>());
    String expectedResult = objectMapper.writeValueAsString(salesman);

    Mockito.when(saleService.findWhoSoldMore())
        .thenReturn(salesman);

    mockMvc.perform(MockMvcRequestBuilders.get("/sale/best_salesman")
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));

    Mockito.verify(saleService, Mockito.times(1))
        .findWhoSoldMore();
  }

  @Test
  void saveShouldSaveSaleWithCorrectData() throws Exception {
    Sale sale = constructSale(1L, new Salesman(), "cellphone", 1, 1800.0);
    String expectedResult = objectMapper.writeValueAsString(sale);

    Mockito.when(saleRepository.save(ArgumentMatchers.any(Sale.class)))
        .thenReturn(sale);

    mockMvc.perform(MockMvcRequestBuilders.post("/sale")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(sale)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));
  }

  @Test
  void updateShouldUpdateObject() throws Exception {
    Sale sale = constructSale(1L, new Salesman(), "cellphone", 1, 1800.0);
    String expectedResult = objectMapper.writeValueAsString(sale);

    Mockito.when(saleRepository.save(ArgumentMatchers.any(Sale.class)))
        .thenReturn(sale);

    mockMvc.perform(MockMvcRequestBuilders.put("/sale")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(sale)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));
  }

  @Test
  void deleteShouldReturnNoContent() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/sale/{id}", 1L)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
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
