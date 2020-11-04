package com.demo.test.code.generator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author youyu
 */
public class Replace {
    private static String voPath = "/Users/jam/company/code/contract-center/contract-admin/src/main/java/io/terminus/srm/contract/admin/vo/sign";
    private static String viewManifestPath = "/Users/jam/company/code/contract-center/contract-admin/src/main/resources/trantor/resources/contract_center/view-manifest.json";
    private static String newModuleKey = "";
    private static String resourcePath = "/Users/jam/company/code/contract-center/contract-admin/src/main/resources/trantor/resources/contract_center";

    public static void main(String[] args) throws IOException {
//        patch("/Users/jam/company/code/itemcenter-backend/ic-backend-admin/src/main/resources/trantor/resources/itemcenter/view-manifest.json","/Users/jam/company/code/itemcenter-backend/ic-backend-admin/src/main/resources/trantor/resources/itemcenter");
        replace();

    }

    private static void replace() throws IOException {
        HashMap<String, String> voMap = getVOMap();

        HashMap<String, String> replaceMap = Maps.newHashMap();

        initReplaceMap(voMap,replaceMap);
        replace(replaceMap);
//        replace("/Users/jam/company/code/itemcenter-backend/ic-backend-admin/src/main/resources/trantor/resources/itemcenter/b2c-item-create-view.xml",replaceMap);
    }


    private static HashMap<String, String> getVOMap() {
        File file = new File(voPath);
        LinkedList<File> filesQueue = new LinkedList<>();
        filesQueue.addFirst(file);
        int count = 0;

        HashMap<String, String> voMap = Maps.newHashMap();

        while (!filesQueue.isEmpty()) {
            File currentFile = filesQueue.pollFirst();
            if (currentFile != null) {
                if (currentFile.isDirectory()) {
                    File[] children = currentFile.listFiles();
                    if (children != null) {
                        Arrays.stream(children).forEach(filesQueue::addFirst);
                    }
                } else {
                    if (currentFile.getName().endsWith("VO.java")) {
                        String name = currentFile.getName();
                        String key = name.substring(0, name.indexOf('V'));
                        voMap.put(key,name.substring(0,name.indexOf('.')));
                        count++;
                    }
                }
            }
        }
        System.out.println(voMap);
        System.out.println("vo count=" + count);
        return voMap;
    }

    private static void initReplaceMap(HashMap<String, String> voMap, HashMap<String, String> replaceMap) throws IOException {
        File file = new File(viewManifestPath);
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));

        Pattern compileOfContainingValue = Pattern.compile(".*\"(.*)\":\"(.*)\".*");
        for (String line:lines){
            if (line.contains("forModel")){
                Matcher matcher = compileOfContainingValue.matcher(line);
                if (matcher.matches()){
                    String oldKey = matcher.group(2);
                    String oldModel = oldKey.substring(oldKey.lastIndexOf('_') + 1);
                    String oldModelPre = oldKey.substring(0,oldKey.lastIndexOf('_') + 1);
                    String vo = voMap.get(oldModel);

                    if (oldModel.endsWith("VO")){
                        vo = oldModel;
                    }

                    if (vo == null){
                        System.out.println("cannot find vo of "+oldModel);
                        continue;
                    }

                    String newKey;
                    if (!StringUtils.isEmpty(newModuleKey)){
                        newKey = newModuleKey +"_"+ vo;
                    }else {
                        newKey = oldModelPre+vo;
                    }

                    replaceMap.put(oldKey,newKey);
                }
            }
        }
    }

    public static void replace(HashMap<String, String> replaceMap) throws IOException {
        File file = new File(resourcePath);
        if (file.isDirectory()){

            File[] filesOfViewAndActions = file.listFiles();

            if (filesOfViewAndActions != null && filesOfViewAndActions.length > 0){

                for (File subFile:filesOfViewAndActions){
                    if (!subFile.isDirectory()){
                        replaceOneFile(replaceMap,subFile);
                    }
                }
            }
        }

    }

    private static void replaceOneFile(HashMap<String, String> replaceMap, File file) throws IOException {
//        if (file.getName().contains("view-manifest")){
//            return;
//        }
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
        ArrayList<String> replacedLines = Lists.newArrayList();

        Set<String> oldKeys = replaceMap.keySet();
//        Set<String> multiOldKeys = Sets.newHashSet();

        for (String line : lines){
            Map<Integer, List<String>> oldKeysFilter = oldKeys.stream().filter(line::contains).collect(Collectors.groupingBy(String::length));
            Optional<String> first = oldKeys.stream().filter(line::contains).findFirst();

            if (!CollectionUtils.isEmpty(oldKeysFilter)){
            }
            if (!CollectionUtils.isEmpty(oldKeysFilter)){
                String oldKey = oldKeysFilter.entrySet().stream().sorted((a,b)-> b.getKey().compareTo(a.getKey())).findFirst().get().getValue().get(0);
//                if (multiOldKeys.size()>1){
//                    System.out.println("there are at least two keys in one file");
//                }
//                multiOldKeys.add(oldKey);

                String replacement = replaceMap.get(oldKey);
                if (replacement == null){
                    System.out.println("cannot find newKey for oldKey="+oldKey);
                    replacedLines.add(line);
                    continue;
                }
                String replacedLine = line.replace(oldKey, replacement);
                replacedLines.add(replacedLine);
            }else {
                replacedLines.add(line);
            }
        }

        String lastLine = replacedLines.get(replacedLines.size() - 1);
        replacedLines.remove(replacedLines.size() - 1);

        Files.write(Paths.get(file.getPath()), replacedLines);
        Files.write(Paths.get(file.getPath()),lastLine.getBytes("UTF-8"), StandardOpenOption.APPEND);
    }

    public static void patch(String viewManifestUrl,String viewUrl) throws IOException {
        File file = new File(viewManifestUrl);
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
        HashMap<String, String> replaceMap = Maps.newHashMap();

        Pattern compileOfContainingValue = Pattern.compile(".*\"(.*)\":\"(.*)\".*");
        for (String line:lines){
            if (line.contains("forModel")){
                Matcher matcher = compileOfContainingValue.matcher(line);
                if (matcher.matches()){
                    String oldKey = matcher.group(2);
                    String oldModel = oldKey.substring(oldKey.lastIndexOf('_') + 1);

                    if (oldModel.contains("VO") && !oldModel.endsWith("VO")){
                        String model = oldModel.replace("VO", "");
                        String newKey = "itemcenter_" + model+"VO";
                        replaceMap.put(oldKey,newKey);
                    }

                }
            }
        }

        replace(replaceMap);
    }
}

