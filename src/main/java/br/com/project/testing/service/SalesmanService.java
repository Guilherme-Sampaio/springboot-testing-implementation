package br.com.project.testing.service;

import br.com.project.testing.repository.SalesmanRepository;
import org.springframework.stereotype.Service;

@Service
public class SalesmanService {

  private final SalesmanRepository repository;

  public SalesmanService(SalesmanRepository salesmanRepository) {
    this.repository = salesmanRepository;
  }
}
