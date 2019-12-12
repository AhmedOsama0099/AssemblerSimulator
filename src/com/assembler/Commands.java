package com.assembler;

import java.util.ArrayList;
import java.util.Map;

public class Commands {

    public void add(Map<String,Integer> mipsRegisters,ArrayList<String> args){//add s0,s1,s2
        int temp=mipsRegisters.get(args.get(1))+mipsRegisters.get(args.get(2));
        mipsRegisters.replace(args.get(0),temp);
    }
    public void sub(Map<String,Integer> mipsRegisters,ArrayList<String> args){
        int temp=mipsRegisters.get(args.get(1))-mipsRegisters.get(args.get(2));
        mipsRegisters.replace(args.get(0),temp);
    }
    public void and(Map<String,Integer> mipsRegisters,ArrayList<String> args){
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1)) & mipsRegisters.get(args.get(2)));
    }
    public void or(Map<String,Integer> mipsRegisters,ArrayList<String> args){
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1)) | mipsRegisters.get(args.get(2)));
    }
    public void sll(Map<String,Integer> mipsRegisters,ArrayList<String> args){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(args.get(1))<<temp);
    }
    public void slt(Map<String,Integer> mipsRegisters, ArrayList<String> args){
        if(mipsRegisters.get(args.get(1))<mipsRegisters.get(args.get(2)))
            mipsRegisters.replace(args.get(0),1);
        else
            mipsRegisters.replace(args.get(0),0);
    }
    public void lw(){

    }
    public void sw(){

    }
    public void addi(Map<String,Integer> mipsRegisters, ArrayList<String> args){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(1)+temp);
    }
    public void andi(Map<String,Integer> mipsRegisters, ArrayList<String> args){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(1) & temp);
    }
    public void ori(Map<String,Integer> mipsRegisters, ArrayList<String> args){
        int temp=Integer.parseInt(args.get(2));
        mipsRegisters.replace(args.get(0),mipsRegisters.get(1) | temp);
    }
    public void slti(Map<String,Integer> mipsRegisters, ArrayList<String> args){
        int temp=Integer.parseInt(args.get(2));
        if(mipsRegisters.get(args.get(1))< temp)
            mipsRegisters.replace(args.get(0),1);
        else
            mipsRegisters.replace(args.get(0),0);
    }
    public void lui(){

    }
    public void jr(){

    }
    public int j(ArrayList<Instruction> codeLines,ArrayList<String> args){
        for(int i=0;i<codeLines.size();i++){
            if(codeLines.get(i).isLabel && codeLines.get(i).labelName.equals(args.get(0)))
                return i;
        }
        return -1;
    }
    public int beq(Map<String,Integer> mipsRegisters,ArrayList<Instruction> codeLines,ArrayList<String> args,int currentLine){
        int temp=mipsRegisters.get(args.get(1));
        if(mipsRegisters.get(args.get(0)) == temp)
            for(int i=0;i<codeLines.size();i++){
                if(codeLines.get(i).isLabel && codeLines.get(i).labelName.equals(args.get(2)))
                    return i;
            }
        else
            return currentLine;
        return -1;
    }
    public int bne(Map<String,Integer> mipsRegisters,ArrayList<Instruction> codeLines,ArrayList<String> args,int currentLine){
        int temp=mipsRegisters.get(args.get(1));
        if(mipsRegisters.get(args.get(0)) != temp)
            for(int i=0;i<codeLines.size();i++){
                if(codeLines.get(i).isLabel && codeLines.get(i).labelName.equals(args.get(2)))
                    return i;
            }
        else
            return currentLine;
        return -1;
    }

}
