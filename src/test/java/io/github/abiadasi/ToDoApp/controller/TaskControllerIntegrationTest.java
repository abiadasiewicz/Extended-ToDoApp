package io.github.abiadasi.ToDoApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.abiadasi.ToDoApp.model.Task;
import io.github.abiadasi.ToDoApp.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    ObjectMapper objectMapper; // = new ObjectMapper();

    @Test
    void hhtpGet_returnsGivenTask() throws Exception {
        //given
        int id = taskRepository.save(new Task("foo", LocalDateTime.now())).getId();

        //expect
        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void hhtpGet_returnsAllTasks() throws Exception {
        //given
        int sizeBefore = taskRepository.findAll().size();
        taskRepository.save(new Task("foo", LocalDateTime.now()));
        taskRepository.save(new Task("bar", LocalDateTime.now()));

        //expect
        mockMvc.perform(get("/tasks/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.*", hasSize(2+sizeBefore)));
    }

    @Test
    void hhtpPut_saveTask() throws Exception {

        //given
        final var task = taskRepository.save(new Task("foo", LocalDateTime.now()));

        //expect
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
