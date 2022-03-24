package com.example.dubbo.springbootdubboconsumer;

import java.util.function.*;

public class TestFun4 {
    public static void main(String[] args){
        /*Function<Integer,Integer> function = x -> x+1;
        System.out.println(function.apply(3));
        BiFunction<Integer,Integer,Integer> f2 = (x,y)->x+y;
        System.out.println(f2.apply(3,4));
        BiConsumer<Integer,Integer> biConsumer = (x,y)->{
            System.out.println("Math.pow("+x+","+y+")="+Math.pow(x,y));
        };
        biConsumer.accept(3,5);*/
        Consumer<Integer> consumer = x ->{
          System.out.println(x+"===============");
        };
        consumer.accept(3);
        Supplier<Integer> supplier = ()->{
            System.out.println("no arguments");
            return 3;
        };
        System.out.println("supplier return value is "+supplier.get());
    }
}
