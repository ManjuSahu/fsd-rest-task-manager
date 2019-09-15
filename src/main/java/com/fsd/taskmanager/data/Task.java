package com.fsd.taskmanager.data;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tasks")
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task")
    private String task;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ParentTask parentTask;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Transient
    private Integer parentTaskId;

    @Transient
    private Integer projectId;
}
