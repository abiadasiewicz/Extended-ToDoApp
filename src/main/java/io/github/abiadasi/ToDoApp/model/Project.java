package io.github.abiadasi.ToDoApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Entity
@Table(name = "projects")
public class Project {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     @NotBlank (message = "Project's description must not be blank :(")
     private String description;
     @OneToMany(mappedBy = "project")
     private Set<TaskGroup> groups;
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
     private Set<ProjectStep> steps;

     public Project() {

     }

     Project(final int id, final String description, final boolean done) {
          this.id = id;
          this.description = description;
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

     Set<TaskGroup> getGroups() {
          return groups;
     }

     void setGroups(final Set<TaskGroup> groups) {
          this.groups = groups;
     }

     Set<ProjectStep> getProjectSteps() {
          return steps;
     }

     void setProjectSteps(final Set<ProjectStep> steps) {
          this.steps = steps;
     }
}
