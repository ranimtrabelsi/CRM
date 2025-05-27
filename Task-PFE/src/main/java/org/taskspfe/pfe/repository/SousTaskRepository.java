package org.taskspfe.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.model.soustask.SousTask;

@Repository
public interface SousTaskRepository extends JpaRepository<SousTask,Integer> {


    @Modifying
    @Transactional
    @Query("delete from SousTask s where s.id = :id")
    void deleteSousTaskById(@Param("id") long id);

}
