package com.example.taskmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private ArrayList<Task> taskList;
    private TaskAdapter adapter;

    private EditText etTaskInput;
    private Button btnAdd;
    private RecyclerView recyclerViewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTaskInput = findViewById(R.id.etTaskInput);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(taskList);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(adapter);

        // Botón agregar
        btnAdd.setOnClickListener(v -> {
            String title = etTaskInput.getText().toString().trim();
            if (!title.isEmpty()) {
                taskList.add(new Task(title));
                adapter.notifyItemInserted(taskList.size() - 1);
                etTaskInput.setText("");
                // Hacer scroll automático hacia abajo al agregar
                recyclerViewTasks.scrollToPosition(taskList.size() - 1);
            }
        });

        // Eliminar deslizando hacia la izquierda
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewTasks);
    }
}
