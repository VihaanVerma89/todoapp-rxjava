package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.google.common.base.Optional;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

/**
 * Created by vihaanverma on 05/01/18.
 */

public class FakeTasksRemoteDataSource implements TasksDataSource {
    private static FakeTasksRemoteDataSource INSTANCE;

    private static final Map<String, Task> TASKS_SERVICE_DATA = new LinkedHashMap<>();
    // Prevent direct instantiation.
    private FakeTasksRemoteDataSource() {}

    public static FakeTasksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeTasksRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        TASKS_SERVICE_DATA.put(task.getId(), task);
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        Collection<Task> values = TASKS_SERVICE_DATA.values();
        return Flowable.fromIterable(values).toList().toFlowable();
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        Task task = TASKS_SERVICE_DATA.get(taskId);
        return Flowable.just(Optional.of(task));
    }

    @Override
    public void deleteAllTasks() {
        TASKS_SERVICE_DATA.clear();
    }

    @Override
    public void completeTask(Task task) {
        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), true);
        TASKS_SERVICE_DATA.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(String taskId) {
        Task task = TASKS_SERVICE_DATA.get(taskId);
        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), true);
        TASKS_SERVICE_DATA.put(taskId, completedTask);
    }
}
