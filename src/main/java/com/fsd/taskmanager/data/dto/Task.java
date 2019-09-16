package com.fsd.taskmanager.data.dto;

import com.fsd.taskmanager.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
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

