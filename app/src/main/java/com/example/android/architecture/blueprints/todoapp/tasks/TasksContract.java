package com.example.android.architecture.blueprints.todoapp.tasks;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.BaseView;
import com.example.android.architecture.blueprints.todoapp.data.Task;

import java.util.List;

/**
 * Created by vihaanverma on 04/01/18.
 */

public interface TasksContract {

    interface View extends BaseView<Presenter>{
        void showAddTask();
        void showSuccessfullySavedMessage();
        void showNoTasks();
        void showTasks(List<Task> tasks);
        void setLoadingIndicator(boolean status);
        void showLoadingTasksError();
        void showTaskDetailsUi(String taskId);
    }

    interface Presenter extends BasePresenter
    {
        void result(int requestCode, int resultCode);
        void addNewTask();
        void loadTasks(boolean forceUpdate);
        void openTaskDetails(@NonNull Task task);
        void completeTask(@NonNull Task task);
        void activateTask(@NonNull Task task);
    }
}
