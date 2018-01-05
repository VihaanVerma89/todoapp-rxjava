package com.example.android.architecture.blueprints.todoapp.util;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by vihaanverma on 05/01/18.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
