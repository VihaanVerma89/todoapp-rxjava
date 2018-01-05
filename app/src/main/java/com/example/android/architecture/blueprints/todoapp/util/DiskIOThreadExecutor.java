package com.example.android.architecture.blueprints.todoapp.util;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by vihaanverma on 05/01/18.
 */

public class DiskIOThreadExecutor implements Executor {

    private final Executor mDiskIO;
    public DiskIOThreadExecutor(){
       mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        mDiskIO.execute(runnable);
    }
}
