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
    public static ArrayList<String> labelNedToBeFound = new ArrayList<>();
    public static ArrayList<String> registerIndexes = new ArrayList<>();
    public final ArrayList<String> instructions = new ArrayList<>(
            Arrays.asList("add", "sub", "and", "or", "sll", "slt", "lw", "sw",
                    "addi", "andi", "ori", "slti", "lui",
                    "jr", "j", "beq", "bne"
            ));
    public static int[] memory=new int[2000];
    ///
    Parser(String inputCode) {
        labelNedToBeFound.clear();
        codeLines.clear();
        mipsRegisters.put("$0", 0);
        mipsRegisters.put("$at", 0);
        mipsRegisters.put("$v0", 0);
        mipsRegisters.put("$v1", 0);
        registerIndexes.add("$0");
        registerIndexes.add("$at");
        registerIndexes.add("$v0");
        registerIndexes.add("$v1");
        int count = 4;
        while (count < 32) {
            if (count < 8) {
                mipsRegisters.put("$a" + (count - 4), 0);
                registerIndexes.add("$a" + (count - 4));
            } else if (count < 16) {
                mipsRegisters.put("$t" + (count - 8), 0);
                registerIndexes.add("$t" + (count - 8));
            } else if (count < 24) {
                mipsRegisters.put("$s" + (count - 16), 0);
                registerIndexes.add("$s" + (count - 16));
            } else if (count < 26) {
                mipsRegisters.put("$t8", 0);
                mipsRegisters.put("$t9", 0);
                mipsRegisters.put("$k0", 0);
                mipsRegisters.put("$k1", 0);
                mipsRegisters.put("$gp", 0);
                mipsRegisters.put("$sp", 0);
                mipsRegisters.put("$fp", 0);
                mipsRegisters.put("$ra", 0);
                registerIndexes.add("$t8");
                registerIndexes.add("$t9");
                registerIndexes.add("$k0");
                registerIndexes.add("$k1");
                registerIndexes.add("$gp");
                registerIndexes.add("$sp");
                registerIndexes.add("$fp");
                registerIndexes.add("$ra");
                break;
            }
            count++;
        }
        System.out.println(memory[1400]);
        System.out.println(registerIndexes);
        System.out.println(mipsRegisters);
        String exceptions = splitCodeAndLabelsAndCheckValidate(inputCode);
        for (int i = 0; i < labelNedToBeFound.size(); i++) {
            String[] labelAndErrorLine = labelNedToBeFound.get(i).split(" ");
            if (!labelFound(labelAndErrorLine[0])) {
                exceptions += labelAndErrorLine[0] + " not found at line " + labelAndErrorLine[1];
            }
        }
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
//                        Commands.lw();
                        break;
                    case 7:
//                        Commands.sw();
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
                        int index = Commands.j(codeLines, instruction.args);
                        if (index != -1) {
                            i = index;
                        }

                        break;
                    case 15:
                        int index2 = Commands.beq(mipsRegisters, codeLines, instruction.args, i);
                        if (index2 != -1) {
                            i = index2;
                        } else {
                            //exc label not found
                        }
                        break;
                    case 16:
                        int index3 = Commands.bne(mipsRegisters, codeLines, instruction.args, i);
                        if (index3 != -1) {
                            i = index3;
                        } else {
                            //exc label not found
                        }
                        break;


                }


            }
            System.out.println(mipsRegisters);
        }
    }

    private boolean labelFound(String s) {
        for (int i = 0; i < codeLines.size(); i++) {
            if (codeLines.get(i).isLabel && codeLines.get(i).labelName.equals(s)) {
                return true;
            }
        }
        return false;
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
                        String labelName = line.substring(0, checkLabel);
                        labelName = labelName.trim();
                        if (labelName.contains(" ")) {
                            exceptions += "label name shouldn't contain spaces at line " + currentLine + "\n";
                            continue;
                        }
                        instruction.labelName = (labelName);
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
                        args[i] = args[i].trim();
                        if (args[i].length() == 0) {//,,
                            exceptions += "unexpected , at line " + currentLine + "\n";
                            break;
                        } else {
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
                                    exceptions += "argument should be 3 at line " + currentLine + "\n";
                                } else if (instruction.args.get(0).equals("$0")) {
                                    exceptions += "u shouldn't initialize value in $0 at line " + currentLine + "\n";
                                } else if (instruction.args.get(0).charAt(0) != '$' || instruction.args.get(1).charAt(0) != '$' || instruction.args.get(2).charAt(0) != '$')
                                    exceptions += "argument 0 and 1 should be registers at line " + currentLine + "\n";
                                else {
                                    instruction.rType = true;
                                }
                                break;
                            case 4:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                                if (instruction.args.size() != 3) {
                                    exceptions += "argument should be 3 line " + currentLine + "\n";
                                } else if (instruction.args.get(0).equals("$0")) {
                                    exceptions += "u shouldn't initialize value in $0 at line " + currentLine + "\n";
                                } else if (instruction.args.get(0).charAt(0) != '$' || instruction.args.get(1).charAt(0) != '$')
                                    exceptions += "argument 0 and 1 should be registers at line " + currentLine + "\n";
                                else {
                                    try {
                                        Integer.parseInt(instruction.args.get(2));
                                        instruction.iType = true;
                                    } catch (Exception e) {
                                        exceptions += "addi third argument should be constant at line " + currentLine + "\n";
                                    }
                                }
                                break;
                            case 12:
                                if(instruction.args.get(0).equals("$0")){
                                    exceptions+="u cannot load word in $0 at line " + currentLine + "\n";
                                }
                            case 13:
                                if(instruction.args.size()!=2){
                                    exceptions+="argument should be 2 at line " + currentLine + "\n";
                                }
                                else if(instruction.args.get(0).charAt(0)!='$'){
                                    exceptions+="first argument should be register at line " + currentLine + "\n";
                                }else{
                                    exceptions+=handelLwAndSW(instruction,currentLine);
                                }
                                break;
                            case 14:
                                if (instruction.args.size() != 1) {
                                    exceptions += "argument should be 1 at line " + currentLine + "\n";
                                } else if (instruction.args.get(0).charAt(0) == '$') {
                                    exceptions += "Jump argument should be label at line " + currentLine + "\n";
                                } else {
                                    labelNedToBeFound.add(instruction.args.get(0) + " " + currentLine);
                                    instruction.jType = true;
                                }
                                break;
                            case 15:
                            case 16:
                                if (instruction.args.size() != 3) {
                                    exceptions += "argument should be 3 at line " + currentLine + "\n";
                                } else if (instruction.args.get(0).charAt(0) != '$' || instruction.args.get(1).charAt(0) != '$' || instruction.args.get(2).charAt(0) == '$') {
                                    exceptions += "argument 0 and 1  should be registers and 2 should be label at line " + currentLine + "\n";
                                } else {
                                    labelNedToBeFound.add(instruction.args.get(2) + " " + currentLine);
                                    instruction.iType = true;
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

    private String handelLwAndSW(Instruction instruction,int currentLine) {
        //ArrayList<String>args=instruction.args;
        ArrayList<String>newArgs=new ArrayList<>();
        newArgs.add(instruction.args.get(0));
        int chk1=0,chk2=0;
        String exceptions="";
        String tmp="";
        String secondArgs=instruction.args.get(1);
        for(int i=0;i<secondArgs.length();i++){
            tmp+=secondArgs.charAt(i);
            if(secondArgs.charAt(i)=='('){
                chk1++;
                try {
                    StringBuilder sb = new StringBuilder(tmp);
                    sb.deleteCharAt(tmp.length() - 1);
                    tmp = sb.toString();
                    tmp=tmp.trim();
                    Integer.valueOf(tmp);
                    newArgs.add(tmp);
                    tmp="";
                }catch (Exception e){
                    exceptions+="error at second argument at line "+currentLine+"\n";
                }
            }
            else if(secondArgs.charAt(i)==')'){
                chk2++;
                StringBuilder sb = new StringBuilder(tmp);
                sb.deleteCharAt(tmp.length() - 1);
                tmp = sb.toString();
                tmp=tmp.trim();
                newArgs.add(tmp);
                if(!mipsRegisters.containsKey(tmp)){
                    exceptions+="second argument should contain register at line "+currentLine+"\n";
                }
            }
        }
        if(chk1>1){
            exceptions+="unExcpected ( at line "+currentLine+"\n";
        }
        else if(chk2>1){
            exceptions+="unExcpected ) at line "+currentLine+"\n";
        }
        if(newArgs.size()!=2){
            exceptions+="error at line "+currentLine+"\n";
        }
        if(exceptions.isEmpty()){
            instruction.args=newArgs;
        }
        return exceptions;
    }

}
