package com.example.dubbo.springbootdubboconsumer;

public class TestFun5 {

    private String test = "TestFun5.test";

    public static void main(String[] args){
        new TestFun5().output();
    }

    private void output(){
        Function function = ()->{
          System.out.println(this);
          System.out.println(this.test);
          System.out.println("11111=================");
        };

        function.outputThis();

        new Function(){

            @Override
            public void outputThis() {
                System.out.println(this);
                System.out.println(this.test);
                System.out.println("22222=================");
            }
        }.outputThis();


    }


}

interface Function{
    String test = "Function.test";
    void outputThis();
}
