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
import com.example.helpingfrog.databinding.FragmentAddTaskBinding;
import com.example.helpingfrog.domain.Author;
import com.example.helpingfrog.domain.Importance;
import com.example.helpingfrog.domain.Task;
import com.example.helpingfrog.noDb.NoDb;
import com.example.helpingfrog.rest.TaskExchangeApiValley;

import java.util.zip.Inflater;


public class AddTaskFragment extends Fragment {

    public final int SERVER_STATE = 1;

    private FragmentAddTaskBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater);

        AuthorSpinnerAdapter authorSpinnerAdapter = new AuthorSpinnerAdapter(getContext(), NoDb.AUTHOR_LIST);
        ImportanceSpinnerAdapter importanceSpinnerAdapter = new ImportanceSpinnerAdapter(getContext(), NoDb.IMPORTANCE_LIST);
        binding.spAuthor.setAdapter(authorSpinnerAdapter);
        binding.spImportance.setAdapter(importanceSpinnerAdapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SERVER_STATE == 1) {

                    NoDb.TASK_LIST.add(new Task(
                            binding.etTaskName.getText().toString(),
                            (Author) binding.spAuthor.getSelectedItem(),
                            (Importance) binding.spImportance.getSelectedItem()
                    ));

                } else {

                    new TaskExchangeApiValley(getContext()).addTask(
                            new Task(
                                    binding.etTaskName.getText().toString(),
                                    ((Author) binding.spAuthor.getSelectedItem()),
                                    ((Importance) binding.spImportance.getSelectedItem())
                            )
                    );
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(AddTaskFragment.this)
                        .commit();
            }
        });

        return binding.getRoot();
    }
}
