package com.example.TODO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private Context context;

    private List<ListTodo> DataTodo = new ArrayList<>();

    public ToDoAdapter(Context context, List<ListTodo> DataTodo, DeleteItemListener deleteItemListener) {
        this.context = context;
        this.DataTodo = DataTodo;
        this.deleteItemListener = deleteItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListTodo listTodo = DataTodo.get(position);

        holder.text.setText(listTodo.getData());
        
    }

    @Override
    public int getItemCount() {
        return DataTodo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView text;
        private Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);

            deleteButton = itemView.findViewById(R.id.done);

            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if(v.getId() == R.id.done){
                deleteItemListener.onDeleteItemClick(position);
            }

        }
    }


    private DeleteItemListener deleteItemListener;


    public interface DeleteItemListener{
        void onDeleteItemClick(int position);
    }
}
