package com.example.android.architecture.blueprints.todoapp.data;

import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;

/**
 * Created by vihaanverma on 05/01/18.
 */

public class FakeTasksRemoteDataSource implements TasksDataSource {
    private static FakeTasksRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private FakeTasksRemoteDataSource() {}

    public static FakeTasksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeTasksRemoteDataSource();
        }
        return INSTANCE;
    }
}
