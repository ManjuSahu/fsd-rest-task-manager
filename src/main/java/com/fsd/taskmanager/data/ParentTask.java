package com.fsd.taskmanager.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "parent_tasks")
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentTask implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "parent_task")
    private String parentTask;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Transient
    private Integer projectId;

}
