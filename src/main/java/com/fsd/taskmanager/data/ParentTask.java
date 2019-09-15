package com.fsd.taskmanager.data;

import lombok.*;

import javax.persistence.*;

@Entity(name = "parent_tasks")
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "parent_task")
    private String parentTask;
}
