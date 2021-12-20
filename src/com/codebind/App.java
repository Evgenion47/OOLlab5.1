package com.codebind;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    private JButton Import;
    private JTextPane result;
    private JButton Export;
    private JPanel panelMain;
    private JScrollPane ScrollPane;

    public App() {
        Import.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION){
                    File selectedFile = jfc.getSelectedFile();
                    StringBuilder sb = new StringBuilder();
                    try (Scanner s = new Scanner(selectedFile)) {

                        while (s.hasNext()){
                            sb.append(s.nextLine());
                            sb.append('\n');
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    String[] words = sb.toString().split("[., \n\t\r-]+");
                    sb.setLength(0);
                    Map<String,Integer> wordsMap = new HashMap<>();
                    for (String w:words){
                        if (!wordsMap.containsKey(w)){
                            wordsMap.put(w,1);
                        }
                        else {
                            wordsMap.put(w,wordsMap.get(w)+1);
                        }
                    }
                    wordsMap.forEach((key,value)->{
                        if (value == 1){
                            sb.append(key+"\n");
                        }
                    });
                    result.setText(sb.toString());
                }
            }
        });
        Export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(panelMain);
                if (userSelection == JFileChooser.APPROVE_OPTION){
                    File fileToSave = fileChooser.getSelectedFile();
                    try (BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave))) {
                        out.write(result.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("App");

        frame.setContentPane(new App().panelMain);
        frame.pack();
        frame.setVisible(true);
    }
}
