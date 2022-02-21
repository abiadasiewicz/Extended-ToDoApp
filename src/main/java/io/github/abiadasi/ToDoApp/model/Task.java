package io.github.abiadasi.ToDoApp.model;

import org.hibernate.boot.model.source.spi.InheritanceType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


     @Entity
     @Table(name = "tasks")
     public class Task extends BaseAuditableEntity{
          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private int id;
          @NotBlank (message = "Task description must not be blank :(")
          private String description;
          private boolean done;
          private LocalDateTime deadline;

          public Task() {

          }

          Task(final int id, final String description, final boolean done) {
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

          public LocalDateTime getDeadline() {
               return deadline;
          }

          void setDeadline(final LocalDateTime deadline) {
               this.deadline = deadline;
          }

          public void updateFrom(final Task sourceTask){
               description = sourceTask.description;
               done = sourceTask.done;
               deadline = sourceTask.deadline;
          }
     }
