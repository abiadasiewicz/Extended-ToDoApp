package io.github.abiadasi.ToDoApp.model.projection;

import io.github.abiadasi.ToDoApp.model.Task;

class GroupTaskReadModel {

    private boolean done;
    private String description;

    GroupTaskReadModel(Task source) {
        done = source.isDone();
        description = source.getDescription();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
