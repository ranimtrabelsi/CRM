package org.taskspfe.pfe.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.model.task.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task , Integer> {

    @Query(value = "select t from Task t where t.id = :id")
    Optional<Task> fetchTaskById(@Param("id") final long id);


    @Query(value = "select t from Task t")
    List<Task> fetchAllTasks();

    @Query(value = "select t from Task t where t.assignedTo.id = :userId")
    List<Task> fetchAllTasksAssignedToUser(@Param("userId") final UUID userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.createAt >= :start AND t.createAt < :end")
    long countTasksByDay(LocalDateTime start, LocalDateTime end);

    @Modifying
    @Transactional
    @Query(value = "delete from Task t where t.id = :id")
    void deleteTaskById(@Param("id") final long id);

}
