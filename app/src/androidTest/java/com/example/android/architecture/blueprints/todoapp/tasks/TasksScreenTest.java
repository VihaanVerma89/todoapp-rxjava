package com.example.android.architecture.blueprints.todoapp.tasks;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.internal.operators.flowable.FlowableTimeoutTimed;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkArgument;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

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
            new ActivityTestRule<TasksActivity>(TasksActivity.class){
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    Injection.provideTasksRepository(InstrumentationRegistry.getTargetContext())
                            .deleteAllTasks();
                }
            };


    @Test
    public void clickAddTaskButton_opensAddTaskUi(){
        onView(withId(R.id.fab_add_task)).perform(click());

        onView(withId(R.id.add_task_title)).check(matches(isDisplayed()));
    }

    @Test
    public void editTask(){
        createTask(TITLE1, DESCRIPTION);
        onView(withText(TITLE1)).perform(click());

        onView(withId(R.id.fab_edit_task)).perform(click());

        String editTaskTitle = TITLE2;
        String editTaskDescription = "New Description";

        onView(withId(R.id.add_task_title))
                .perform(replaceText(editTaskTitle), closeSoftKeyboard());

        onView(withId(R.id.add_task_description))
                .perform(replaceText(editTaskDescription),closeSoftKeyboard());

        onView(withId(R.id.fab_edit_task_done))
                .perform(click());

        onView(withItemText(editTaskTitle)).check(matches(isDisplayed()));
        onView(withItemText(TITLE1)).check(doesNotExist());
    }

    @Test
    public void addTaskToTasksList(){
        createTask(TITLE1, DESCRIPTION);
        onView(withItemText(TITLE1)).check(matches(isDisplayed()));
    }

    @Test
    public void markTaskAsComplete(){
        viewAllTasks();
        createTask(TITLE1, DESCRIPTION);

        clickCheckBoxForTask(TITLE1);

        viewAllTasks();
        onView(withItemText(TITLE1)).check(matches(isDisplayed()));
        viewActiveTasks();
        onView(withItemText(TITLE1)).check(matches(not(isDisplayed())));
        viewCompletedTasks();
        onView(withItemText(TITLE1)).check(matches(isDisplayed()));
    }

    private Matcher<View> withItemText(String itemText)
    {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return allOf( isDescendantOfA(isAssignableFrom(ListView.class))
                        , withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }

    private void viewAllTasks(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_all)).perform(click());
    }

    private void viewActiveTasks(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_active)).perform(click());
    }

    private void viewCompletedTasks(){
        onView(withId(R.id.menu_filter)).perform(click());
        onView(withText(R.string.nav_completed)).perform(click());
    }

    private void clickCheckBoxForTask(String title){
        onView(allOf(withId(R.id.complete), hasSibling(withText(title)))).perform(click());
    }

    private void createTask(String title, String description){
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.add_task_title)).perform(typeText(title),closeSoftKeyboard());
        onView(withId(R.id.add_task_description)).perform(typeText(description),closeSoftKeyboard());
        onView(withId(R.id.fab_edit_task_done)).perform(click());
    }



















































}
