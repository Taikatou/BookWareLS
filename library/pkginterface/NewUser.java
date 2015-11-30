/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkginterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.pkgdataBaseAdapter.User;
import library.pkgdataBaseAdapter.UserManager;

//based off code found here
public class NewUser extends JFrame
{

    private JButton create;
    private JPanel newUserPanel;
    private JTextField txuserer;
    private JTextField passer;
    private JLabel username;
    private JLabel password;

    public NewUser()
    {
        super("Registration");

        create = new JButton("Create");
        newUserPanel = new JPanel();
        txuserer = new JTextField(15);
        passer = new JPasswordField(15);
        username = new JLabel("User - ");
        password = new JLabel("Pass - ");

        setSize(300, 200);
        setLocation(500, 280);
        newUserPanel.setLayout(null);

        txuserer.setBounds(70, 30, 150, 20);
        passer.setBounds(70, 65, 150, 20);
        create.setBounds(110, 100, 80, 20);
        username.setBounds(20, 28, 80, 20);
        password.setBounds(20, 63, 80, 20);

        newUserPanel.add(create);
        newUserPanel.add(txuserer);
        newUserPanel.add(passer);
        newUserPanel.add(username);
        newUserPanel.add(password);

        getContentPane().add(newUserPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        create.addActionListener((ActionEvent e) ->
        {
            UserManager user_manager = UserManager.getManager();
            User u = user_manager.addUser(txuserer.getText(), passer.getText());
            if(u != null)
            {
                LibraryInterface libInterface = new LibraryInterface(u);
                dispose();
            }
        });
    }
}
