package com.fsd.taskmanager.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name="manager_Id")
    private User manager;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Task> taks;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<ParentTask> parentTasks;

    @Transient
    private Integer managerId;

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", project='" + project + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                ", manager=" + manager +
                ", managerId=" + managerId +
                '}';
    }
}
