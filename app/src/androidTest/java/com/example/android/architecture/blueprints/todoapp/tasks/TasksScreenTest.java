package com.example.android.architecture.blueprints.todoapp.tasks;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.architecture.blueprints.todoapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vihaanverma on 07/01/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TasksScreenTest {

    private final static String TITLE1 = "TITLE1";

    private final static String DESCRIPTION = "DESCR";

    private final static String TITLE2 = "TITLE2";

    @Rule
    public ActivityTestRule<TasksActivity> mTaskActivityRule=
            new ActivityTestRule<TasksActivity>(TasksActivity.class);


    @Test
    public void clickAddTaskButton_opensAddTaskUi(){
        onView(withId(R.id.fab_add_task)).perform(click());

        onView(withId(R.id.add_task_title)).check(matches(isDisplayed()));
    }

    @Test
    public void editTask(){
        createTask(TITLE1, DESCRIPTION);
        onView(withText(TITLE1)).perform(click());
//        onView(withId(R.id.fab)).perform(click());
    }

    private void createTask(String title, String description){
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.add_task_title)).perform(typeText(title),closeSoftKeyboard());
        onView(withId(R.id.add_task_description)).perform(typeText(description),closeSoftKeyboard());
        onView(withId(R.id.fab_edit_task_done)).perform(click());
    }
}
