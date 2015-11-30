package library.pkgdataBaseAdapter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class UserManager implements Manager
{
    private static UserManager Manager = null;
    private static Executor pool = Executors.newFixedThreadPool(5);
    protected static ArrayList<User> libraryMembers;
    
    protected final String fileName = "C:\\Users\\Conors PC\\Documents\\NetBeansProjects\\Library interface\\build\\classes\\library\\pkginterface\\userData.txt";
    
    protected UserManager()
    {
        libraryMembers = new ArrayList<>();
        
        initialiseManagerData();
    }
    
    public static UserManager getManager()
    {
        if(Manager == null)
        {
           Manager = new UserManager();
        }
        return Manager;
    }
    
    public DefaultListModel getListOfUsers(boolean isLibrarian)
    {
        DefaultListModel model = new DefaultListModel();
        for(User u : libraryMembers)
        {
            if(u.getIsLibrarian() == isLibrarian)
            {
                model.addElement(u.getName());
                System.out.println(u.getName());
            }
        }
        return model;
    }
    
    public User checkDetails(String user, String pass)
    {
        for(User u : libraryMembers)
        {
            if(u.checkDetails(user, pass))
            {
                return u;
            }
        }
        return null;
    }
    
    public int loanItem(LibraryItem b, User currentUserLoggedIn)
    {
        if(!b.getCurrentlyOnLoan())
        {
            if(currentUserLoggedIn.addLoan(b) == true)
            {
                return 1;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "You have reached the max number of loans " + currentUserLoggedIn.getLoanListSize());
            }
        }
        else if(currentUserLoggedIn.getId() != b.getOnLoanTo())
        {
            String ObjButtons[] = {"Yes","No"};
            int PromptResult = JOptionPane.showOptionDialog(null,"Book is loaned already, do you want to reserve the book" ,"Researve book",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
            if(PromptResult == JOptionPane.YES_OPTION)
            {
                currentUserLoggedIn.addReservation(b);
                JOptionPane.showMessageDialog(null, "You should expect the book to be return around the " + b.getReturnDate());
                return 2;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You already have this book");
        }
        return 0;
    }
    
    public User addUser(String name, String password)
    {
        for(User u : libraryMembers)
        {
            if(u.checkName(name))
            {
                JOptionPane.showMessageDialog(null, "User name has been taken");
                return null;
            }
        }
        
        if(password.length() < 5)
        {
            JOptionPane.showMessageDialog(null, "Please have a password of equal or greater length than 5");
            return null;
        }
        else
        {
            libraryMembers.add(new User(name, password, libraryMembers.size() - 1, false));
            save();
            return libraryMembers.get(libraryMembers.size() - 1);
        }
    }
    
    public void turnUserIntoLibrarian(String name)
    {
        User u = null;
        for(User useIterator : libraryMembers)
        {
            if(useIterator.getName().matches(name))
            {
                useIterator.setLibrarian(true);
                save();
                break;
            }
        }
    }
    
    @Override
    public void initialiseManagerData()
    {
        File readIn = new File(fileName);
        if(!readIn.exists())
        {
            PrintWriter output = null;
            try
            {
                output = new PrintWriter(readIn);
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                output.close();
            }
        }
        else
        {
            try
            (Scanner in = new Scanner(readIn)) {
                while(in.hasNext())
                {
                    String lineFromFile = in.nextLine();
                    String [] details = lineFromFile.split(",");
                    if(details.length == 3)
                    {
                        libraryMembers.add(new User(details[0],
                                details[1],
                                libraryMembers.size() - 1,
                                details[2].matches("true")));
                    }
                }
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void save()
    {
        Runnable r = new Runnable()
                      {
                         @Override
                         public void run()
                         {
                            PrintWriter writer;
                            try
                            {
                                writer = new PrintWriter(fileName, "UTF-8");
                                libraryMembers.stream().forEach((u) -> {
                                    u.print(writer);
                                });
                                writer.close();
                            }
                            catch (Exception e)
                            {
                                System.err.println(e);
                            }
                        }
                      };
        pool.execute(r);
    }

    @Override
    public int getSize()
    {
        return libraryMembers.size();
    }
}
