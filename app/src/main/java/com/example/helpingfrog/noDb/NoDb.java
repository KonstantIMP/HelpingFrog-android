package com.example.helpingfrog.noDb;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.helpingfrog.domain.Author;
import com.example.helpingfrog.domain.Importance;
import com.example.helpingfrog.domain.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoDb {

    private NoDb(){}

    public static final List<Task> TASK_LIST = new ArrayList<Task>();
    public static final List<Author> AUTHOR_LIST = new ArrayList<Author>();
    public static final List<Importance> IMPORTANCE_LIST = new ArrayList<Importance>();

    public static int TOTAL_HOURS = 0;
}