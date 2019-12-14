package com.assembler;

import java.util.ArrayList;
import java.util.Map;

public class Commands {

    public static void add(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){//add s0,s1,s2
        int temp=mipsRegisters.get(args.get(1))+mipsRegisters.get(args.get(2));
        mipsRegisters.replace(args.get(0),temp);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void sub(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){
        int temp=mipsRegisters.get(args.get(1))-mipsRegisters.get(args.get(2));
        mipsRegisters.replace(args.get(0),temp);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void and(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1)) & mipsRegisters.get(args.get(2)));
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void or(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1)) | mipsRegisters.get(args.get(2)));
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void sll(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1))<<temp);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void slt(int[]memory,Map<String,Integer> mipsRegisters, ArrayList<String> args,String code){
        if(mipsRegisters.get(args.get(1))<mipsRegisters.get(args.get(2)))
            mipsRegisters.replace(args.get(0),1);
        else
            mipsRegisters.replace(args.get(0),0);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static String lw(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){
        int num=Integer.parseInt(args.get(1));
        int numOfRegister=mipsRegisters.get(args.get(2))+1000;
        if(num+numOfRegister<=2000) {
            mipsRegisters.replace(args.get(0),memory[num+numOfRegister]);
            System.out.println(memory[num+numOfRegister]);
            int insCode=Integer.parseInt(code);
            for(int i=0;i<1000;i++){
                if(memory[i]==0){
                    memory[i]=insCode;
                    break;
                }
            }
            return "";
        }
        else  return "Unvalid memory";
    }
    public static String sw(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){
        System.out.println(args+" ;;;;;;");
        int num=Integer.parseInt(args.get(1));
        int numOfRegister=mipsRegisters.get(args.get(2))+1000;
        if(num+numOfRegister<2000) {
            memory[num+numOfRegister]=mipsRegisters.get(args.get(0));
            System.out.println(memory[num+numOfRegister]);
            int insCode=Integer.parseInt(code);
            for(int i=0;i<1000;i++){
                if(memory[i]==0){
                    memory[i]=insCode;
                    break;
                }
            }
            return "";
        }
        else return "Unvalid memory";
    }
    public static void addi(int[]memory,Map<String,Integer> mipsRegisters, ArrayList<String> args,String code){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1))+temp);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void andi(int[]memory,Map<String,Integer> mipsRegisters, ArrayList<String> args,String code){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1)) & temp);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void ori(int[]memory,Map<String,Integer> mipsRegisters, ArrayList<String> args,String code){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1)) | temp);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void slti(int[]memory,Map<String,Integer> mipsRegisters, ArrayList<String> args,String code){
        int temp=Integer.parseInt(args.get(2));
        if(mipsRegisters.get(args.get(1))< temp)
            mipsRegisters.replace(args.get(0),1);
        else
            mipsRegisters.replace(args.get(0),0);
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
    }
    public static void lui(){

    }
    public static int jr(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<String> args,String code){
        int insCode=Integer.parseInt(code);
        for(int i=0;i<1000;i++){
            if(memory[i]==0){
                memory[i]=insCode;
                break;
            }
        }
        return mipsRegisters.get(args.get(0));

    }
    public static int j(int[]memory,ArrayList<Instruction> codeLines,ArrayList<String> args,String code){
       //System.out.println(args);
        for(int i=0;i<codeLines.size();i++){
            if(codeLines.get(i).isLabel && codeLines.get(i).labelName.equals(args.get(0))) {
                int insCode = Integer.parseInt(code);
                for (int j = 0; j < 1000; j++) {
                    if (memory[j] == 0) {
                        memory[j] = insCode;
                        break;
                    }
                }
                return i;
            }
        }
        return -1;
    }
    public static int beq(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<Instruction> codeLines,ArrayList<String> args,int currentLine,String code){
        int temp=mipsRegisters.get(args.get(1));
        if(mipsRegisters.get(args.get(0)) == temp)
            for(int i=0;i<codeLines.size();i++){
                if(codeLines.get(i).isLabel && codeLines.get(i).labelName.equals(args.get(2))){
                    int insCode = Integer.parseInt(code);
                    for (int j = 0; j < 1000; j++) {
                        if (memory[j] == 0) {
                            memory[j] = insCode;
                            break;
                        }
                    }
                    return i;
                }
            }
        else{
            int insCode = Integer.parseInt(code);
            for (int j = 0; j < 1000; j++) {
                if (memory[j] == 0) {
                    memory[j] = insCode;
                    break;
                }
            }
            return currentLine;
        }
            return -1;
    }
    public static int bne(int[]memory,Map<String,Integer> mipsRegisters,ArrayList<Instruction> codeLines,ArrayList<String> args,int currentLine,String code){
        int temp=mipsRegisters.get(args.get(1));
        if(mipsRegisters.get(args.get(0)) != temp)
            for(int i=0;i<codeLines.size();i++){
                if(codeLines.get(i).isLabel && codeLines.get(i).labelName.equals(args.get(2)))
                {   int insCode = Integer.parseInt(code);
                    for (int j = 0; j < 1000; j++) {
                        if (memory[j] == 0) {
                            memory[j] = insCode;
                            break;
                        }
                    }
                    return i;
                }
            }
        else{
            int insCode = Integer.parseInt(code);
            for (int j = 0; j < 1000; j++) {
                if (memory[j] == 0) {
                    memory[j] = insCode;
                    break;
                }
            }
            return currentLine;
        }
        return -1;
    }

}
