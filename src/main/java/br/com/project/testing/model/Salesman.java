package br.com.project.testing.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "salesmen")
public class Salesman implements Serializable {

  @Id
  @Column
  @SequenceGenerator(name = "salesman_sequence", sequenceName = "salesman_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesman_sequence")
  private Long id;

  @Column
  private String name;

  @Column
  private String email;

  @Column
  private Double salary;

  @Column
  private Integer commission;

  @Override
  public String toString() {
    return "Salesman{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", salary=" + salary +
        ", commission=" + commission +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Salesman salesman = (Salesman) o;
    return id.equals(salesman.id) && name.equals(salesman.name) && Objects.equals(email, salesman.email)
        && salary.equals(salesman.salary) && Objects.equals(commission, salesman.commission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, salary, commission);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Double getSalary() {
    return salary;
  }

  public Integer getCommission() {
    return commission;
  }
}
