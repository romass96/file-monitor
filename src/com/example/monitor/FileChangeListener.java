package com.example.monitor;

public interface FileChangeListener
{
    void onCreated();
    void onModified();
    void onDeleted();
}
