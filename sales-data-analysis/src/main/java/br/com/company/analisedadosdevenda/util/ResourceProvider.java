package br.com.company.analisedadosdevenda.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResourceProvider {

    private ResourceProvider(){}

    public static Collection<Resource> getResourcesInPath(String path) {

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

    public static Resource createFileOut(String basePathOutFile, String originFileName) {

    String[] fragments = originFileName.split("/");
    Integer length = fragments.length;
    String lastFragment = fragments[length - 1];

    File file = new File(basePathOutFile + lastFragment.substring(0, lastFragment.length() - 4) + LocalDateTime.now() + ".txt");

        try {

            file.createNewFile();

        } catch (IOException ex){
            throw new RuntimeException(ex.getMessage());
        }

        return new FileSystemResource(file);
    }


    public static boolean hasResource(String path){

        String[] filesPath = new File(path).list();
        return (filesPath == null || filesPath.length < 0);
    }
}
