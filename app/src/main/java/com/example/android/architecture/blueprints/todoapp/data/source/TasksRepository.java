package com.example.android.architecture.blueprints.todoapp.data.source;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vihaanverma on 05/01/18.
 */

public class TasksRepository implements TasksDataSource {

    private static TasksRepository INSTANCE = null;


    private final TasksDataSource mTasksRemoteDataSource;

    private final TasksDataSource mTasksLocalDataSource;

    // Prevent direct instantiation.
    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                            @NonNull TasksDataSource tasksLocalDataSource) {
        mTasksRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        mTasksLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    public static TasksRepository getInstance(TasksDataSource tasksRemoteDataSource,
                                              TasksDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        Flowable<List<Task>> remoteTasks= getAndSaveRemoteTasks();
        return remoteTasks;
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        Flowable<Optional<Task>> localTask = getTaskWithIdFromLocalRepository(taskId);
        Flowable<Optional<Task>> remoteTask = mTasksRemoteDataSource
                .getTask(taskId)
                .doOnNext(optionalTask->{

                    if(optionalTask.isPresent())
                    {
                        Task task = optionalTask.get();
                    }
                });


        return Flowable.concat(localTask, remoteTask)
                .firstElement()
                .toFlowable();
    }

    @Override
    public void deleteAllTasks() {
        mTasksLocalDataSource.deleteAllTasks();
    }

    @Override
    public void completeTask(Task task) {
        checkNotNull(task);
        mTasksRemoteDataSource.completeTask(task);
        mTasksLocalDataSource.completeTask(task);
    }

    @Override
    public void completeTask(String id) {

    }


    private Flowable<List<Task>> getAndSaveRemoteTasks() {
        return mTasksRemoteDataSource
                .getTasks()
                .flatMap( tasks -> Flowable.fromIterable(tasks).doOnNext( task -> { } ) )
                .toList()
                .toFlowable()
                .doOnComplete(() -> {
                });
    }


    Flowable<Optional<Task>> getTaskWithIdFromLocalRepository(@NonNull final String taskId)
    {
        return mTasksLocalDataSource
                .getTask(taskId)
                .firstElement()
                .toFlowable();
    }
}
