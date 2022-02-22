package io.github.abiadasi.ToDoApp.adapter;

import io.github.abiadasi.ToDoApp.model.Project;
import io.github.abiadasi.ToDoApp.model.ProjectsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface SqlProjectRepository extends ProjectsRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("from Project ps join fetch ps.step")
    List<Project> findAll();
}
