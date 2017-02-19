package com.bookies.Views;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Emil on 2017-02-18.
 */
public class BookiesView {
    public JFrame jFrame;
    public JTextPane console;
    public JTextField input;
    public JScrollPane scrollPane;

    public StyledDocument document;

    boolean trace = false;

    public BookiesView() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jFrame = new JFrame();
        jFrame.setTitle("Console");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        console = new JTextPane();
        console.setFont(new Font("Courier New", Font.PLAIN, 12));
        console.setEditable(false);
        console.setOpaque(false);

        document = console.getStyledDocument();


        input = new JTextField();
        input.setFont(new Font("Courier New", Font.PLAIN, 12));
        input.setEditable(true);
        input.setForeground(Color.WHITE);
        input.setCaretColor(Color.GREEN);
        input.setOpaque(false);

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = input.getText();

                if (text.length() > 1) {
                    print(text + "\n", false);

                    doCommand(text);
                    scrollBottom();
                    input.selectAll();
                }
            }
        });

        input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        scrollPane = new JScrollPane(console);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        jFrame.add(input, BorderLayout.SOUTH);
        jFrame.add(scrollPane, BorderLayout.CENTER);
        jFrame.getContentPane().setBackground(new Color(50, 50, 50));
        jFrame.setSize(660, 350);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    public void doCommand(String s) {

    }

    public void scrollTop() {
        console.setCaretPosition(0);
    }

    public void scrollBottom() {
        console.setCaretPosition(console.getDocument().getLength());
    }

    public void print(String s, boolean trace) {
        print(s, trace, new Color(255, 255, 255));
    }

    public void print(String s, boolean trace, Color c) {
        Style style = console.addStyle("Style", null);
        StyleConstants.setForeground(style, c);

        if (trace) {
            Throwable t = new Throwable();
            StackTraceElement[] elements = t.getStackTrace();
            String caller = elements[0].getClassName();

            s = caller + " -> " + s;

        }
        try {
            document.insertString(document.getLength(), s, style);
        } catch (Exception ex) {

        }
    }

    public void println(String s, boolean trace) {
        println(s, trace, new Color(255, 255, 255));
    }

    public void println(String s, boolean trace, Color c) {
        print(s + "\n", trace, c);
    }

    public void clear() {
        try {
            document.remove(0, document.getLength());
        } catch (Exception ex) {

        }
    }
}
