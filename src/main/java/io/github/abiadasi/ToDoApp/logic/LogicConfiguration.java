package io.github.abiadasi.ToDoApp.logic;

import io.github.abiadasi.ToDoApp.TaskConfigurationProperties;
import io.github.abiadasi.ToDoApp.model.ProjectRepository;
import io.github.abiadasi.ToDoApp.model.TaskGroupRepository;
import io.github.abiadasi.ToDoApp.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {
@Bean
    ProjectService projectService(
        final ProjectRepository repository,
        final TaskGroupRepository taskGroupRepository,
        final TaskGroupService taskGroupService,
        final TaskConfigurationProperties config
        ) {
    return new ProjectService(repository, taskGroupRepository, taskGroupService, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository repository,
            final TaskRepository taskRepository
            ) {
        return new TaskGroupService(repository, taskRepository);
    }
}

