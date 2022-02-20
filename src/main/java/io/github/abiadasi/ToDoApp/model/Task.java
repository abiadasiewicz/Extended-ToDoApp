package io.github.abiadasi.ToDoApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


     @Entity
     @Table(name = "tasks")
     public class Task {
          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private int id;
          @NotBlank (message = "Task description must not be blank :(")
          private String description;
          private boolean done;

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

          public void setId(int id) {
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
     }
