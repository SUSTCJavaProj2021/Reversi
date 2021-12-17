package com.demo.reversi;

public class Test3 {
    public static void main(String[] args) {
        try{
            System.out.println(Test3.class.getResource("QAQ.txt").toURI().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
