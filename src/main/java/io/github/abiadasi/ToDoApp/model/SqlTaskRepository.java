package io.github.abiadasi.ToDoApp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    //Spring may do this for us, it is just for knowledge how to make own query
//    @Override
//    @Query(nativeQuery = true, value = "select count(*) > 0 from TASKS where id =:id")
//    boolean existsById(@Param("id") Integer id);
}
