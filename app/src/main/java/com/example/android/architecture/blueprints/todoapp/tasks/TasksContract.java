package com.example.android.architecture.blueprints.todoapp.tasks;

import com.example.android.architecture.blueprints.todoapp.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.BaseView;

/**
 * Created by vihaanverma on 04/01/18.
 */

public interface TasksContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter
    {

    }
}
