package br.com.project.testing.repository;

import br.com.project.testing.model.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesmanRepository extends JpaRepository<Salesman, Long> {

  @Modifying
  @Query(value = "update Salesman set commission = :newCommission where id = :salesmanId")
  int updateSalesmanCommission(@Param("newCommission") int newCommission, @Param("salesmanId") Long salesmanId);

  @Query(value = "select salesmen.* from salesmen, sales "
      + "where salesmen.id = sales.salesmen_id "
      + "group by salesmen.id "
      + "order by sum(sales.quantity * sales.value) desc limit 1 ", nativeQuery = true)
  Salesman findWhoSoldMore();
}
