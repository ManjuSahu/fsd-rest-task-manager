package com.fsd.taskmanager.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "task_id")
    private Integer taskId;


}
