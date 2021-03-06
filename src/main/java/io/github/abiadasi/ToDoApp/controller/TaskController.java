package io.github.abiadasi.ToDoApp.controller;

import io.github.abiadasi.ToDoApp.model.Task;
import io.github.abiadasi.ToDoApp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository){
        this.repository = repository;
    }

    @GetMapping(params = {"!sort", "!size", "!page"})
    ResponseEntity <?> readAllTasks(){
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(params = "!sort")
    ResponseEntity <List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pagable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping(params = "sort")
    ResponseEntity<List<Task>> readAllTasks(Sort sort){
        logger.info("Custom pagable");
        return ResponseEntity.ok(repository.findAll(sort));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> {
                task.updateFrom(toUpdate);
                repository.save(task);
        });
        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate){
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Transactional
    @PatchMapping
    public ResponseEntity<Task> toggleTask(@RequestBody int id){
        if(!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state){
        return ResponseEntity.ok(
                repository.findByDone(state)
        );
    }

}
