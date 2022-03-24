package com.example.dubbo.springbootdubboconsumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {

    public static void main(String[] args) throws Exception{
        //new TestStream().fileReadWithStream("E://bmworkspace2//springboot-dubbo-consumer//logging.log");
        //new TestStream().streamForeach("rangeClosed");
        String[] str = {"A","a","cd","e"};
        //new TestStream().streamArray(str,false);
        str = new String[]{"BCD","EFG"};
        //new TestStream().stramMap(str);
        //new TestStream().streamFlat(str);
        //new TestStream().streamFilter(0,10);
        //new TestStream().streamSortAndDistinct();
        new TestStream().streamList(str).stream().forEach(System.out::println);
    }

    public List streamList(String[] strArray){
        return Stream.of(strArray).flatMap(x->Stream.of(x.split(""))).collect(Collectors.toList());
    }

    public void streamFilter(int start ,int end){
        IntStream.range(start,end).filter(x->x%2==1).forEach(System.out::println);
    }

    public void streamSortAndDistinct(){
        Integer[] intArray = new Integer[]{1,3,1,4,4,3,0,21,44,1,5,4,3,2,1};//不能使用int，是Stream.of对基础类型支持不行吗？
        Stream.of(intArray).sorted().forEach(System.out::println);
        Stream.of(intArray).sorted().distinct().forEach(System.out::println);
    }

    public void stramMap(String[] args){
        Stream.of(args).map(x->x.split("")).forEach(System.out::println);
    }
    public void streamFlat(String[] args){
        Stream.of(args).flatMap(x->Stream.of(x.split(""))).forEach(System.out::println);
    }

    public void streamArray(String[] args,boolean flag){
        if(flag){
            Stream.of(args).forEach(System.out::println);
        }else{
            Arrays.asList(args).stream().forEach(System.out::println);
        }
    }

    public void fileReadWithStream(String path) throws Exception{
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        bufferedReader.lines().forEach(System.out::println);
        bufferedReader.close();
    }

    public void streamForeach(String operation){
        if("range".equals(operation)){
            IntStream.range(1,4).map(x->x*x).forEach(System.out::println);
        }else if("rangeClosed".equals(operation)){
            IntStream.rangeClosed(1,4).map(x->x*x).forEach(System.out::println);
        }
    }


}
