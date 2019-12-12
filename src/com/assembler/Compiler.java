package com.assembler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Compiler extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextArea inputCode;
    private JButton loadFileButton;
    private JButton startCompileButton;

    Compiler(){
        setTitle("Simulator");
        setSize(800, 600);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                //FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                //chooser.setFileFilter(filter);
                //chooser.setMultiSelectionEnabled(true);
                chooser.showOpenDialog(null);
                File f=chooser.getSelectedFile();
                if(f!=null){
                    try
                    {
                        FileReader reader = new FileReader( f.getPath());
                        BufferedReader br = new BufferedReader(reader);
                        inputCode.read( br, null );
                        br.close();
                        inputCode.requestFocus();
                    }
                    catch(Exception e2) { System.out.println(e2); }
                }
            }
        });
        startCompileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Parser parser=new Parser(inputCode.getText());
            }
        });
    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Compiler gui = new Compiler();
                gui.setVisible(true);
            }
        });
    }
}
