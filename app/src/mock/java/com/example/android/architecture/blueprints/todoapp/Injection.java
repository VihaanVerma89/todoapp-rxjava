package com.example.android.architecture.blueprints.todoapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.FakeTasksRemoteDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource;
import com.example.android.architecture.blueprints.todoapp.util.BaseSchedulerProvider;
import com.example.android.architecture.blueprints.todoapp.util.schedulers.SchedulerProvider;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by vihaanverma on 04/01/18.
 */

public class Injection {

    public static TasksRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return TasksRepository.getInstance(FakeTasksRemoteDataSource.getInstance(),
                TasksLocalDataSource.getInstance(context, provideScheduleProvider()));
    }

    public static BaseSchedulerProvider provideScheduleProvider() {
        return SchedulerProvider.getInstance();
    }

}
