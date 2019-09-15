package com.fsd.taskmanager.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "tasks")
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {
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

    @ManyToOne
    @JoinColumn(name = "task_owner_id")
    private User taskOwner;

    @Transient
    private Integer parentTaskId;

    @Transient
    private Integer projectId;

    @Transient
    private Integer taskOwnerId;

}
