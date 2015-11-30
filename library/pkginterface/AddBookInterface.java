/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkginterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import library.pkgdataBaseAdapter.AudioBook;
import library.pkgdataBaseAdapter.Book;
import library.pkgdataBaseAdapter.BookManager;
import library.pkgdataBaseAdapter.LibraryItem;
import library.pkgdataBaseAdapter.LibraryItemWithPictureDecorator;

/**
 *
 * @author Conors PC
 */
public class AddBookInterface  extends JFrame
{
    private JButton add;
    private JTextField bookNameTextBox;
    private JLabel booknameText;
    private int[] duration = {3, 7, 14};
    protected JButton addButton;
    protected JComboBox loanLengthDecisionBox = new JComboBox();
    protected JComboBox typeOfItem = new JComboBox();
    JCheckBox checkbox;
    
    JComboBox addChoiceBox = new JComboBox();
    public AddBookInterface(DefaultListModel list)
    {
        super("Add Book");
        
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        
        JPanel nameContainer = new JPanel();
        nameContainer.setLayout(new BoxLayout(nameContainer, BoxLayout.Y_AXIS));
        booknameText = new JLabel("Enter name");
        nameContainer.add(booknameText);
        bookNameTextBox = new JTextField(20);
        nameContainer.add(bookNameTextBox);
        
        container.add(nameContainer);
        
        
        addButton = new JButton("Add book");
        addButton.addActionListener(new ActionListener()
                                        {
                                            @Override
                                            public void actionPerformed(ActionEvent ae)
                                            {
                                                if(bookNameTextBox.getText().length() > 0)
                                                {
                                                    BookManager bookManager = BookManager.getManager();
                                                    if(bookManager.findItemByName(bookNameTextBox.getText()) == null)
                                                    {
                                                        int index = loanLengthDecisionBox.getSelectedIndex();
                                                        int indexB = typeOfItem.getSelectedIndex();
                                                        LibraryItem i = null;
                                                        if(indexB == 0)
                                                        {
                                                            i = new Book
                                                                    (
                                                                        bookNameTextBox.getText(),
                                                                        duration[index],
                                                                        false,
                                                                        0,
                                                                        new Date(),
                                                                        false
                                                                    );
                                                        }
                                                        else
                                                        {
                                                            JOptionPane.showMessageDialog(null, "Please select the audio file for the audio book");
                                                            String fileName = getFileName(new FileNameExtensionFilter("Sound Files", "mp3", "wav"));
                                                            if(fileName != null)
                                                            {
                                                                i = new AudioBook(
                                                                        bookNameTextBox.getText(),
                                                                        duration[index],
                                                                        false,
                                                                        0,
                                                                        new Date(),
                                                                        false,
                                                                        fileName
                                                                    );
                                                            }
                                                        }

                                                        if(i != null)
                                                        {
                                                            if(checkbox.isSelected())
                                                            {
                                                                JOptionPane.showMessageDialog(null, "Please select thumbnail");
                                                                String fileName = getFileName(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif"));
                                                                if(fileName == null || "".equals(fileName)) 
                                                                {
                                                                    return;
                                                                }
                                                                else
                                                                {
                                                                    i = new LibraryItemWithPictureDecorator(i, fileName);
                                                                }
                                                            }
                                                            bookManager.addItem(i);
                                                            list.addElement(bookNameTextBox.getText());
                                                            dispose();
                                                        }
                                                    }
                                                    else
                                                    {
                                                        JOptionPane.showMessageDialog(null, "Book name already exists in the data base");
                                                    }
                                                }
                                                else
                                                {
                                                    JOptionPane.showMessageDialog(null, "You need to have a name for a book");
                                                }
                                            }
                                        }
                                    );
        loanLengthDecisionBox.addItem("3 days");
        loanLengthDecisionBox.addItem("1 week");
        loanLengthDecisionBox.addItem("2 weeks");
        typeOfItem.addItem("Book");
        typeOfItem.addItem("Audio book");
        container.add(loanLengthDecisionBox);
        container.add(typeOfItem);
        checkbox = new JCheckBox("Add image", true);
        container.add(checkbox);
        container.add(addButton);
        getContentPane().add(container);
        
        setSize(300, 400);
        setLocation(500, 280);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    public String getFileName(FileNameExtensionFilter filter)
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/User/Conors PC"));
        jFileChooser.addChoosableFileFilter(filter);

        JFrame mainFrame = new JFrame();

        int result = jFileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = jFileChooser.getSelectedFile();
            return selectedFile.toString();
        }
        return null;
    }
}
