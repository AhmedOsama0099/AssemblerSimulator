package com.assembler;

public class Main {

    public static void main(String[] args) {
	// write your code here
       // Parser parser=new Parser();
        String x="16($s4)(";
        String xx="()";
        String[]arr=x.split(xx);
        System.out.println(arr.length);
        for(int i=0;i<arr.length;i++)
            System.out.println(arr[i]+"      "+arr[i].length());
    }
}
