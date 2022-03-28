package br.com.project.testing.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import br.com.project.testing.repository.SalesmanRepository;
import br.com.project.testing.service.SalesmanService;
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

@WebMvcTest(controllers = SalesmanController.class)
public class SalesmanControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private SalesmanRepository salesmanRepository;

  @MockBean
  private SalesmanService salesmanService;

  @Test
  void findByIdShouldReturnAnSalesman() throws Exception {
    Salesman salesman = constructSalesman(1L, "Bob", "test@gmail.com", 1800.0, 1, new ArrayList<>());
    String expectedResult = objectMapper.writeValueAsString(salesman);

    Mockito.when(salesmanRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(salesman));

    mockMvc.perform(MockMvcRequestBuilders.get("/salesman/{id}", 1L)
        .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));
  }

  @Test
  void findAllShouldReturnAnSalesmenList() throws Exception {
    Salesman salesman = constructSalesman(1L, "Bob", "test@gmail.com", 1800.0, 1, new ArrayList<>());
    List<Salesman> salesmanList = Arrays.asList(salesman);
    String expectedResult = objectMapper.writeValueAsString(salesmanList);

    Mockito.when(salesmanRepository.findAll())
        .thenReturn(salesmanList);

    mockMvc.perform(MockMvcRequestBuilders.get("/salesman/all")
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));
  }

  @Test
  void saveShouldSaveSalesmanWithCorrectData() throws Exception {
    Salesman salesman = constructSalesman(1L, "Bob", "test@gmail.com", 1800.0, 1, new ArrayList<>());

    String expectedResult = objectMapper.writeValueAsString(salesman);

    Mockito.when(salesmanRepository.save(ArgumentMatchers.any(Salesman.class)))
        .thenReturn(salesman);

    mockMvc.perform(MockMvcRequestBuilders.post("/salesman")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(salesman)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));

    Mockito.verify(salesmanService, Mockito.times(1))
        .validateEmail(ArgumentMatchers.anyString());
  }

  @Test
  void payEmployeeShouldReturnOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/salesman/{id}", 1L)
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    Mockito.verify(salesmanService, Mockito.times(1))
        .payEmployee(ArgumentMatchers.anyLong());
  }

  @Test
  void updateShouldUpdateObject() throws Exception {
    Salesman salesman = constructSalesman(1L, "Bob", "test@gmail.com", 1800.0, 1, new ArrayList<>());
    String expectedResult = objectMapper.writeValueAsString(salesman);

    Mockito.when(salesmanRepository.save(ArgumentMatchers.any(Salesman.class)))
        .thenReturn(salesman);

    mockMvc.perform(MockMvcRequestBuilders.put("/salesman")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(salesman)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> Assertions.assertEquals(result.getResponse().getContentAsString(), expectedResult));
  }

  @Test
  void improveEmployeeCommissionShouldReturnOk() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/salesman/improve_commission")
            .contentType("application/json")
            .param("salesmanId", "1")
            .param("increase", "2"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    Mockito.verify(salesmanService, Mockito.times(1))
        .improveEmployeeCommission(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
  }

  @Test
  void improveEmployeeCommissionWithoutParamsShouldReturnBadRequest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.patch("/salesman/improve_commission")
            .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    Mockito.verify(salesmanService, Mockito.times(0))
        .improveEmployeeCommission(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
  }

  @Test
  void deleteShouldReturnNoContent() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/salesman/{id}", 1L)
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

}
