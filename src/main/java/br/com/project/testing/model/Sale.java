package br.com.project.testing.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale implements Serializable {

  @Id
  @Column
  @SequenceGenerator(name = "sales_sequence", sequenceName = "sales_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_sequence")
  private  Long id;

  @ManyToOne
  @JoinColumn(name = "salesmen_id")
  @JsonIgnore
  private Salesman salesman;

  @Column
  private String product;

  @Column
  private Integer quantity;

  @Column
  private Double value;

  @Override
  public String toString() {
    return "Sale{" +
        "id=" + id +
        ", salesman=" + salesman +
        ", product='" + product + '\'' +
        ", quantity=" + quantity +
        ", value=" + value +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Sale sale = (Sale) o;
    return id.equals(sale.id) && salesman.equals(sale.salesman) && product.equals(sale.product) && quantity.equals(
        sale.quantity) && value.equals(sale.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, salesman, product, quantity, value);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Salesman getSalesman() {
    return salesman;
  }

  public void setSalesman(Salesman salesman) {
    this.salesman = salesman;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }
}
