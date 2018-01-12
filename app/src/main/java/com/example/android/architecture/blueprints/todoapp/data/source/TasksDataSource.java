package com.example.android.architecture.blueprints.todoapp.data.source;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by vihaanverma on 05/01/18.
 */

public interface TasksDataSource {
    void saveTask(@NonNull Task task);
    Flowable<List<Task>> getTasks();
    Flowable<Optional<Task>> getTask(@NonNull String taskId);
    void deleteAllTasks();

    void completeTask(Task task);

    void completeTask(String id);

    void activateTask(Task task);

    void activateTask(@NonNull String taskId);

    void clearCompletedTasks();
}
