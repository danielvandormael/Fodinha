package Main.GUI;

import Main.Logic.Game;
import Main.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JPanel panel1;
    private JRadioButton twoRadioButton;
    private JRadioButton threeRadioButton;
    private JRadioButton fiveRadioButton;
    private JRadioButton sixRadioButton;
    private JRadioButton fourRadioButton;
    private JButton playButton;
    private JRadioButton oneLRadioButton;
    private JRadioButton twoLRadioButton;
    private JRadioButton threeLRadioButton;
    private JRadioButton fourLRadioButton;
    private JRadioButton fiveLRadioButton;

    JFrame  frame = new JFrame();
    ButtonGroup group1 = new ButtonGroup();
    ButtonGroup group2 = new ButtonGroup();

    public Menu(){
        createGUI();

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(group1.getSelection() != null && group2.getSelection() != null){
                    frame.dispose();
                    new Game(Integer.parseInt(group1.getSelection().getActionCommand()), Integer.parseInt(group2.getSelection().getActionCommand()));
                }
            }
        });
    }

    private void createGUI(){
        JPanel theroot = getRootPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setContentPane(theroot);
        frame.setTitle("FODINHA");

        ImageIcon icon = new ImageIcon("src/Main/Images/logo.png");
        frame.setIconImage(icon.getImage());

        frame.setLocationRelativeTo(null);

        twoRadioButton.setActionCommand("2");
        group1.add(twoRadioButton);
        threeRadioButton.setActionCommand("3");
        group1.add(threeRadioButton);
        fourRadioButton.setActionCommand("4");
        group1.add(fourRadioButton);
        fiveRadioButton.setActionCommand("5");
        group1.add(fiveRadioButton);
        sixRadioButton.setActionCommand("6");
        group1.add(sixRadioButton);

        oneLRadioButton.setActionCommand("1");
        group2.add(oneLRadioButton);
        twoLRadioButton.setActionCommand("2");
        group2.add(twoLRadioButton);
        threeLRadioButton.setActionCommand("3");
        group2.add(threeLRadioButton);
        fourLRadioButton.setActionCommand("4");
        group2.add(fourLRadioButton);
        fiveLRadioButton.setActionCommand("5");
        group2.add(fiveLRadioButton);

        frame.setVisible(true);
    }

    public JPanel getRootPanel(){
        return panel1;
    }
}
