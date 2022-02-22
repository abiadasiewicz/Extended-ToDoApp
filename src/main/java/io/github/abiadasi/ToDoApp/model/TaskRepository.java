package io.github.abiadasi.ToDoApp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

    public interface TaskRepository {
        List<Task> findAll();
        
        Optional<Task> findById(Integer id);

        Task save(Task entity);

        List<Task> findAll(Sort sort);

        boolean existsById(Integer id);

        Page<Task> findAll(Pageable page);

        boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

        List<Task> findByDone(boolean done);
    }
