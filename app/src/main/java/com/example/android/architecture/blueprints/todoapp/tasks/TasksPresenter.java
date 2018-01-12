package com.example.android.architecture.blueprints.todoapp.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskActivity;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.util.BaseSchedulerProvider;
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vihaanverma on 04/01/18.
 */

public class TasksPresenter implements TasksContract.Presenter {

    @NonNull
    private final TasksRepository mTasksRepository;

    @NonNull
    private final TasksContract.View mTasksView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;


    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @NonNull
    private TasksFilterType mCurrentFiltering = TasksFilterType.ALL_TASKS;

    public TasksPresenter(@NonNull TasksRepository tasksRepository,
                          @NonNull TasksContract.View tasksView,
                          @NonNull BaseSchedulerProvider schedulerProvider) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mTasksView = checkNotNull(tasksView, "tasksView cannot be null!");

        mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mCompositeDisposable = new CompositeDisposable();
        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void subscribe() {
        loadTasks(false);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        EspressoIdlingResource.increment();
        mCompositeDisposable.clear();
        Disposable disposable = mTasksRepository
                .getTasks()
                .flatMap(Flowable::fromIterable)
                .filter(task -> {
                    switch (mCurrentFiltering) {
                        case ACTIVE_TASKS:
                            return task.isActive();
                        case COMPLETED_TASKS:
                            return task.isCompleted();
                        case ALL_TASKS:
                        default:
                            return true;
                    }
                })
                .toList()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(tasks -> {
                            processTasks(tasks);
                            mTasksView.setLoadingIndicator(false);
                        },
                        throwable -> {
                            mTasksView.showLoadingTasksError();
                        });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void openTaskDetails(@NonNull Task task) {
        checkNotNull(task, "requestedTask cannot be null!");
        mTasksView.showTaskDetailsUi(task.getId());
    }

    @Override
    public void completeTask(@NonNull Task task) {
        checkNotNull(task, "completedTask cannot be null!");
        mTasksRepository.completeTask(task);
        mTasksView.showTaskMarkedComplete();
        loadTasks(false);
    }

    @Override
    public void activateTask(@NonNull Task task) {
        checkNotNull(task, "activeTask cannot be null!");
        mTasksRepository.activateTask(task);
        mTasksView.showTaskMarkedActive();
        loadTasks(false );
    }

    @Override
    public void clearCompletedTasks() {
        mTasksRepository.clearCompletedTasks();
        mTasksView.showCompletedTasksCleared();
        loadTasks(false);
    }

    @Override
    public void setFiltering(TasksFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public TasksFilterType getFiltering() {
        return mCurrentFiltering;
    }

    private void processTasks(@NonNull List<Task> tasks) {
        if (tasks.isEmpty()) {
            processEmptyTasks();
        } else {
            mTasksView.showTasks(tasks);
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            default:
                mTasksView.showNoTasks();
                break;
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {

        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {

        }
    }

    @Override
    public void addNewTask() {
        mTasksView.showAddTask();
    }
}
