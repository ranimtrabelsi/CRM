package org.taskspfe.pfe.dto.task;

import org.taskspfe.pfe.dto.user.UserEntityDTO;
import org.taskspfe.pfe.model.soustask.SousTask;
import org.taskspfe.pfe.model.task.TaskStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record TaskDTO (
        long id,
        String name,
        int timeInHours,
        int progress,
        String description,
        String status,
        boolean isAccepted,
        String causeOfRejection,
        List<SousTask> sousTasks,
        LocalDateTime createAt,
        Date endAt,
        UserEntityDTO createdBy,
        UserEntityDTO assignedTo
){
}
