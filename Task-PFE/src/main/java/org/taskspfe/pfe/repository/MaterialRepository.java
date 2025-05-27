package org.taskspfe.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.model.material.Material;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MaterialRepository extends JpaRepository<Material , Integer> {


    @Query(value = "select m from Material m where m.id = :id")
    Optional<Material> fetchMaterialById(@Param("id") long id);

    @Query(value = "select m from Material m where m.client.id = :id")
    List<Material> fetchMaterialsByClientId(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query(value = "delete from Material m where m.id = :id")
    void deleteMaterialById(@Param("id") long id);
}
