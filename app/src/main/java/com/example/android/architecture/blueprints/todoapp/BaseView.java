package com.example.android.architecture.blueprints.todoapp;

/**
 * Created by vihaanverma on 04/01/18.
 */

public interface BaseView<T>{

    void setPresenter(T presenter);
}
