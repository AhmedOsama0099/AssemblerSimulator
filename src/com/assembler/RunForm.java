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
    DefaultTableModel tableModelMemory = new DefaultTableModel() {
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 0 || columnIndex == 1 || rowIndex < 1000)
                return false;
            return true;
        }
    };
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
                boolean hasException = false;
                String exceptions = "";
                for (int i = 0; i < 1000; i++) {
                    try {
                        Parser.memory[i] = Integer.parseInt(String.valueOf(tableModelMemory.getValueAt(i, 2)), 2);
                    } catch (Exception e) {
                        hasException = true;
                        exceptions += "error at index " + i + " should be binary from 31 bit\n";
                        break;
                    }
                }
                if (hasException) {
                    JOptionPane.showMessageDialog(null, exceptions);
                    return;
                }
                registers.setEnabled(false);
                runNextLineButton.setEnabled(false);
                runWholeProgramButton.setEnabled(false);
                for (; programCount < Parser.codeLines.size(); programCount++) {
                    programCount = Parser.run(programCount);
                    progCount.setText(String.valueOf(programCount));
                    for (int j = 0; j < Parser.registerIndexes.size(); j++) {
                        tableModelRegisters.setValueAt(Parser.mipsRegisters.get(Parser.registerIndexes.get(j)), j, 1);
                    }
                }
                programCount = 0;
                runNextLineButton.setEnabled(true);
                runWholeProgramButton.setEnabled(true);
            }
        });
        runNextLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean hasException = false;
                String exceptions = "";
                if(programCount==0){
                    for (int i = 0; i < 1000; i++) {
                        try {
                            Parser.memory[i] = Integer.parseInt(String.valueOf(tableModelMemory.getValueAt(i, 2)), 2);
                        } catch (Exception e) {
                            hasException = true;
                            exceptions += "error at index " + i + " should be binary from 31 bit\n";
                            break;
                        }
                    }
                    if (hasException) {
                        JOptionPane.showMessageDialog(null, exceptions);
                        return;
                    }
                }

                registers.setEnabled(false);
                runWholeProgramButton.setEnabled(false);
                memory.setEnabled(false);

                programCount = Parser.run(programCount);
                progCount.setText(String.valueOf(programCount));
                for (int j = 0; j < Parser.registerIndexes.size(); j++) {
                    tableModelRegisters.setValueAt(Parser.mipsRegisters.get(Parser.registerIndexes.get(j)), j, 1);
                }
                programCount++;
                if (programCount == Parser.codeLines.size()) {
                    programCount = 0;
                    runNextLineButton.setEnabled(true);
                    runWholeProgramButton.setEnabled(true);
                    memory.setEnabled(true);
                }


            }
        });
    }

}
