package com.company.salesanalysis.port.adapter.filesystem;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ResourceProvider {

    private static final String DEFAULT_EXTENSION_FILE = ".txt";


    private ResourceProvider() {}

    public static Resource getFileInPath(String path, String fileName) {
        return new FileSystemResource(path + fileName + DEFAULT_EXTENSION_FILE);
    }

    public static Collection<Resource> getFilesInPath(String path) {

        return Optional
                .ofNullable(new File(path).list())
                .map(files -> collectFilesInPath(path, files))
                .orElse(new ArrayList<>());
    }

    private static List<Resource> collectFilesInPath(String path, String[] files) {

        return Arrays
                .stream(files)
                .map(filePath -> new FileSystemResource(path + filePath))
                .map(fileSystemResource -> (Resource) fileSystemResource)
                .collect(Collectors.toList())
                ;
    }

    public static Resource createFileInPath(String basePathOutFile, String originFileName) {

        File file;

        try {

            file = new File(basePathOutFile + originFileName + DEFAULT_EXTENSION_FILE);
            file.createNewFile();

        } catch (IOException ex) {

            throw new RuntimeException(ex);
        }

        return new FileSystemResource(file);
    }

}
