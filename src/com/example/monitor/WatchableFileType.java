package com.example.monitor;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Predicate;

public enum WatchableFileType {
    FILE(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)),
    SYMLINK(Files::isSymbolicLink),
    FOLDER(path -> Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));

    private final Predicate<Path> watchablePredicate;

    WatchableFileType(Predicate<Path> watchablePredicate) {
        this.watchablePredicate = watchablePredicate;
    }

    public boolean isWatchablePath(Path changed) {
        return watchablePredicate.test(changed);
    }
}
