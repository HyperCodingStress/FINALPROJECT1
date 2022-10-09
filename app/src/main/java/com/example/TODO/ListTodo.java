package com.example.TODO;

public class ListTodo {

    int id;
    String Data;

    public ListTodo(String Data) {
        this.Data = Data;
    }

    public ListTodo() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
