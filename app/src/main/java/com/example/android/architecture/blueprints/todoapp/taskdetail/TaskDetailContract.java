package com.example.android.architecture.blueprints.todoapp.taskdetail;

import com.example.android.architecture.blueprints.todoapp.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.BaseView;

/**
 * Created by vihaanverma on 07/01/18.
 */

public class TaskDetailContract {

    interface View extends BaseView<Presenter>{
        void showMissingTask();
        void setLoadingIndicator(boolean status);

        void hideTitle();

        void showTitle(String title);

        void hideDescription();

        void showDescription(String description);

        void showCompletionStatus(boolean completed);

        void showEditTask(String mTaskId);
    }

    interface Presenter extends BasePresenter{
        public void editTask();

        void completeTask();

        void activateTask();
    }
}
