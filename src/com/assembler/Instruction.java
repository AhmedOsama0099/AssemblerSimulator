package com.assembler;

import java.util.ArrayList;

public class Instruction {
    String instruct;//
    String labelName;
    ArrayList<String>args=new ArrayList<>();
    Boolean isLabel=false;
    Boolean rType=false;
    Boolean iType=false;
    Boolean jType=false;

    public Instruction() {
    }

    public Instruction(String instruct, ArrayList<String> args, Boolean isLabel, Boolean rType, Boolean iType, Boolean jType) {
        this.instruct = instruct;
        this.args = args;
        this.isLabel = isLabel;
        this.rType = rType;
        this.iType = iType;
        this.jType = jType;
    }
}
