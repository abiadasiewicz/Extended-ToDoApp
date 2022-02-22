package io.github.abiadasi.ToDoApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "project_steps")
public class ProjectStep {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     @NotBlank (message = "Project step's description must not be blank :(")
     private String description;
     private int daysToDeadline;
     @ManyToOne()
     @JoinColumn(name = "project_id")
     private Project projects;

     public ProjectStep() {

     }

     ProjectStep(final int id, final String description, final boolean done) {
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

     public Project getProjects() {
          return projects;
     }

     void setProjects(final Project projects) {
          this.projects = projects;
     }

     public int getDaysToDeadline() {
          return daysToDeadline;
     }

     void setDaysToDeadline(final int daysToDeadline) {
          this.daysToDeadline = daysToDeadline;
     }
}
