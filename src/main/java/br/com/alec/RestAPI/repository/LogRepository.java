package br.com.alec.RestAPI.repository;

import br.com.alec.RestAPI.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LogRepository extends JpaRepository<Log, Long>{
    @Override
    Page<Log> findAll(Pageable pageable);
    @Query("FROM Log l WHERE l.created_at BETWEEN ?1 AND ?2")
    Page<Log> findByDate(Date date1, Date date2, Pageable pageable);
}
