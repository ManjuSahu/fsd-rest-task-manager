package com.fsd.taskmanager.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentTask {

    private Integer parentId;

    private String parentTask;

    private String status;

}
