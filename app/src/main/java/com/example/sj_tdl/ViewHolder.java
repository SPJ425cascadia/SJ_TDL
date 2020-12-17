package com.example.sj_tdl;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


@SuppressWarnings("ALL")
public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView TextView;
    public CheckBox CheckBox;
    public TextView dateTextView;
    public Button deleteBtn;
    public Button deleteTask;

    public ViewHolder(@NonNull View itemView) {

        super(itemView);
        TextView = itemView.findViewById(R.id.task_name);
        CheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        deleteTask = (Button) itemView.findViewById(R.id.delete_task);
    }
    public ViewHolder(@NonNull View itemView, boolean b) {

        super(itemView);
        TextView = itemView.findViewById(R.id.task_name);
        CheckBox = itemView.findViewById(R.id.checkBox);
        deleteTask = (Button) itemView.findViewById(R.id.delete_task);

        CheckBox.setChecked(b);
    }
}