package io.github.abiadasi.ToDoApp.logic;

import io.github.abiadasi.ToDoApp.TaskConfigurationProperties;
import io.github.abiadasi.ToDoApp.model.*;
import io.github.abiadasi.ToDoApp.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when configured to allow just one group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_and_openGroupsExists_throwsIllegalStateException() {
        //given
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);

        TaskConfigurationProperties mockConfig = configurationReturning(false);

        var toTest = new ProjectService(null, mockGroupRepository, null, mockConfig);

        //when
        var exception = catchThrowable(()-> toTest.createGroup(LocalDateTime.now(), 1));
        //then -> checking if the Exception has the correct message
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configuration ok and no projects for a given id")
    void createGroup_configOk_and_noProjects_throwsIllegalArgumentException() {
        //given
        TaskConfigurationProperties mockConfig = configurationReturning(true);

        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        var toTest = new ProjectService(mockRepository, null, null, mockConfig);

        //when
        var exception = catchThrowable(()-> toTest.createGroup(LocalDateTime.now(), 0));
        //then -> checking if the Exception has the correct message
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given ID not found");
    }


    @Test
    @DisplayName("Should throw IllegalArgumentException when configured to allow just one group and no groups and no projects")
    void createGroup_noMultipleGroupsConfig_and_noUndoneGroupsExists_noProjects_throwsIllegalArgumentException() {
        //given
        TaskConfigurationProperties mockConfig = configurationReturning(false);

        TaskGroupRepository mockGroup = groupRepositoryReturning(false);

        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        var toTest = new ProjectService(mockRepository, mockGroup, null, mockConfig);

        //when
        var exception = catchThrowable(()-> toTest.createGroup(LocalDateTime.now(), 0));
        //then -> checking if the Exception has the correct message
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given ID not found");
    }

    @Test
    @DisplayName("Schould create a new group from project")
    void createGroup_configOk_existingProjectCreatesAndSavesNewGroup(){
        //given
        var today = LocalDate.now().atStartOfDay();
        var project = projectWith("eloelo", Set.of(-1, -2));
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));

        InMemoryGroupRepository memoryGroupRepo = inMemoryGroupRepository();
        var serviceInMemRepo = dummyGroupService();
        int countBeforeCall = memoryGroupRepo.count();

        TaskConfigurationProperties mockConfig = configurationReturning(true);

        var toTest = new ProjectService(mockRepository, memoryGroupRepo, serviceInMemRepo, mockConfig);

        //when
        GroupReadModel result = toTest.createGroup(today, 1);

        //then
        assertThat(result.getDescription().compareTo("eloelo"));
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(countBeforeCall)
                .isEqualTo(inMemoryGroupRepository().count());
    }

    private TaskGroupService dummyGroupService() {
        return new TaskGroupService(inMemoryGroupRepository(), null);
    }

    private Project projectWith(String projectDescription, Set<Integer> daysToDeadline){
        final Set<ProjectStep> steps = daysToDeadline.stream()
                .map(days -> {
                    var step = mock(ProjectStep.class);
                    when(step.getDescription()).thenReturn("foo");
                    when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet());
        var result = mock(Project.class);
        when(result.getDescription()).thenReturn(projectDescription);
        when(result.getProjectSteps()).thenReturn(steps);
        return result;
    }


    private TaskConfigurationProperties configurationReturning(final boolean result) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);

        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }


    private TaskGroupRepository groupRepositoryReturning(boolean result) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private InMemoryGroupRepository inMemoryGroupRepository(){
        return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements TaskGroupRepository{

            private int index = 0;
            private Map<Integer, TaskGroup> map = new HashMap<>();
            @Override
            public List<TaskGroup> findAll() {
                return new ArrayList<>(map.values());
            }

            @Override
            public Optional<TaskGroup> findById(final Integer id) {
                return Optional.ofNullable(map.get(id));
            }

            @Override
            public TaskGroup save(final TaskGroup entity) {
                if(entity.getId() == 0) {
                    try {
                       var field = TaskGroup.class.getDeclaredField("id");
                       field.setAccessible(true);
                       field.set(entity, ++index);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                map.put(entity.getId(), entity);
                return entity;
            }

            @Override
            public boolean existsByDoneIsFalseAndProject_Id(final Integer project_Id) {
                return map.values().stream()
                        .filter(group -> !group.isDone())
                        .anyMatch(group -> group.getProject() != null && group.getProject().getId() == project_Id);
            }

            public int count(){
                return map.values().size();
            }
    }
}