package com.example.sj_tdl;

class item_adapter {
    private int id;
    private String task;
    private String date;
    private boolean done;

    public item_adapter(String task, String date, boolean status){
        this.task = task;
        this.date = date;
        this.done = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getStatus() {
        return done;
    }

    public void setStatus(boolean done) {
        this.done = done;
    }

    public boolean getStatus(int id){
        this.id = id;
        return this.done;
    }

    public item_adapter(int id, String task, String date, boolean done){
        this.id = id;
        this.task = task;
        this.date = date;
        this.done = done;
    }


}