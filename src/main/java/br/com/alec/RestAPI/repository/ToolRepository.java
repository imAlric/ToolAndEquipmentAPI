package br.com.alec.RestAPI.repository;

import br.com.alec.RestAPI.model.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    @Query("FROM Tool t WHERE t.status = br.com.alec.RestAPI.model.Status.Active AND t.status != br.com.alec.RestAPI.model.Status.Deleted")
    Page<Tool> findActive(Pageable pageable);
}
