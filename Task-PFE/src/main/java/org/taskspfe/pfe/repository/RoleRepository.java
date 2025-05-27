package org.taskspfe.pfe.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.model.role.Role;

import java.util.Optional;
@Repository
@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query(value = "SELECT R FROM Role R WHERE R.name = :name")
    Optional<Role> fetchRoleByName(@Param("name")String name);

    @Query(value = "SELECT R FROM Role R WHERE R.id = :id")
    Optional<Role> fetchRoleById(@Param("id")long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Role R WHERE R.id = :id")
    void deleteRoleById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Role R WHERE R.name = :name")
    void deleteRoleByName(@Param("name")String name);

}

