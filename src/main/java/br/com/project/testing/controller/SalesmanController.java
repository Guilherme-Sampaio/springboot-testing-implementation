package br.com.project.testing.controller;

import java.util.List;
import java.util.Optional;

import br.com.project.testing.model.Salesman;
import br.com.project.testing.repository.SalesmanRepository;
import br.com.project.testing.service.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/salesman", consumes = "application/json", produces = "application/json")
public class SalesmanController {

  private final SalesmanRepository repository;
  private final SalesmanService service;

  @Autowired
  public SalesmanController(SalesmanRepository repository, SalesmanService service) {
    this.repository = repository;
    this.service = service;
  }

  @GetMapping(path = "/{id}")
  public Optional<Salesman> findById(@PathVariable("id") Long id) {
    return repository.findById(id);
  }

  @GetMapping(path = "/all")
  public List<Salesman> findAll() {
    return repository.findAll();
  }

  @PostMapping
  public Salesman save(@RequestBody Salesman salesman) throws Exception {
    service.validateEmail(salesman.getEmail());

    return repository.save(salesman);
  }

  @PostMapping(path = "/{id}")
  public ResponseEntity<Void> payEmployee(@PathVariable("id") Long salesmanId) throws Exception {
    service.payEmployee(salesmanId);

    return ResponseEntity.ok().build();
  }

  @PutMapping
  public Salesman update(@RequestBody Salesman salesman) {
    return repository.save(salesman);
  }

  @PatchMapping(path = "/improve_commission")
  public ResponseEntity<Void> improveEmployeeCommission(@RequestParam(value = "salesmanId") Long salesmanId,
                                            @RequestParam(value = "increase") int increase) throws Exception {
    service.improveEmployeeCommission(salesmanId, increase);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
