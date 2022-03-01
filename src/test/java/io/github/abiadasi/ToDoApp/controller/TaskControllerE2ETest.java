package io.github.abiadasi.ToDoApp.controller;

import io.github.abiadasi.ToDoApp.model.Task;
import io.github.abiadasi.ToDoApp.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repository;

    @Test
    void hhtpGet_returns_allAllTasks(){
        int beforeSize = repository.findAll().size();
        //given
        repository.save(new Task("foo", LocalDateTime.now()));
        repository.save(new Task("bar", LocalDateTime.now()));

        //when
        Task[] result = restTemplate.getForObject("http://localhost:"+port+"/tasks", Task[].class);

        //then
        assertThat(result).hasSize(2+beforeSize);

    }
}