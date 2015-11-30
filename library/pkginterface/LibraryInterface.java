package library.pkginterface;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import library.pkgdataBaseAdapter.Book;
import library.pkgdataBaseAdapter.BookManager;
import library.pkgdataBaseAdapter.LibraryItem;
import library.pkgdataBaseAdapter.User;
import library.pkgdataBaseAdapter.UserManager;

public class LibraryInterface extends JFrame
{
    /**
     * @param args the command line arguments
     */
    private static JList[]                              list;
    private static DefaultListModel[]                   listModel;
    private static JTextField                           searchBox;
    
    protected static boolean                            isLibrarian;
    protected static BookManager                        bookManager;
    protected static UserManager                        userManager;
    protected static User                               currentUserLoggedIn;
    
    public LibraryInterface(User loggedIn)
    {
        bookManager = BookManager.getManager();
        userManager = UserManager.getManager();
        this.isLibrarian = loggedIn.getIsLibrarian();
        this.currentUserLoggedIn = loggedIn;
        // TODO code application logic here
        createMenuBar();
        showMethod();
    }
    
    private int getRow(Point point)
    {
        return list[2].locationToIndex(point);
    }
    
    public void showMethod()
    {
        try
        {
            listModel = new DefaultListModel[3];
            listModel[0] = new DefaultListModel();
            listModel[1] = bookManager.getListOfItems("");
            listModel[2] = new DefaultListModel();
            for(int i = 0; i < currentUserLoggedIn.getLoanListSize(); i++)
            {
                listModel[0].addElement(currentUserLoggedIn.getBookName(i));
            }
            for(int j = 0; j < currentUserLoggedIn.getReservationListSize(); j++)
            {
                listModel[2].addElement(currentUserLoggedIn.GetReservationBookName(j));
            }
            list = new JList[3];
            list[0] = new LibraryJList(listModel[0]);
          
            list[1] = new LibraryJList(listModel[1]);
            
            list[2] = new LibraryJList(listModel[2]);

            list[2].addMouseListener( new MouseAdapter()
            {
               public void mousePressed(MouseEvent e)
               {
                   System.out.println(e);
                   if ( SwingUtilities.isRightMouseButton(e) )
                   {
                       System.out.println("Row: " + getRow(e.getPoint()));
                       list[2].setSelectedIndex(getRow(e.getPoint()));
                   }
               }
            });
            
            JScrollPane scrollBar2 = new JScrollPane(list[1]);
            
            JScrollPane scrollBar3 = new JScrollPane(list[2]);
            
            JPanel listOfAllBooks = new JPanel();
            listOfAllBooks.setLayout(new BoxLayout(listOfAllBooks, BoxLayout.Y_AXIS));
            JLabel text = new JLabel("All books in library");
            listOfAllBooks.add(text);
            
            listOfAllBooks.add(AddSearchPanel());
            
            listOfAllBooks.add(scrollBar2);

            JButton showDetailsButton = new JButton("Show Details");
            showDetailsButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int [] fromindex = list[1].getSelectedIndices();
                    if(fromindex.length == 1)
                    {
                        bookManager.showDetails(listModel[1].get(fromindex[0]).toString(), currentUserLoggedIn.getIsLibrarian());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Please select a single item");
                    }
                }
            });
            
            listOfAllBooks.add(showDetailsButton);
            
            JPanel addRemove = new JPanel();
            addRemove.setLayout(new BoxLayout(addRemove, BoxLayout.X_AXIS));
            JButton returnBookButton = new JButton("Return book");
            returnBookButton.addActionListener	((ActionEvent ae) -> {
                int [] fromindex = list[0].getSelectedIndices();
                for(int i = (fromindex.length-1); i >=0; i--)
                {
                    String book = listModel[0].get(fromindex[i]).toString();
                    LibraryItem b = bookManager.findItemByName(book);
                    listModel[0].remove(fromindex[i]);
                    bookManager.ReturnItem(b, currentUserLoggedIn);
                }
            });

            JButton loanButton = new JButton("Loan book");
            loanButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent ae) {
                    addRemove.setEnabled(false);
                    int[] fromindex = list[1].getSelectedIndices();
                    for(int i = (fromindex.length - 1); i >= 0; i--)
                    {
                        String book = listModel[1].get(fromindex[i]).toString();
                        LibraryItem b = bookManager.findItemByName(book);
                        if(b != null)
                        {
                            switch(userManager.loanItem(b, currentUserLoggedIn))
                            {
                                case 1: listModel[0].addElement(book);
                                bookManager.loanItem(b, currentUserLoggedIn.getId());
                                break;
                                case 2: listModel[2].addElement(book);
                                break;
                            }
                        }
                    }
                }
            });
            addRemove.add(returnBookButton);
            addRemove.add(loanButton);
            
            JScrollPane scrollBar = new JScrollPane(list[0]);
            JPanel listOfCurrentLoans = new JPanel();
            listOfCurrentLoans.setLayout(new BoxLayout(listOfCurrentLoans, BoxLayout.Y_AXIS));
            JLabel text2 = new JLabel("Current Loans");
            listOfCurrentLoans.add(text2);
            listOfCurrentLoans.add(scrollBar);
            JLabel reservationLabel = new JLabel("Reservations");
            listOfCurrentLoans.add(reservationLabel);
            listOfCurrentLoans.add(scrollBar3);
            
            JPanel loan_container = new JPanel();
            loan_container.setLayout(new BoxLayout(loan_container, BoxLayout.Y_AXIS));
            
            loan_container.add(listOfCurrentLoans);
            loan_container.add(addRemove);
            
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
            container.add(loan_container);
            container.add(listOfAllBooks);
            
            add(container);
            setSize(600, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            setTitle("Library Interface");
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }
    
    public JPanel AddSearchPanel()
    {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

        searchBox = new JTextField();
        JButton searchButton = new JButton("Search");
        
        searchButton.addActionListener (
                                            new ActionListener()
                                            {
                                                @Override
                                                public void actionPerformed(ActionEvent ae)
                                                {
                                                    search();
                                                }
                                            }
                                        );
        searchPanel.add(searchBox);
        searchPanel.add(searchButton);
        
        return searchPanel;
    }
    
    private void createMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem = new JMenuItem("Exit", icon);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        });
        
        JMenuItem logOutItem = new JMenuItem("Log out", icon);
        logOutItem.setMnemonic(KeyEvent.VK_L);
        logOutItem.setToolTipText("Log out");
        logOutItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                LoginScreen l = new LoginScreen();
                dispose();
            }
        });

        file.add(eMenuItem);
        file.add(logOutItem);
        
        menubar.add(file);
        if(isLibrarian == true)
        {
            AddLibrarianOptions(menubar);
        }
        AddAccountOptions(menubar);
        
        JMenu helpMenu = new JMenu("Help");
        menubar.add(Box.createHorizontalGlue());
        menubar.add(helpMenu);
        setJMenuBar(menubar);
    }
    
    public void AddLibrarianOptions(JMenuBar menubar)
    {
        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        
        JMenuItem AddBook = new JMenuItem("Add Book");
        AddBook.setMnemonic(KeyEvent.VK_A);
        AddBook.setToolTipText("Add Book");

        AddBook.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                AddBookInterface i = new AddBookInterface(listModel[1]);
                if(searchBox.getText().length() > 0)
                {
                    search();
                }
            }
        });
        
        JMenuItem RemoveBook = new JMenuItem("Remove Book");
        RemoveBook.setMnemonic(KeyEvent.VK_R);
        RemoveBook.setToolTipText("Add Book");

        RemoveBook.addActionListener((ActionEvent event) -> {
            int [] fromindex = list[1].getSelectedIndices();
            String books;
            if(fromindex.length > 0)
            {
                if(fromindex.length == 1)
                {
                    books = "book";
                }
                else
                {
                    books = "books";
                }
                String ObjButtons[] = {"Yes","No"};
                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to remove selected " + books,"Remove Book",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    for(int i = (fromindex.length-1); i >=0; i--)
                    {
                        String book = listModel[1].get(fromindex[i]).toString();
                        listModel[1].remove(fromindex[i]);
                        bookManager.removeBookByName(book);
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"You selected no books");
            }
        });
        
        JMenuItem AddLibrarian = new JMenuItem("Add Librarian");
        AddLibrarian.setMnemonic(KeyEvent.VK_L);
        AddLibrarian.setToolTipText("Turn user into librarian");
        
        AddLibrarian.addActionListener((ActionEvent event) -> {
            AddNewLibrarianInterface i = new AddNewLibrarianInterface();
        });


        optionsMenu.add(AddBook);
        optionsMenu.add(RemoveBook);
        optionsMenu.add(AddLibrarian);
        menubar.add(optionsMenu);
    }
    
    public void AddAccountOptions(JMenuBar menubar)
    {
        JMenu optionsMenu = new JMenu("Account");
        optionsMenu.setMnemonic(KeyEvent.VK_A);
        
        JMenuItem payFines = new JMenuItem("Pay Fines");
        payFines.setMnemonic(KeyEvent.VK_P);
        payFines.setToolTipText("Pay Fine");

        payFines.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                double fine = currentUserLoggedIn.getFine();
                PayFinesInterface p = new PayFinesInterface(fine);
            }
        });

        optionsMenu.add(payFines);
        menubar.add(optionsMenu);
    }
    
    public void search()
    {
        listModel[1] = bookManager.getListOfItems(searchBox.getText());
        list[1].setModel(listModel[1]);
    }
}