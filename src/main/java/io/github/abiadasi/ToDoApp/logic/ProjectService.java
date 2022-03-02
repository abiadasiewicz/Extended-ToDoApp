package io.github.abiadasi.ToDoApp.logic;

import io.github.abiadasi.ToDoApp.TaskConfigurationProperties;
import io.github.abiadasi.ToDoApp.model.Project;
import io.github.abiadasi.ToDoApp.model.ProjectRepository;
import io.github.abiadasi.ToDoApp.model.TaskGroupRepository;
import io.github.abiadasi.ToDoApp.model.projection.GroupReadModel;
import io.github.abiadasi.ToDoApp.model.projection.GroupTaskWriteModel;
import io.github.abiadasi.ToDoApp.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    ProjectService(final ProjectRepository projectRepository,
                   final TaskGroupRepository taskGroupRepository,
                   final TaskGroupService taskGroupService, TaskConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
        this.taskGroupService = taskGroupService;
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

        return projectRepository.findById(projectId)
                .map(project -> {
                   var targetGroup = new GroupWriteModel();
                   targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getProjectSteps().stream()
                                    .map(step ->{
                                        var task = new GroupTaskWriteModel();
                                        task.setDescription(step.getDescription());
                                        task.setDeadline(deadline.plusDays(step.getDaysToDeadline()));
                                        return task;
                                    })
                                    .collect(Collectors.toSet()));
                   return taskGroupService.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given ID not found"));
    }
}
