package com.example.sj_tdl;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<item_adapter> listTasks;
    public DbHelper dbHelper;

    public Adapter(Context context, ArrayList<item_adapter> listTasks) {
        this.context = context;
        this.listTasks = listTasks;
        dbHelper = new DbHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent, false);
        return new ViewHolder(view);
    }
    private boolean Check(int n){
        return n!=0;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final item_adapter itemadapter = listTasks.get(position);

        holder.TextView.setText(itemadapter.getTask());
        holder.CheckBox.setChecked(itemadapter.getStatus());
        holder.CheckBox.setOnCheckedChangeListener((view, checked)-> {


            if (checked == true) {
                dbHelper.updateStatus(listTasks.get(holder.getAdapterPosition()).getId(), true);
            }
            else {
                dbHelper.updateStatus(listTasks.get(holder.getAdapterPosition()).getId(), false);
            }
        });



        holder.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.deleteTask(itemadapter.getId());
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }


    @Override
    public int getItemCount(){
        return listTasks.size();
    }


    private void editTaskDialog(final item_adapter itemadapter){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.create_task, null);

        final EditText enter_task = (EditText)subView.findViewById(R.id.newTaskText);
        final EditText enter_date = (EditText)subView.findViewById(R.id.newTaskDate);
        final int itemId  = itemadapter.getId();
        final boolean done = itemadapter.getStatus();

        if(itemadapter != null){
            enter_task.setText(itemadapter.getTask());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit task");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT TASK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String task = enter_task.getText().toString();
                final String date = enter_date.getText().toString();
                final boolean edit_done= done;
                final int id_edit = itemId;


                if(TextUtils.isEmpty(task)){
                    Toast.makeText(context, "Please Try Again", Toast.LENGTH_LONG).show();
                }
                else{
                    dbHelper.updateTask(new item_adapter(id_edit, task, date, edit_done));
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
