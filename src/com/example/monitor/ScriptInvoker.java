package com.example.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class ScriptInvoker implements FileChangeListener
{
    private final Path scriptPath;

    public ScriptInvoker(Path scriptPath) {
        this.scriptPath = scriptPath;
    }

    @Override
    public void onCreated()
    {
        try
        {
//            Thread.sleep(3000);
            System.out.println("Invoking script...");

            ProcessBuilder builder = new ProcessBuilder("bash", "-c", scriptPath.toString());
            builder.redirectErrorStream(true);
            Process process = builder.start();

            printCommandOutput(process);
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private void printCommandOutput(Process process) throws IOException
    {
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Override
    public void onModified()
    {
        System.out.println("This event is not handled");
    }

    @Override
    public void onDeleted()
    {
        System.out.println("This event is not handled");
    }
}
