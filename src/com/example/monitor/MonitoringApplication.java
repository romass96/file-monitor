package com.example.monitor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MonitoringApplication
{
    public static void main(String[] args)
    {
        Path folderPath = Paths.get(args[0]);
        FileMonitor fileMonitor = new FileMonitor(folderPath, WatchableFileType.FOLDER, WatchableFileEvent.CREATED);

        Path scriptPath = Paths.get(args[1]);
        fileMonitor.addListener(new ScriptInvoker(scriptPath));

        fileMonitor.watch();
        System.out.println("Application has been finished");
    }
}
