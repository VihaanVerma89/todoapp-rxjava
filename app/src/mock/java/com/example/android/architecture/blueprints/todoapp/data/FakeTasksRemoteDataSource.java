package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;

import java.util.LinkedHashMap;
import java.util.Map;

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
}
