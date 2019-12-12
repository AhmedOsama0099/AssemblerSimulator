package com.assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    private static ArrayList<Instruction> codeLines = new ArrayList<>();
    public final ArrayList<String> instructions = new ArrayList<>(

            Arrays.asList("add", "sub", "and", "or", "sll", "slt", "lw", "sw",
                    "addi", "andi", "ori", "slti", "lui",
                    "jr", "j", "beq", "bne"
            ));

    Parser(String inputCode) {
        codeLines.clear();
        splitCodeAndLabelsAndCheckValidate(inputCode);
    }

    private String splitCodeAndLabelsAndCheckValidate(String inputCode) {
        String exceptions = "";
        //String instruction="";
        boolean op = true;//first one is command
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
                if (line == "\n")
                    continue;
                line = line.trim();
                checkLabel = line.indexOf(":");
                if (checkLabel != -1) {
                    if (checkLabel != line.length() - 1) {
                        exceptions += "error label definition at line " + currentLine + "\n";
                    } else if (line.length() == 1) {
                        exceptions += "no label name at line " + currentLine + "\n";
                    } else {
                        instruction.isLabel = true;
                        String labelName = line.substring(0, checkLabel - 1);
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
                    line=line.substring(tmp.length()+1);
                    line=line.trim();
                    String []args=line.split(",");
                    for(int i=0;i<args.length;i++){
                        if(args[i].length()==0){
                            exceptions+="unexpected , at line "+currentLine+"\n";
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
