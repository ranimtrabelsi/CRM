package org.taskspfe.pfe.service.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.dto.task.TaskDTO;
import org.taskspfe.pfe.dto.task.TaskDTOMapper;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.model.soustask.SousTask;
import org.taskspfe.pfe.model.task.Task;
import org.taskspfe.pfe.model.task.TaskStatus;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.repository.SousTaskRepository;
import org.taskspfe.pfe.repository.TaskRepository;
import org.taskspfe.pfe.service.user.UserEntityService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskDTOMapper taskDTOMapper;
    private final UserEntityService userEntityService;
    private final SousTaskRepository sousTaskRepository;
    public TaskServiceImpl(TaskRepository taskRepository, TaskDTOMapper taskDTOMapper, UserEntityService userEntityService, SousTaskRepository sousTaskRepository) {
        this.taskRepository = taskRepository;
        this.taskDTOMapper = taskDTOMapper;
        this.userEntityService = userEntityService;
        this.sousTaskRepository = sousTaskRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<CustomResponseEntity<TaskDTO>> createTask(UserDetails userDetails, UUID assignedToId, Task newTask) {
        final UserEntity createdBy = userEntityService.getUserEntityByEmail(userDetails.getUsername());
        UserEntity assignedTo = null;
        if (assignedToId != null) {
            assignedTo = userEntityService.getUserEntityById(assignedToId);
        }

        newTask.setCreateAt(LocalDateTime.now());
        newTask.setCreatedBy(createdBy);
        newTask.setAssignedTo(assignedTo);
        newTask.setAccepted(false);
        newTask.setStatus(TaskStatus.SUSPENDED.toString());

        final TaskDTO task = taskDTOMapper.apply(taskRepository.save(newTask));

        final CustomResponseEntity<TaskDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.CREATED, task);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(long taskId, int progress, UUID assignedToId,String status) {
        Task savedTask = getTaskById(taskId);
        final UserEntity assignedTo = (assignedToId != null) ? userEntityService.getUserEntityById(assignedToId) : savedTask.getAssignedTo();

        savedTask.setProgress( (progress != 0) ? progress : savedTask.getProgress() );
        savedTask.setStatus( (status != null) ? status : savedTask.getStatus() );
        savedTask.setAssignedTo(assignedTo);

        final Task updatedTask = taskRepository.save(savedTask);
        final TaskDTO task = taskDTOMapper.apply(updatedTask);
        final CustomResponseEntity<TaskDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, task);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateTask(long taskId, UUID assignedToId,Task taskDetails) {
        Task savedTask = getTaskById(taskId);
        final UserEntity assignedTo = (assignedToId != null) ? userEntityService.getUserEntityById(assignedToId) : savedTask.getAssignedTo();

        savedTask.setName(taskDetails.getName());
        savedTask.setTimeInHours(taskDetails.getTimeInHours());
        savedTask.setProgress(taskDetails.getProgress());
        savedTask.setDescription(taskDetails.getDescription());
        savedTask.setAssignedTo(assignedTo);
        savedTask.setEndAt(taskDetails.getEndAt());

        if(savedTask.isAccepted())
            savedTask.setStatus(taskDetails.getStatus());

        final Task updatedTask = taskRepository.save(savedTask);
        final TaskDTO task = taskDTOMapper.apply(updatedTask);
        final CustomResponseEntity<TaskDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, task);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> fetchTaskByCurrentUser(UserDetails userDetails) {
        final UserEntity user = userEntityService.getUserEntityByEmail(userDetails.getUsername());

        final List<TaskDTO> tasks = taskRepository.fetchAllTasksAssignedToUser(user.getId()).stream().map(taskDTOMapper).toList();
        final CustomResponseEntity<List<TaskDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, tasks);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> deleteTask(long taskId) {
        Task savedTask = getTaskById(taskId);
        sousTaskRepository.deleteAll(savedTask.getSousTasks());
        taskRepository.deleteTaskById(taskId);
        final TaskDTO task = taskDTOMapper.apply(savedTask);
        final CustomResponseEntity<TaskDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, task);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> getAllTasks() {
        final List<Task> tasks = taskRepository.fetchAllTasks();
        final List<TaskDTO> taskDTOS = tasks.stream().map(taskDTOMapper).toList();
        final CustomResponseEntity<List<TaskDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, taskDTOS);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<List<TaskDTO>>> searchTasks(Long taskId,String taskName, String taskDescription, String taskStatus, Boolean isAccepted,UUID taskCreatedBy, UUID taskAssignedTo) {
        List<TaskDTO> filteredTasks = filterTasksString(taskId,taskName, taskDescription, taskStatus, isAccepted,taskCreatedBy, taskAssignedTo);

        CustomResponseEntity<List<TaskDTO>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, filteredTasks);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }
    private List<TaskDTO> filterTasksString(Long taskId,String taskName, String taskDescription, String taskStatus, Boolean isAccepted,UUID taskCreatedBy, UUID taskAssignedTo) {
        return taskRepository.findAll().stream()
                .filter(task -> taskId == null || task.getId() == taskId)
                .filter(task -> taskName == null || task.getName().toLowerCase().contains(taskName.toLowerCase()))
                .filter(task -> taskDescription == null || task.getDescription().toLowerCase().contains(taskDescription.toLowerCase()))
                .filter(task -> taskStatus == null || task.getStatus().equalsIgnoreCase(taskStatus))
                .filter(task -> isAccepted == null || task.isAccepted() == isAccepted)
                .filter(task -> taskCreatedBy == null || task.getCreatedBy().getId().equals(taskCreatedBy))
                .filter(task -> taskAssignedTo == null || task.getAssignedTo().getId().equals(taskAssignedTo))
                .map(taskDTOMapper)
                .collect(Collectors.toList());
    }


    @Override
    public ResponseEntity<CustomResponseEntity<Map<LocalDate, Long>>> getTaskCountByDayInMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        Map<LocalDate, Long> taskCountByDay = new LinkedHashMap<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            LocalDateTime startDate = date.atStartOfDay();
            LocalDateTime endDate = date.plusDays(1).atStartOfDay();
            long count = taskRepository.countTasksByDay(startDate, endDate);
            taskCountByDay.put(date, count);
        }

        final CustomResponseEntity<Map<LocalDate, Long>> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, taskCountByDay);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }


    @Override
    public Task getTaskById(long taskId) {
        return taskRepository.fetchTaskById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task not found with id: " + taskId)
        );
    }

    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> acceptTask(long taskId) {
        final Task task = getTaskById(taskId);
        task.setAccepted(true);
        task.setStatus(TaskStatus.TODO.toString());
        final TaskDTO updatedTask = taskDTOMapper.apply(taskRepository.save(task));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK, updatedTask));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> rejectTask(long taskId, String causeOfRejection) {
        final Task task = getTaskById(taskId);
        task.setAccepted(false);
        task.setCauseOfRejection(causeOfRejection);
        task.setStatus(TaskStatus.SUSPENDED.toString());
        final TaskDTO updatedTask = taskDTOMapper.apply(taskRepository.save(task));
        return ResponseEntity.ok(new CustomResponseEntity<>(HttpStatus.OK, updatedTask));
    }

    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> addSousTask(long taskId, SousTask sousTask) {
        final Task savedTask = getTaskById(taskId);
        final SousTask savedSousTask = sousTaskRepository.save(sousTask);
        savedTask.getSousTasks().add(savedSousTask);
        final TaskDTO task = taskDTOMapper.apply(taskRepository.save(savedTask));
        final CustomResponseEntity<TaskDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, task);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> removeSousTask(long taskId,long sousTaskId) {
        final Task savedTask = getTaskById(taskId);
        sousTaskRepository.deleteSousTaskById(sousTaskId);
        savedTask.getSousTasks().removeIf(sousTask -> sousTask.getId() == sousTaskId);
        final TaskDTO task = taskDTOMapper.apply(taskRepository.save(savedTask));
        final CustomResponseEntity<TaskDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, task);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<TaskDTO>> updateSousTask(long taskId,long sousTaskId, SousTask sousTask) {
        final Task savedTask = getTaskById(taskId);
        final SousTask savedSousTask = savedTask.getSousTasks().stream()
                .filter(sousTask1 -> sousTask1.getId() == sousTaskId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("SousTask not found with id: " + sousTaskId));
        savedSousTask.setTitle(sousTask.getTitle());
        savedSousTask.setDescription(sousTask.getDescription());
        final TaskDTO task = taskDTOMapper.apply(taskRepository.save(savedTask));
        final CustomResponseEntity<TaskDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, task);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }


}
