package br.com.alec.RestAPI.repository;

import br.com.alec.RestAPI.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("FROM Staff s WHERE s.status = br.com.alec.RestAPI.model.Status.Active AND s.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Staff> findActive(Pageable pageable);
    @Query("FROM Staff s WHERE s.cpf = ?1 AND s.status != br.com.alec.RestAPI.model.Status.Deleted")
    Optional<Staff> findByCPF(String cpf);
}
