package com.example.monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.*;

public enum WatchableFileEvent {
    CREATED(FileChangeListener::onCreated, ENTRY_CREATE),
    MODIFIED(FileChangeListener::onModified, ENTRY_MODIFY),
    DELETED(FileChangeListener::onDeleted, ENTRY_DELETE);

    private final Consumer<FileChangeListener> listenerAction;
    private final WatchEvent.Kind<Path> eventType;

    WatchableFileEvent(Consumer<FileChangeListener> listenerAction, WatchEvent.Kind<Path> eventType) {
        this.listenerAction = listenerAction;
        this.eventType = eventType;
    }

    public void notifyListener(FileChangeListener listener) {
        listenerAction.accept(listener);
    }

    public WatchEvent.Kind<Path> getEventType() {
        return eventType;
    }
}
