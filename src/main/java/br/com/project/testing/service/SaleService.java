package br.com.project.testing.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import br.com.project.testing.repository.SaleRepository;
import br.com.project.testing.repository.SalesmanRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

  private final SaleRepository repository;
  private final SalesmanRepository salesmanRepository;

  public SaleService(SaleRepository repository, SalesmanRepository salesmanRepository) {
    this.repository = repository;
    this.salesmanRepository = salesmanRepository;
  }

  public List<Sale> findSalesBySalesman(Salesman salesman) throws Exception {
    if(isSalesmanInvalid(salesman)) {
      throw new Exception();
    }

    return repository.findBySalesman(salesman);
  }

  public int countBySalesman(Long salesmanId) throws Exception {
    Optional<Salesman> salesman = salesmanRepository.findById(salesmanId);

    if (!salesman.isPresent()) {
      throw new Exception();
    }

    return repository.countBySalesman(salesman.get());
  }

  public Salesman findWhoSoldMore() {
     Salesman salesman = repository.findWhoSoldMore();

     System.out.println("The best salesman, who sold more is " + salesman.getName());
     return salesman;
  }

  public boolean isSalesmanInvalid(Salesman salesman) {
    return Objects.isNull(salesman);
  }
}
