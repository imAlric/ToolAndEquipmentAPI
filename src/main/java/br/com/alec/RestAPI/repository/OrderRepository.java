package br.com.alec.RestAPI.repository;

import br.com.alec.RestAPI.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    @Query("FROM Order o WHERE o.id = ?1 AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Optional<Order> findById(Long id);
    @Query("FROM Order o WHERE o.status = br.com.alec.RestAPI.model.Status.Pending AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findPending(Pageable pageable);
    @Query("FROM Order o WHERE o.status = br.com.alec.RestAPI.model.Status.Closed AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findClosed(Pageable pageable);
    @Query("FROM Order o WHERE o.customer.id = ?1 AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findByCustomerId(Long id, Pageable pageable);
    @Query("FROM Order o WHERE o.customer.id = ?1 AND o.status = br.com.alec.RestAPI.model.Status.Pending AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findPendingByCustomerId(Long id, Pageable pageable);
    @Query("FROM Order o WHERE o.customer.id = ?1 AND o.status = br.com.alec.RestAPI.model.Status.Closed AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findClosedByCustomerId(Long id, Pageable pageable);

    @Query("FROM Order o WHERE o.staff.id = ?1 AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findByStaffId(Long id, Pageable pageable);
    @Query("FROM Order o WHERE o.staff.id = ?1 AND o.status = br.com.alec.RestAPI.model.Status.Pending AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findPendingByStaffId(Long id, Pageable pageable);
    @Query("FROM Order o WHERE o.staff.id = ?1 AND o.status = br.com.alec.RestAPI.model.Status.Closed AND o.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Order> findClosedByStaffId(Long id, Pageable pageable);
}
