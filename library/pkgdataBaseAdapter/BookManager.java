/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import library.pkginterface.ShowDetails;

/**
 *
 * @author Conors PC
 */
public class BookManager implements Manager
{
    private static BookManager bookManager = null;
    private static Executor pool = Executors.newFixedThreadPool(5);
    
    protected static ArrayList<LibraryItem> allItems;
    protected File database;
    protected File tempFile;
    protected static final String fileName = "C:\\Users\\Conors PC\\Documents\\NetBeansProjects\\Library interface\\build\\classes\\library\\pkginterface\\bookData.txt";
    
    protected BookManager()
    {
        allItems = new ArrayList<>();
        try
        {
            initialiseManagerData();
        }
        catch (Exception ex)
        {
            Logger.getLogger(BookManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showDetails(String itemName, boolean isLibrarian)
    {
        LibraryItem l =  findItemByName(itemName);
        ShowDetails s = new ShowDetails(l, isLibrarian);
    }
    
    public LibraryItem ReturnItem(LibraryItem bookToReturn, User u)
    {
        for(LibraryItem b : allItems)
        {
            if(b.getName().equals(bookToReturn.getName()))
            {
                b.returnItem();
                u.returnItem(bookToReturn);
                save();
                return b;
            }
        }
        return null;
    }
    
    public LibraryItem loanItem(LibraryItem bookToReturn, int id)
    {
        for(LibraryItem b : allItems)
        {
            if(b.getName().equals(bookToReturn.getName()))
            {
                b.loanItem(id);
                save();
                return b;
            }
        }
        return null;
    }
    //SingleTon design pattern
    public static BookManager getManager()
    {
        if(bookManager == null)
        {
           bookManager = new BookManager();
        }
        return bookManager;
   }
    
    public void addItem(LibraryItem b)
    {
        allItems.add(b);
        save();
    }
    
    public String getBookName(int i)
    {
        return allItems.get(i).getName();
    }
    
    @Override
    public int getSize()
    {
        return allItems.size();
    }
    
    public LibraryItem findItemByName(String name)
    {
        for(LibraryItem b : allItems)
        {
            if(b.getName().matches(name))
            {
                return b;
            }
        }
        return null;
    }
    
    public boolean removeBookByName(String name)
    {
        for(int i = 0; i < allItems.size(); i++)
        {
            if(allItems.get(i).getName().matches(name))
            {
                allItems.remove(i);
                save();
                return true;
            }
        }
        return true;
    }
    
    @Override
    public void initialiseManagerData()
    {
        Runnable r = new Runnable()
                      {
                         @Override
                         public void run()
                         {
                            readDataBase();
                         }
                      };
        pool.execute(r);
    }
    
    public void readDataBase()
    {
        Scanner in;
        LibraryItemFactory itemFactory = new LibraryItemFactory();
        if(fileName != null)
        {
            database = new File(fileName); 
            tempFile = new File(fileName);
            PrintWriter output;
            String lineFromFile = "";
            if(!database.exists())
            {
                try {
                    output = new PrintWriter(database);
                    output.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BookManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try
                {
                    in = new Scanner(database);


                    while(in.hasNext())
                    {
                        lineFromFile = in.nextLine();
                        String [] details = lineFromFile.split(",");
                        LibraryItem i = itemFactory.getLibraryItem(details);
                        allItems.add(
                                        i
                                    );
                    }
                    in.close();
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(BookManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public DefaultListModel getListOfItems(String filter)
    {
        DefaultListModel model = new DefaultListModel();
        for(int i = 0; i < allItems.size(); i++)
        {
            if(allItems.get(i).getName().contains(filter))
            {
                model.addElement(allItems.get(i).getName());
            }
        }
        return model;
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
                                for(LibraryItem b : allItems)
                                {
                                    b.save(writer);
                                    writer.println();
                                }
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
}
