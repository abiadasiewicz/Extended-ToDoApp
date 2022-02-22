package io.github.abiadasi.ToDoApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Entity
@Table(name = "task_group")
public class TaskGroup {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     @NotBlank (message = "Task group's description must not be blank :(")
     private String description;
     private boolean done;
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
     private Set<Task> taskList;
     @ManyToOne
     private Project projects;

     public TaskGroup() {

     }

     TaskGroup(final int id, final String description, final boolean done) {
          this.id = id;
          this.description = description;
          this.done = done;
     }

     public int getId() {
          return id;
     }

     void setId(int id) {
          this.id = id;
     }

     public String getDescription() {
          return description;
     }

     void setDescription(String description) {
          this.description = description;
     }

     public boolean isDone() {
          return done;
     }

     public void setDone(boolean done) {
          this.done = done;
     }

     public Set<Task> getTaskList() {
          return taskList;
     }

     void setTaskList(final Set<Task> taskList) {
          this.taskList = taskList;
     }

     public Project getProjects() {
          return projects;
     }

     void setProjects(final Project projects) {
          this.projects = projects;
     }
}
