package com.fsd.taskmanager.data.dto;

import com.fsd.taskmanager.data.entity.User;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private Integer taskId;

    private String task;

    private Date startDate;

    private Date endDate;

    private Integer priority;

    private String status;

    private ParentTask parentTask;

    private User taskOwner;
}

