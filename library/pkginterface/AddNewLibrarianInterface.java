/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkginterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import library.pkgdataBaseAdapter.UserManager;

/**
 *
 * @author Conors PC
 */
public class AddNewLibrarianInterface extends JFrame
{
    private static JList                                list;
    private static DefaultListModel                     listModel;
    private JButton                                     TurnToLibrarian;        
    public AddNewLibrarianInterface()
    {
        UserManager userManager = UserManager.getManager();
        listModel = userManager.getListOfUsers(false);
        list = new LibraryJList(listModel);

        JScrollPane scrollBar = new JScrollPane(list);
        JPanel listOfAllUsers = new JPanel();
        JLabel text = new JLabel("All non-librarian users");
        listOfAllUsers.add(text);
        listOfAllUsers.setLayout(new BoxLayout(listOfAllUsers, BoxLayout.Y_AXIS));
        listOfAllUsers.add(scrollBar);
        TurnToLibrarian = new JButton("Turn into librarian");
        TurnToLibrarian.addActionListener(
                                            new ActionListener()
                                            {
                                                @Override
                                                public void actionPerformed(ActionEvent ae)
                                                {
                                                    int[] fromindex = list.getSelectedIndices();
                                                    for(int i = (fromindex.length - 1); i >= 0; i--)
                                                    {
                                                        String userString = listModel.get(fromindex[i]).toString();
                                                        userManager.turnUserIntoLibrarian(userString);
                                                        listModel.remove(fromindex[i]);
                                                    }
                                                }
                                            }
                                        );
        listOfAllUsers.add(TurnToLibrarian);
        add(listOfAllUsers);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Library Interface");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
