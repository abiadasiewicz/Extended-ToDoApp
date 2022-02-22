package io.github.abiadasi.ToDoApp.adapter;

import io.github.abiadasi.ToDoApp.model.TaskGroup;
import io.github.abiadasi.ToDoApp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {

    @Override
    @Query("from TaskGroup g join fetch g.taskList")
    List<TaskGroup> findAll();

    @Override
    boolean existsByDoneIsFalseAndProjects_Id(Integer project_Id);
}
