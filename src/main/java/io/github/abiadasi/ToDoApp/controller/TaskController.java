package io.github.abiadasi.ToDoApp.controller;

import io.github.abiadasi.ToDoApp.model.Task;
import io.github.abiadasi.ToDoApp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository){
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!size", "!page"})
    ResponseEntity <?> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/tasks", params = "!sort")
    ResponseEntity <List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pagable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping(value = "/tasks", params = "sort")
    ResponseEntity<?> readAllTasks(Sort sort){
        logger.info("Custom pagable");
        return ResponseEntity.ok(repository.findAll(sort));
    }


    //TODO does not working properly
//    @RequestMapping(value = "/tasks/search/done")
//        ResponseEntity<?> readTasksByDone(boolean isDone){
//        logger.warn(("Exposing all tasks by done flag"));
//
//        return ResponseEntity.ok(repository.findByDone(isDone));
//    }
}
