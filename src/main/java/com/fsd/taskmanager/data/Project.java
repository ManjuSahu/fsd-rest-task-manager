package com.fsd.taskmanager.data;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "projects")
@ToString
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "project")
    private String project;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "priority")
    private Integer priority;
    @ManyToOne
    @JoinColumn(name="manager_Id")
    private User manager;
    @Transient
    private Integer managerId;
}
