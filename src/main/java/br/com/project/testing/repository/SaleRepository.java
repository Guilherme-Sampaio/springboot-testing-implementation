package br.com.project.testing.repository;

import java.util.List;

import br.com.project.testing.model.Sale;
import br.com.project.testing.model.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

  List<Sale> findBySalesman(Salesman salesman);

  @Query(value = "select query.salesmen "
      + "from (select salesmen, sum(sales.quantity * sales.value) as total from salesmen, sales "
      + "where salesmen.id = sales.salesmen_id "
      + "group by salesmen "
      + "order by total desc limit 1) as query;", nativeQuery = true)
  Salesman findWhoSoldMore();

  int countBySalesman(Salesman salesman);

  @Query("select count(quantity) from Sale where product = :product")
  int countSalesByProduct(@Param("product") String product);

  @Query("select max(quantity * value) from Sale")
  Sale findBiggestSale();
}
