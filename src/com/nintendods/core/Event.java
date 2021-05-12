package com.nintendods.core;

public abstract class Event {
    public final Student student;

    protected Event(Student student) {
        this.student = student;
    }

    public abstract void execute();
}
