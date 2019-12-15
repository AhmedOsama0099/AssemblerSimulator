package com.assembler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunForm extends JFrame {
    private JTable memory;
    private JTable registers;
    private JList code;
    DefaultTableModel tableModelMemory = new DefaultTableModel();
    DefaultTableModel tableModelRegisters = new DefaultTableModel();
    private JPanel panel1;
    private JButton runWholeProgramButton;
    private JButton runNextLineButton;
    private JLabel progCount;
    int programCount = 0;
    RunForm() {
        programCount = 0;

        setTitle("Running");
        setSize(800, 600);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DefaultListModel dflm = new DefaultListModel();
        code.setModel(dflm);
        dflm.removeAllElements();
        dflm.addAll(Parser.userCodes);
        memory.setModel(tableModelMemory);

        tableModelMemory.addColumn("Bin");
        tableModelMemory.addColumn("Hex");
        tableModelMemory.addColumn("MemValue");
        Parser.tableModelMemory = tableModelMemory;
        for (int i = 0; i < 2000; i++) {

            tableModelMemory.insertRow(tableModelMemory.getRowCount(), new Object[]{i, "0x" + Integer.toString(i, 16), 0});
        }
        TableColumnModel columnModel = memory.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(220);

        registers.setEnabled(false);
        registers.setModel(tableModelRegisters);
        tableModelRegisters.addColumn("Register");
        tableModelRegisters.addColumn("Value");
        System.out.println(Parser.registerIndexes);
        System.out.println(Parser.registerIndexes.size());
        for (String reg : Parser.registerIndexes) {
            tableModelRegisters.insertRow(tableModelRegisters.getRowCount(), new Object[]{reg, Parser.mipsRegisters.get(reg)});
        }
        runWholeProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registers.setEnabled(false);
                runNextLineButton.setEnabled(false);
                runWholeProgramButton.setEnabled(false);
                for(;programCount<Parser.codeLines.size();programCount++){
                    progCount.setText(String.valueOf(programCount));
                    Parser.run(programCount);
                    for(int j=0;j<Parser.registerIndexes.size();j++){
                        tableModelRegisters.setValueAt(Parser.mipsRegisters.get(Parser.registerIndexes.get(j)),j,1);
                    }
                }
                registers.setEnabled(true);
                runNextLineButton.setEnabled(true);
                runWholeProgramButton.setEnabled(true);
            }
        });
        runNextLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

}
