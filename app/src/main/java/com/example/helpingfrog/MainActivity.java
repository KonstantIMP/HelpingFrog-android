package com.example.helpingfrog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helpingfrog.R;
import com.example.helpingfrog.adapter.TaskAdapter;
import com.example.helpingfrog.databinding.ActivityMainBinding;
import com.example.helpingfrog.domain.Author;
import com.example.helpingfrog.domain.Importance;
import com.example.helpingfrog.domain.Task;
import com.example.helpingfrog.fragment.AddTaskFragment;
import com.example.helpingfrog.noDb.NoDb;
import com.example.helpingfrog.rest.AuthorizationActivity;
import com.example.helpingfrog.rest.ComingSoonActivity;
import com.example.helpingfrog.rest.ProfileActivity;
import com.example.helpingfrog.rest.TaskExchangeApiValley;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SERVER_STATE = 1;
    private TaskAdapter taskAdapter;
    private TaskExchangeApiValley taskExchangeApiValley;


    private ItemTouchHelper.SimpleCallback simpleCallback;

    private ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskExchangeApiValley = new TaskExchangeApiValley(this);
        taskExchangeApiValley.fillAuthor();
        taskExchangeApiValley.fillImportance();
        taskExchangeApiValley.fillTask();

        taskAdapter = new TaskAdapter(this, NoDb.TASK_LIST);
        binding.rvTask.setAdapter(taskAdapter);

        Button btn_to_profile = binding.btntToProfile;
        binding.btnAddTask.setOnClickListener(this);
        btn_to_profile.setOnClickListener(this);

        simpleCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT // ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT (TODO: обработка принятия таски свайпом вправо)
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Task task = NoDb.TASK_LIST.get(viewHolder.getAdapterPosition());

                if (direction == ItemTouchHelper.LEFT) {
                    if (SERVER_STATE == 1){
                        NoDb.TOTAL_HOURS ++;
                        NoDb.TASK_LIST.remove(task.getId());

                    }else {
                        taskExchangeApiValley.deleteTask(task.getId());
                    }
                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper((simpleCallback));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_task:

                AddTaskFragment addTaskFragment = new AddTaskFragment();

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_main, addTaskFragment)
                        .commit();
                break;
            case R.id.btnt_to_profile:
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
        }
    }

    public void updateAdapter(){

        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        int size = fragmentList.size();

        if (size > 0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragmentList.get(size - 1))
                    .commit();
        } else {
            finish();
        }
    }

    public void update() {

        taskAdapter.notifyDataSetChanged();
    }
}