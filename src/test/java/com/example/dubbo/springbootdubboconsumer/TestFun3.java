package com.example.dubbo.springbootdubboconsumer;

public class TestFun3 {

    public static void main(String[] args){
        execute("hello world",()->{System.out.println("function callable");});
    }

    public static void execute(String arg,Callable callable){
        System.out.println(arg);
        callable.call();
    }

}


@FunctionalInterface
interface Callable{
    void call();
}
