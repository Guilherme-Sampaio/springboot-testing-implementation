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
@Table(name = "sales")
public class Sale implements Serializable {

  @Id
  @Column
  @SequenceGenerator(name = "sales_sequence", sequenceName = "sales_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_sequence")
  private  Long id;

  @Column
  private Long salesmanId;

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
        ", salesmanId=" + salesmanId +
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
    return id.equals(sale.id) && salesmanId.equals(sale.salesmanId) && product.equals(sale.product) && quantity.equals(
        sale.quantity) && value.equals(sale.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, salesmanId, product, quantity, value);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSalesmanId() {
    return salesmanId;
  }

  public void setSalesmanId(Long salesmanId) {
    this.salesmanId = salesmanId;
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
