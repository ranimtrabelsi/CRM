package org.taskspfe.pfe.model.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.taskspfe.pfe.model.soustask.SousTask;
import org.taskspfe.pfe.model.user.UserEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tasks")
public class Task {

    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE ,
            generator = "task_sequence"
    )
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "time_in_hours" , nullable = false)
    private int timeInHours;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "end_at")
    private Date endAt;

    @Column(name = "progress" ,nullable = false)
    private int progress;

    @Column(name = "description" , nullable = false ,  columnDefinition = "TEXT")
    private String description;

    @Column(name = "status")
    private String status ;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @Column(name  = "cause_of_rejection")
    private String causeOfRejection;

    @OneToMany
    @JoinColumn(name = "sous_task_id")
    private List<SousTask> sousTasks;

    @OneToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @OneToOne
    @JoinColumn(name = "assigned_to")
    private UserEntity assignedTo;
}
