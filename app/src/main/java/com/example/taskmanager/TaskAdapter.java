package com.example.taskmanager;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;

    public TaskAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = taskList.get(position);
        holder.tvTaskTitle.setText(currentTask.getTitle());

        com.google.android.material.card.MaterialCardView card =
                (com.google.android.material.card.MaterialCardView) holder.itemView;

        if (currentTask.isCompleted()) {
            holder.tvTaskTitle.setPaintFlags(holder.tvTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTaskTitle.setTextColor(0xFF9CA3AF);
            card.setCardBackgroundColor(0xFFF9FAFB);
        } else {
            holder.tvTaskTitle.setPaintFlags(holder.tvTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvTaskTitle.setTextColor(0xFF1F2937);
            card.setCardBackgroundColor(0xFFFFFFFF);
        }

        // Marcar como completada
        holder.itemView.setOnClickListener(v -> {
            currentTask.setCompleted(!currentTask.isCompleted());
            notifyItemChanged(position);
        });

        // Botón eliminar
        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                taskList.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskTitle;
        ImageView btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
