package io.github.abiadasi.ToDoApp.model.projection;

import io.github.abiadasi.ToDoApp.model.Task;

import java.time.LocalDateTime;

public class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Task toTask(){
        var result = new Task(description, deadline);
        return result;
    }
}
