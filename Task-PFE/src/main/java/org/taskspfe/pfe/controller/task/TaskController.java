package org.taskspfe.pfe.controller.task;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.taskspfe.pfe.dto.task.TaskDTO;
import org.taskspfe.pfe.model.soustask.SousTask;
import org.taskspfe.pfe.model.task.Task;
import org.taskspfe.pfe.service.email.EmailSenderService;
import org.taskspfe.pfe.service.task.TaskService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("api/v1/task")
@RestController
public class TaskController{

    private final TaskService taskService;
    private final EmailSenderService emailSenderService;

    public TaskController(TaskService taskService, EmailSenderService emailSenderService) {
        this.taskService = taskService;
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/admin")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> createTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "assignedToId" , required = false) UUID assignedToId,
            @Valid @RequestBody Task newTask
    ) {
        return taskService.createTask(userDetails, assignedToId, newTask);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/all/search")
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> searchTasks(
            @RequestParam(name = "taskId" ,required = false) Long taskId,
            @RequestParam(name = "taskName" , required = false) String taskName,
            @RequestParam(name = "taskDescription" , required = false) String taskDescription,
            @RequestParam(name = "taskStatus" , required = false) String taskStatus,
            @RequestParam(name = "isAccepted" , required = false) Boolean isAccepted,
            @RequestParam(name = "taskCreatedBy" , required = false) UUID taskCreatedBy,
            @RequestParam(name = "taskAssignedTo" , required = false) UUID taskAssignedTo
    ) {
        return taskService.searchTasks(taskId,taskName, taskDescription, taskStatus, isAccepted,taskCreatedBy, taskAssignedTo);
    }

    @GetMapping("/all/current")
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> fetchTaskByCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return taskService.fetchTaskByCurrentUser(userDetails);
    }

    @PutMapping("/technician/{taskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            @PathVariable("taskId") long taskId,
            @RequestParam(name = "progress" , required = false) int progress,
            @RequestParam(name = "assignedToId" , required = false) UUID assignedToId,
            @RequestParam(name = "status" , required = false) String status
    ) {
        return taskService.updateTask(taskId, progress, assignedToId, status);
    }

    @PutMapping("/admin/{taskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(
            @PathVariable("taskId") long taskId,
            @RequestParam(name = "assignedToId" , required = false) UUID assignedToId,
            @Valid @RequestBody Task updatedTask
    ) {
        return taskService.updateTask(taskId, assignedToId, updatedTask);
    }

    @DeleteMapping("/admin/{taskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> deleteTask(@PathVariable("taskId") final long taskId) {
        return taskService.deleteTask(taskId);
    }
    @GetMapping("/admin/count-tasks-daily")
    public ResponseEntity<CustomResponseEntity<Map<LocalDate, Long>>> countTasksByDayInMonth(
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ) {
        return taskService.getTaskCountByDayInMonth(year, month);
    }

    @PutMapping("/admin/{taskId}/accept")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> acceptTask(@PathVariable("taskId") long taskId) {
        return taskService.acceptTask(taskId);
    }
    @PutMapping("/admin/{taskId}/reject")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> rejectTask(
            @PathVariable("taskId") long taskId,
            @RequestParam(name = "causeOfRejection" , required = false) String causeOfRejection
    ) {
        return taskService.rejectTask(taskId,causeOfRejection);
    }

    @PostMapping("/technician/{taskId}/sous-tasks")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> addSousTask(
            @PathVariable long taskId,
            @RequestBody SousTask sousTask) {
        return taskService.addSousTask(taskId, sousTask);
    }

    @DeleteMapping("/technician/{taskId}/sous-tasks/{sousTaskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> removeSousTask(
            @PathVariable long taskId,
            @PathVariable long sousTaskId) {
        return taskService.removeSousTask(taskId, sousTaskId);
    }

    @PutMapping("/technician/{taskId}/sous-tasks/{sousTaskId}")
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateSousTask(
            @PathVariable long taskId,
            @PathVariable long sousTaskId,
            @RequestBody SousTask sousTask) {
        return taskService.updateSousTask(taskId, sousTaskId, sousTask);
    }

}
