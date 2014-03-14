package main;

import mines.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame implements ActionListener{
    JLabel choiceLabel, nameLabel;
    JTextField nameText;
    JButton beginner, normal, expert, exit;
    Container con;
    
    MainMenu() {
        con = getContentPane();
        FlowLayout flow = new FlowLayout();
        con.setLayout(null);
        
        choiceLabel = new JLabel("Choose Difficulty");
        con.add(choiceLabel);
        beginner = new JButton("Beginner");
        con.add(beginner);
        //actionPerformed ap = new actionPerformed();
        //beginner.addActionPerformed();
        normal = new JButton("Normal");
        con.add(normal);
        expert = new JButton("Expert");
        con.add(expert);
        exit = new JButton("EXIT");
        con.add(exit);
        Insets insets = con.getInsets();
        
        choiceLabel.setBounds(insets.left+10, insets.top+5, 100, 25);
        beginner.setBounds(insets.left+10, insets.top+32, 100, 25);
        normal.setBounds(insets.left+10, insets.top+57, 100, 25);
        expert.setBounds(insets.left+10, insets.top+82, 100, 25);
        exit.setBounds(insets.left+10, insets.top+107, 100, 25);
        
	beginner.addActionListener( this );
	normal.addActionListener( this );
	expert.addActionListener( this );
	exit.addActionListener( this );    	

	setSize(100,200);
        setVisible(true);
        setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent event) {
        if(event.getActionCommand().equals("Beginner")) {
            Mines ms = new Mines(180, 205, 1);
        } 
        if(event.getActionCommand().equals("Normal")) {
            Mines ms = new Mines(280, 305, 2);
        } 
        if(event.getActionCommand().equals("Expert")) {
            Mines ms = new Mines(405, 430, 3);
        } 
        if(event.getActionCommand().equals("EXIT")) {
            System.exit(0);
        }
    }
    
    public static void main(String [] args) {
        MainMenu mn = new MainMenu();
	//while( mn != 0 ){ mn = new MainMenu(); }
    }
}
// window listner