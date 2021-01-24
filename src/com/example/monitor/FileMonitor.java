package com.example.monitor;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileMonitor
{
    private final List<FileChangeListener> listeners = new ArrayList<>();
    private final Path folderPath;
    private final WatchableFileType fileType;
    private final WatchableFileEvent fileEvent;

    public FileMonitor(Path folderPath, WatchableFileType fileType, WatchableFileEvent fileEvent) {
        this.folderPath = folderPath;
        this.fileType = fileType;
        this.fileEvent = fileEvent;
    }

    public void watch() {
        System.out.printf("Monitoring is started for %s%n", folderPath);
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            folderPath.register(watchService, fileEvent.getEventType());
            boolean poll = true;
            while (poll) {
                poll = pollEvents(watchService);
            }
        } catch ( IOException | InterruptedException | ClosedWatchServiceException e) {
            e.printStackTrace();
        }
    }

    protected boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key = watchService.take();

        for (WatchEvent<?> event : key.pollEvents()) {
            Path changedPath = (Path) event.context();
            Path absolutePath = folderPath.resolve(changedPath);
            if (isWatchableChange(absolutePath)) {
                notifyListeners();
            }
        }

        return key.reset();
    }

    private boolean isWatchableChange(Path changed) {
        return fileType.isWatchablePath(changed);
    }

    private void notifyListeners()
    {
        for ( FileChangeListener listener : listeners )
        {
            fileEvent.notifyListener(listener);
        }
    }

    public void addListener(FileChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(FileChangeListener listener) {
        listeners.remove(listener);
    }
}
