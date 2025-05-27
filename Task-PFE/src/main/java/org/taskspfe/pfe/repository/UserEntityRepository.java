package org.taskspfe.pfe.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.model.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@Transactional(readOnly = true)
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {


    @Query(value = "SELECT U FROM UserEntity  U WHERE U.id = :id")
    Optional<UserEntity> fetchUserWithId(@Param("id") UUID id);
    @Query(value = "SELECT U FROM UserEntity U WHERE  U.email = :email ")
    Optional<UserEntity> fetchUserWithEmail(@Param("email") String email);

    @Query(value = "SELECT U FROM UserEntity U")
    List<UserEntity> fetchAllUsers();

    @Query("SELECT U FROM UserEntity U WHERE U.email LIKE :prefix%")
    List<UserEntity> fetchUsersWithEmailPrefix(@Param("prefix")String prefix);

    @Query(value = "SELECT EXISTS(SELECT U FROM UserEntity U WHERE  U.email = :email) AS RESULT")
    Boolean isEmailRegistered(@Param("email")String email);

    @Query(value = "select u from UserEntity u where u.role.name = 'ADMIN'")
    List<UserEntity> fetchAllAdmins();

    @Query(value = "select u from UserEntity u where u.role.name = 'CLIENT' ")
    List<UserEntity> fetchAllClients();

    @Query(value = "select u from UserEntity u where u.role.name = 'TECHNICIAN' ")
    List<UserEntity> fetchAllTechnicians();

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role.name = 'CLIENT' AND u.createdAt >= :start AND u.createdAt < :end")
    long countClientsByMonth(LocalDateTime start, LocalDateTime end);
    @Query(value = "SELECT COUNT(U) FROM UserEntity U")
    long getTotalUserEntityCount();

}
