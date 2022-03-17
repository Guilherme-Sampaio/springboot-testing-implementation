package br.com.project.testing.service;

import br.com.project.testing.repository.SaleRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

  private final SaleRepository repository;

  public SaleService(SaleRepository repository) {
    this.repository = repository;
  }
}
