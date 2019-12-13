package com.assembler;

public class Main {

    public static void main(String[] args) {
	// write your code here
       // Parser parser=new Parser();
        String x="start";
        String[]arr=x.split(",");
        System.out.println(arr.length);
        for(int i=0;i<arr.length;i++)
            System.out.println(arr[i]+"      "+arr[i].length());
    }
}
