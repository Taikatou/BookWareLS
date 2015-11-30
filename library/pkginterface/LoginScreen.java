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
import library.pkgdataBaseAdapter.Example;
import library.pkgdataBaseAdapter.User;
import library.pkgdataBaseAdapter.UserManager;


//based of code found here http://stackoverflow.com/questions/26665538/java-gui-login-screen
public class LoginScreen extends JFrame
{
    private JButton blogin;
    private JPanel loginpanel;
    private JTextField txuser;
    private JTextField pass;
    private JButton newUSer;
    private JLabel username;
    private JLabel password;
    public LoginScreen()
    {
        blogin = new JButton("Login");
        loginpanel = new JPanel();
        txuser = new JTextField(15);
        pass = new JPasswordField(15);
        newUSer = new JButton("New User?");
        username = new JLabel("User - ");
        password = new JLabel("Pass - ");

        setSize(300, 200);
        setLocation(500, 280);
        loginpanel.setLayout(null);

        txuser.setBounds(70, 30, 150, 20);
        pass.setBounds(70, 65, 150, 20);
        blogin.setBounds(110, 100, 80, 20);
        newUSer.setBounds(110, 135, 80, 20);
        username.setBounds(20, 28, 80, 20);
        password.setBounds(20, 63, 80, 20);

        loginpanel.add(blogin);
        loginpanel.add(txuser);
        loginpanel.add(pass);
        loginpanel.add(newUSer);
        loginpanel.add(username);
        loginpanel.add(password);

        getContentPane().add(loginpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        blogin.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                UserManager user_manager = UserManager.getManager();
                User u = user_manager.checkDetails(txuser.getText(), pass.getText());
                if (u != null)
                {
                    LibraryInterface libInterface = new LibraryInterface(u);
                    dispose();
                }
                else if (txuser.equals("") || pass.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Please insert Username and Password");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Wrong Username / Password");
                    txuser.setText("");
                    pass.setText("");
                    txuser.requestFocus();
                }
            }
        });

        newUSer.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                NewUser user = new NewUser();
                dispose();
            }
        });
    }
}