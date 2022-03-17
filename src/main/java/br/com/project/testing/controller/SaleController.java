package br.com.project.testing.controller;

import java.util.List;
import java.util.Optional;

import br.com.project.testing.model.Sale;
import br.com.project.testing.repository.SaleRepository;
import br.com.project.testing.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sale", consumes = "application/json", produces = "application/json")
public class SaleController {

  private final SaleRepository repository;
  private final SaleService service;

  @Autowired
  public SaleController(SaleRepository repository, SaleService service) {
    this.repository = repository;
    this.service = service;
  }

  @GetMapping(path = "/{id}")
  public Optional<Sale> findById(@PathVariable("id") Long id) {
    return repository.findById(id);
  }

  @GetMapping(path = "/all")
  public List<Sale> findAll() {
    return repository.findAll();
  }

  @PostMapping
  public Sale save(@RequestBody Sale sale) {
    return repository.save(sale);
  }

  @PutMapping
  public Sale update(@RequestBody Sale sale) {
    return repository.save(sale);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
