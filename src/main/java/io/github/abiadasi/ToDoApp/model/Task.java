package io.github.abiadasi.ToDoApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


     @Entity
     @Table(name = "tasks")
     public class Task{
          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private int id;
          @NotBlank (message = "Task's description must not be blank :(")
          private String description;
          private boolean done;
          private LocalDateTime deadline;
          @Embedded
          private Audit audit = new Audit();
          @ManyToOne
          @JoinColumn(name = "task_group_id")
          private TaskGroup group;

          public Task(){
          }

          public Task(String description, LocalDateTime deadline, TaskGroup group) {
               this.deadline = deadline;
               this.description = description;
               if(group != null) {
                    this.group = group;
               }
          }

          public Task(String description, LocalDateTime deadline) {
               this.deadline = deadline;
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

          TaskGroup getGroup() {
               return group;
          }

          void setGroup(final TaskGroup group) {
               this.group = group;
          }

          public void updateFrom(final Task sourceTask){
               description = sourceTask.description;
               done = sourceTask.done;
               deadline = sourceTask.deadline;
               group = sourceTask.group;
          }
     }
