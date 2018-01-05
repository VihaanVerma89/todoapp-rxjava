package com.example.android.architecture.blueprints.todoapp.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource;
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors;
import com.example.android.architecture.blueprints.todoapp.util.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import static com.google.common.base.Preconditions.checkNotNull;

import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksPersistenceContract.TaskEntry;
/**
 * Created by vihaanverma on 05/01/18.
 */

public class TasksLocalDataSource implements TasksDataSource {


    @NonNull
    private static TasksLocalDataSource INSTANCE;


    @NonNull
    private final BriteDatabase mDatabaseHelper;


    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private TasksLocalDataSource(@NonNull Context context,
                                 @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
        TasksDbHelper dbHelper = new TasksDbHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
    }

    public static TasksLocalDataSource getInstance(@NonNull Context context,
                                                   @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            synchronized (TasksLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TasksLocalDataSource(context, schedulerProvider);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        values.put(TaskEntry.COLUMN_NAME_TITLE, task.getTitle());
        values.put(TaskEntry.COLUMN_NAME_DESCRIPTION, task.getDescription());
        values.put(TaskEntry.COLUMN_NAME_COMPLETED, task.isCompleted());
        mDatabaseHelper.insert(TaskEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
