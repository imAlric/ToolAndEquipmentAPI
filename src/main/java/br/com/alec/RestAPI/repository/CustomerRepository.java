package br.com.alec.RestAPI.repository;

import br.com.alec.RestAPI.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("FROM Customer c WHERE c.status = br.com.alec.RestAPI.model.Status.Active AND c.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Customer> findActive(Pageable pageable);
    @Query("FROM Customer c WHERE c.cpf = ?1 AND c.status != br.com.alec.RestAPI.model.Status.Deleted")
    Optional<Customer> findByCPF(String cpf);
}
