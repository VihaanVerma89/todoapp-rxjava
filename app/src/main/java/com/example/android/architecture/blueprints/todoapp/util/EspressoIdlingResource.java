package com.example.android.architecture.blueprints.todoapp.util;

import android.support.test.espresso.IdlingResource;

/**
 * Created by vihaanverma on 12/01/18.
 */

public class EspressoIdlingResource {
    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource=
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment(){
        mCountingIdlingResource.increment();
    }
    public static void decrement(){
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource(){
        return mCountingIdlingResource;
    }
}
