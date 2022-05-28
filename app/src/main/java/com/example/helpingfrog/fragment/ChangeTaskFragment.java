package com.example.helpingfrog.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.helpingfrog.R;
import com.example.helpingfrog.adapter.AuthorSpinnerAdapter;
import com.example.helpingfrog.adapter.ImportanceSpinnerAdapter;
import com.example.helpingfrog.adapter.TaskAdapter;
import com.example.helpingfrog.databinding.FragmentChangeTaskBinding;
import com.example.helpingfrog.domain.Author;
import com.example.helpingfrog.domain.Importance;
import com.example.helpingfrog.domain.Task;
import com.example.helpingfrog.noDb.NoDb;
import com.example.helpingfrog.rest.TaskExchangeApiValley;

public class ChangeTaskFragment extends Fragment {

    private final int SERVER_STATE = 1;

    private FragmentChangeTaskBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChangeTaskBinding.inflate(inflater);

        Task task = (Task)getArguments().getSerializable(TaskAdapter.TASK_KEY);

        AuthorSpinnerAdapter authorSpinnerAdapter = new AuthorSpinnerAdapter(getContext(), NoDb.AUTHOR_LIST);
        ImportanceSpinnerAdapter importanceSpinnerAdapter = new ImportanceSpinnerAdapter(getContext(), NoDb.IMPORTANCE_LIST);
        binding.spAuthor.setAdapter(authorSpinnerAdapter);
        binding.spImportance.setAdapter(importanceSpinnerAdapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SERVER_STATE == 1){

                    int id = task.getId();

                    NoDb.TASK_LIST.remove(id);

                    NoDb.TASK_LIST.add(
                            new Task(id,
                                    binding.etTaskName.getText().toString(),
                                    (Author)binding.spAuthor.getSelectedItem(),
                            (Importance)binding.spImportance.getSelectedItem())
                    );


                }else {

                    new TaskExchangeApiValley(getContext()).updateTask(
                            task.getId(),
                            binding.etTaskName.getText().toString(),
                            ((Author) binding.spAuthor.getSelectedItem()).getName(),
                            ((Importance) binding.spImportance.getSelectedItem()).getName()
                    );
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(ChangeTaskFragment.this)
                        .commit();
            }
        });






        return binding.getRoot();
    }
}