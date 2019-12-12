package com.assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static ArrayList<Instruction> codeLines = new ArrayList<>();
    public final Map<String, Integer> mipsRegisters = new HashMap<>();

    public final ArrayList<String> instructions = new ArrayList<>(

            Arrays.asList("add", "sub", "and", "or", "sll", "slt", "lw", "sw",
                    "addi", "andi", "ori", "slti", "lui",
                    "jr", "j", "beq", "bne"
            ));

    ///
    Parser(String inputCode) {
        codeLines.clear();
        mipsRegisters.put("$0", 0);
        mipsRegisters.put("$at", 0);
        mipsRegisters.put("$v0", 0);
        mipsRegisters.put("$v1", 0);
        int count = 4;
        while (count < 32) {
            if (count < 8)
                mipsRegisters.put("$a" + (count - 4), 0);
            else if (count < 16)
                mipsRegisters.put("$t" + (count - 8), 0);
            else if (count < 24)
                mipsRegisters.put("$s" + (count - 16), 0);
            else if (count < 26) {
                mipsRegisters.put("$t8", 0);
                mipsRegisters.put("$t9", 0);
                mipsRegisters.put("$k0", 0);
                mipsRegisters.put("$k1", 0);
                mipsRegisters.put("$gp", 0);
                mipsRegisters.put("$sp", 0);
                mipsRegisters.put("$fp", 0);
                mipsRegisters.put("$ra", 0);
                break;
            }
            count++;
        }
        System.out.println(mipsRegisters);
        String exceptions = splitCodeAndLabelsAndCheckValidate(inputCode);
        if (!exceptions.isEmpty()) {
            System.out.println(exceptions);
        } else {
            for (int i = 0; i < codeLines.size(); i++) {
                /*System.out.println("----------------------");
                System.out.println(codeLines.get(i).instruct);
                for(int j=0;j<codeLines.get(i).args.size();j++){
                    System.out.println(codeLines.get(i).args.get(j));
                }*/
                Instruction instruction = codeLines.get(i);
                switch (instructions.indexOf(instruction.instruct)) {
                    case 0:
                        Commands.add(mipsRegisters, instruction.args);
                        break;
                    case 1:
                        Commands.sub(mipsRegisters, instruction.args);
                        break;
                    case 2:
                        Commands.and(mipsRegisters, instruction.args);
                        break;
                    case 3:
                        Commands.or(mipsRegisters, instruction.args);
                        break;
                    case 4:
                        Commands.sll(mipsRegisters, instruction.args);
                        break;
                    case 5:
                        Commands.slt(mipsRegisters, instruction.args);
                        break;
                    case 6:
                        Commands.lw();
                        break;
                    case 7:
                        Commands.sw();
                        break;

                    case 8:
                        Commands.addi(mipsRegisters, instruction.args);
                        break;
                    case 9:
                        Commands.andi(mipsRegisters, instruction.args);
                        break;
                    case 10:
                        Commands.ori(mipsRegisters, instruction.args);
                        break;
                    case 11:
                        Commands.slti(mipsRegisters, instruction.args);
                        break;
                    case 12:
                        Commands.lui();
                        break;
                    case 13:
                        Commands.jr();
                        break;
                    case 14:
                        int index=Commands.j(codeLines,instruction.args);
                        if(index !=-1){
                            i=index;
                        }

                        break;
                    case 15:
                        int index2=Commands.beq(mipsRegisters,codeLines,instruction.args,i);
                        if(index2 !=-1){
                            i=index2;
                        }
                        else{
                            //exc label not found
                        }
                        break;
                    case 16:
                        int index3=Commands.bne(mipsRegisters,codeLines,instruction.args,i);
                        if(index3 !=-1){
                            i=index3;
                        }
                        else{
                            //exc label not found
                        }
                        break;




                }


            }
            System.out.println(mipsRegisters);
        }
    }


    private String splitCodeAndLabelsAndCheckValidate(String inputCode) {
        String exceptions = "";
        //String instruction="";
        // boolean op = true;//first one is command
        Instruction instruction;
        String line;
        ///String label="";
        int checkLabel;
        int currentLine = 0;
        BufferedReader reader = new BufferedReader(new StringReader(inputCode));
        try {
            while ((line = reader.readLine()) != null) {
                currentLine++;
                instruction = new Instruction();
                codeLines.add(instruction);
                if (line == "\n")
                    continue;
                line = line.trim();
                checkLabel = line.indexOf(":");
                if (checkLabel != -1) {
                    if (checkLabel != line.length() - 1) {
                        exceptions += "unexpected : at line " + currentLine + "\n";
                    } else if (line.length() == 1) {
                        exceptions += "no label name at line " + currentLine + "\n";
                    } else {
                        instruction.isLabel = true;
                        String labelName = line.substring(0, checkLabel );
                        instruction.labelName = (labelName.trim());
                        System.out.println(labelName.trim());
                        codeLines.add(instruction);
                        //instruction=new Instruction();
                    }
                } else {
                    line = line.trim();
                    instruction.instruct = "";
                    // boolean isInstruct = true;
                    String tmp = "";
                    for (int i = 0; i < line.length(); i++) {
                        tmp += line.charAt(i);
                        tmp = tmp.trim();
                        if (i == line.length() - 1) {
                            if (instructions.indexOf(tmp) == -1)
                                exceptions += "incorrect instruct at line " + currentLine + "\n";
                            else
                                exceptions += "too few argument at line " + currentLine + "\n";
                            continue;
                        }
                        if (line.charAt(i) == ' ' || line.charAt(i) == '$') {
                            //isInstruct = false;
                            tmp = tmp.trim();
                            if (tmp.charAt(tmp.length() - 1) == '$') {
                                StringBuilder sb = new StringBuilder(tmp);
                                sb.deleteCharAt(tmp.length() - 1);
                                tmp = sb.toString();
                            }
                            int instructionIndex = instructions.indexOf(tmp);
                            if (instructionIndex == -1) {
                                exceptions += "incorrect instruct at line " + currentLine + "\n";
                                break;
                            } else {
                                instruction.instruct = tmp;
                                break;
                            }
                        }
                    }
                    line = line.substring(tmp.length() + 1);
                    line = line.trim();
                    String[] args = line.split(",");
                    boolean unKnownRegister = false;
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].length() == 0) {//,,
                            exceptions += "unexpected , at line " + currentLine + "\n";
                            break;
                        } else {
                            args[i] = args[i].trim();
                            if (mipsRegisters.containsKey(args[i]) || args[i].charAt(0) != '$')
                                instruction.args.add(args[i].trim());
                            else {
                                unKnownRegister = true;
                                exceptions += "unknown register at line " + currentLine + "\n";
                                break;
                            }
                        }
                    }
                    if (!unKnownRegister) {
                        switch (instructions.indexOf(instruction.instruct)) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 5:
                                if (instruction.args.size() != 3) {
                                    exceptions += "argument should be 3 line " + currentLine + "\n";
                                } else if (instruction.args.get(0).equals("$0")) {
                                    exceptions += "u shouldn't initialize value in $0 line " + currentLine + "\n";
                                }
                                else if(instruction.args.get(0).charAt(0)!='$'||instruction.args.get(1).charAt(0)!='$'||instruction.args.get(2).charAt(0)!='$')
                                    exceptions += "argument 0 and 1 should be registers line " + currentLine + "\n";
                                break;
                            case 4:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                                if (instruction.args.size() != 3) {
                                    exceptions += "argument should be 3 line " + currentLine + "\n";
                                } else if (instruction.args.get(0).equals("$0")) {
                                    exceptions += "u shouldn't initialize value in $0 line " + currentLine + "\n";
                                }
                                else if(instruction.args.get(0).charAt(0)!='$'||instruction.args.get(1).charAt(0)!='$')
                                    exceptions += "argument 0 and 1 should be registers line " + currentLine + "\n";
                                else{
                                    try {
                                        Integer.parseInt(instruction.args.get(2));
                                    }catch (Exception e){
                                        exceptions += "addi third argument should be constant " + currentLine + "\n";
                                    }
                                }
                                break;
                            case 14:
                                if (instruction.args.size() != 1) {
                                    exceptions += "argument should be 1 line " + currentLine + "\n";
                                }
                                else if(instruction.args.get(0).charAt(0)=='$'){
                                    exceptions += "Jump argument should be label line " + currentLine + "\n";
                                }
                                break;
                            case 15:
                            case 16:
                                if (instruction.args.size() != 3) {
                                    exceptions += "argument should be 3 line " + currentLine + "\n";
                                }
                                else if(instruction.args.get(0).charAt(0)!='$'||instruction.args.get(1).charAt(0)!='$'||instruction.args.get(2).charAt(0)=='$')
                                {
                                    exceptions += "argument 0 and 1  should be registers and 2 should be label line " + currentLine + "\n";
                                }
                                break;


                        }
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return exceptions;
    }
}
