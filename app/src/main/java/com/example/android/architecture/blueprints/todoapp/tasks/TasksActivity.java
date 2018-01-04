package com.example.android.architecture.blueprints.todoapp.tasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.architecture.blueprints.todoapp.R;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);
    }
}
