package io.github.abiadasi.ToDoApp.logic;

import io.github.abiadasi.ToDoApp.TaskConfigurationProperties;
import io.github.abiadasi.ToDoApp.model.*;
import io.github.abiadasi.ToDoApp.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class ProjectService {

    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    ProjectService(final ProjectRepository projectRepository, final TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll(){
        return projectRepository.findAll();
    }

    public Project save(final Project source){
        return projectRepository.save(source);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        TaskGroup result = projectRepository.findById(projectId)
                .map(project -> {
                   var targetGroup = new TaskGroup();
                   targetGroup.setDescription(project.getDescription());
                   targetGroup.setTaskList(
                           project.getProjectSteps().stream()
                                .map(step ->new Task(
                                        step.getDescription(),
                                        deadline.plusDays(step.getDaysToDeadline())))
                                   .collect(Collectors.toSet()));
                   targetGroup.setProjects(project);
                   return taskGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given ID not found"));

        return new GroupReadModel(result);
    }
}
