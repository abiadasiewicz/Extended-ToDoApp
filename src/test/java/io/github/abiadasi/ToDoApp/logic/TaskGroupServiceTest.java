package io.github.abiadasi.ToDoApp.logic;

import io.github.abiadasi.ToDoApp.model.TaskGroup;
import io.github.abiadasi.ToDoApp.model.TaskGroupRepository;
import io.github.abiadasi.ToDoApp.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when group has undone tasks")
    void toggleGroup_everyTasksInGroupAreUndone_throwsIllegalStateException() {
        //given
        TaskRepository mockTaskRepository = TaskRepositoryReturns(true);

        var toTest = new TaskGroupService(null, mockTaskRepository);

        //when
        var exception = catchThrowable(()-> toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when group does not exists")
    void toggleGroup_repositoryDoesNotExiststhrowsIllegalArgumentException() {
        //given
        var taskGroup = mock(TaskGroup.class);
        taskGroup.setDone(false);
        var taskGroupRepository = mock(TaskGroupRepository.class);
        when(taskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());

        TaskRepository mockTaskRepository = TaskRepositoryReturns(false);

        var toTest = new TaskGroupService(taskGroupRepository, mockTaskRepository);

        //when
        var exception = catchThrowable(()-> toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given id not found");
    }

    @Test
    @DisplayName("Should toggle task group")
    void toggleGroup_repositoryWorksProperly() {
        //given
        var taskGroup = new TaskGroup();
        taskGroup.setDone(false);
        var taskGroupRepository = mock(TaskGroupRepository.class);
        when(taskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));

        TaskRepository mockTaskRepository = TaskRepositoryReturns(false);

        var toTest = new TaskGroupService(taskGroupRepository, mockTaskRepository);

        //when
        toTest.toggleGroup(0);

        //then
        assertThat(taskGroup.isDone()).isTrue();

    }

    private TaskRepository TaskRepositoryReturns(final boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }
}