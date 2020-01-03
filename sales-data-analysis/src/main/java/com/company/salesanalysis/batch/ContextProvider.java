package com.company.salesanalysis.batch;

import org.springframework.batch.item.ExecutionContext;

import java.util.List;

public class ContextProvider {


    public static String getFileNameInStepContext(ExecutionContext executionContext){

        String pathFile = ((String) executionContext.get("fileName")).substring(6);
        String[] fragments = pathFile.split("/");
        String fileNameWithExtension = fragments[fragments.length - 1];
        return fileNameWithExtension.substring(0, fileNameWithExtension.length() - 4);

    }

    public static List<String> getFileNamesInJobContext(ExecutionContext executionContext){ return  (List<String>) executionContext.get("fileNames"); }

    public static void putFileNamesInJobContext(List<String> fileNames, ExecutionContext executionContext) {

        executionContext.put("fileNames", fileNames);
    }
}
