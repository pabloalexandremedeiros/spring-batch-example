package com.company.salesanalysis.port.adapter.systemfile;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResourceProvider {

    private static final ExtensionFile DEFAULT_EXTENSION_FILE = ExtensionFile.TXT;
    private static final String BAR = "/";

    public enum ExtensionFile {

        TXT(".txt");

        private String des;

        ExtensionFile(String des) {
            this.des = des;
        }

        public String getDesc() {
            return des;
        }

    }

    private ResourceProvider() {}

    public static Resource getFileInPath(String path, String fileName) {
        return new FileSystemResource(path + fileName + DEFAULT_EXTENSION_FILE.getDesc());
    }

    public static Collection<Resource> getFilesInPath(String path) {

        return Optional
                .ofNullable(new File(path).list())
                .map(files ->

                        Arrays
                                .stream(files)
                                .map(filePath -> new FileSystemResource(path + filePath))
                                .map(fileSystemResource -> (Resource) fileSystemResource)
                                .collect(Collectors.toList())

                ).orElse(new ArrayList<>());
    }

    public static Resource createFileInPath(String basePathOutFile, String originFileName) {

        File file = new File(basePathOutFile + originFileName + DEFAULT_EXTENSION_FILE.getDesc());

        try {

            file.createNewFile();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return new FileSystemResource(file);
    }

}
