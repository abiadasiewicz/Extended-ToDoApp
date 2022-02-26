package io.github.abiadasi.ToDoApp.logic;

import io.github.abiadasi.ToDoApp.model.TaskGroup;
import io.github.abiadasi.ToDoApp.model.TaskGroupRepository;
import io.github.abiadasi.ToDoApp.model.TaskRepository;
import io.github.abiadasi.ToDoApp.model.projection.GroupReadModel;
import io.github.abiadasi.ToDoApp.model.projection.GroupWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    TaskGroupService(final TaskGroupRepository repository, TaskRepository taskRepository){
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undone tasks. Dome all the tasks first!");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Task Group with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
