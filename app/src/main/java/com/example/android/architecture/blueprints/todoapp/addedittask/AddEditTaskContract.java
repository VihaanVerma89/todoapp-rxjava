package com.example.android.architecture.blueprints.todoapp.addedittask;

import com.example.android.architecture.blueprints.todoapp.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.BaseView;

/**
 * Created by vihaanverma on 05/01/18.
 */

public interface AddEditTaskContract {
    interface View extends BaseView<Presenter>{
        void showEmptyTaskError();
        void showTasksList();

        boolean isActive();

        void setTitle(String title);

        void setDescription(String title);
    }

    interface Presenter extends BasePresenter{
        void saveTask(String title, String description);
        void populateTask();
    }
}
