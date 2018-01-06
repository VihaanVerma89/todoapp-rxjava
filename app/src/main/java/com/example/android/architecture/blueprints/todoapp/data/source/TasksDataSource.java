package com.example.android.architecture.blueprints.todoapp.data.source;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.Task;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by vihaanverma on 05/01/18.
 */

public interface TasksDataSource {
    void saveTask(@NonNull Task task);
    Flowable<List<Task>> getTasks();
}
