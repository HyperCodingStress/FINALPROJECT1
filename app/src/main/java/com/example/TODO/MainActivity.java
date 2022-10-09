package com.example.TODO;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        ToDoAdapter.DeleteItemListener {

    RecyclerView recyclerView;
    Button addButton;

    DatabaseHelper db;
    List<ListTodo> listTodoList;
    ToDoAdapter toDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.country_list_rv);
        addButton = (Button)  findViewById(R.id.add_country_btn);
        addButton.setOnClickListener(this);

        loadDataCountry();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_country_btn){
            //Add Data

            popupFormCountry("Add a new task \n what do you want to do next?", null, null, true);
        }
    }

    void popupFormCountry(String title, String countryName, String population, boolean isAddNew){

        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        EditText countryNameInput = (EditText) view.findViewById(R.id.country_name_input);

        Button saveButton = (Button) view.findViewById(R.id.save_button);

        if(!isAddNew){
            countryNameInput.setText(countryName);

        }

        popupBuilder.setView(view);

        AlertDialog popupForm = popupBuilder.create();
        popupForm.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert Data

                String countryName = countryNameInput.getText().toString();

                ListTodo listTodo = new ListTodo(countryName);


                if(isAddNew){
                    db.addCountry(listTodo);
                    Toast.makeText(MainActivity.this, "Todo  : "+countryName+"  Berhasil Ditambahkan ", Toast.LENGTH_SHORT).show();
                }
                loadDataCountry();
                popupForm.dismiss();
            }
        });

    }

    private void loadDataCountry(){
        listTodoList = db.getAllCountries();
        toDoAdapter = new ToDoAdapter(this, listTodoList, this);
        recyclerView.setAdapter(toDoAdapter);

    }

    @Override
    public void onDeleteItemClick(int position) {
        ListTodo listTodo = listTodoList.get(position);
        popup(listTodo.getData());
        db.delete(listTodo);
        Toast.makeText(MainActivity.this, "ToDo "+ listTodo.getData() + " Diselesaikan ", Toast.LENGTH_SHORT).show();
        loadDataCountry();
    }

    void popup(String nama){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage(nama)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("Todo Telah Diselesaikan")
                .create();
        myAlert.show();
    }
}