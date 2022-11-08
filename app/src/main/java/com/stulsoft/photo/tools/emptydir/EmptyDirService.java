/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools.emptydir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class EmptyDirService {
    public static EmptyDirResult findEmptyDirs(String path) {
        EmptyDirResult result;

        try (Stream<Path> allPaths = Files.walk(Paths.get(path))) {
            result = new EmptyDirResult(Optional.of(allPaths
                    .filter(Files::isDirectory)
                    .filter(p -> {
                        try (Stream<Path> children = Files.list(p)) {
                            return children.findAny().isEmpty();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            return false;
                        }
                    })
                    .map(p -> p.toFile().getAbsolutePath())
                    .toList()),
                    Optional.empty());
        } catch (NoSuchFileException exception){
            result = new EmptyDirResult(Optional.empty(), Optional.of(exception.getMessage() + ": no such file exists"));
        } catch(IOException exception) {
            exception.printStackTrace();
            result = new EmptyDirResult(Optional.empty(), Optional.of(exception.getMessage()));
        }
        return result;
    }
}
