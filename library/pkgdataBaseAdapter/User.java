/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Conors PC
 */
public class User
{
    protected int               id;
    protected ArrayList<LibraryItem>   currentLoans;
    protected ArrayList<LibraryItem>   currentReservations;
    protected int               maxLoansNumber;
    protected boolean           isLibrarian;
    protected DateFormat        format = new SimpleDateFormat("MMMM/d/yyyy", Locale.ENGLISH);
    protected String            loansFile = "";
    protected String            reservationsFile = "";
    protected String            userName;
    protected String            userPassword;
    
    public User(String userName, String password, int id, boolean isLibrarian)
    {
        this.userName = userName;
        this.userPassword = password;
        this.id = id;

        this.currentLoans = new ArrayList<>();
        currentReservations = new ArrayList<>();
        this.maxLoansNumber = 5;
        this.isLibrarian = isLibrarian;
        loansFile = "C:\\Users\\Conors PC\\Documents\\NetBeansProjects\\Library interface\\build\\classes\\library\\pkginterface\\" + id + "Loans.txt";
        reservationsFile = "C:\\Users\\Conors PC\\Documents\\NetBeansProjects\\Library interface\\build\\classes\\library\\pkginterface\\" + id + "Reservations.txt";
        
        ReadFile(currentLoans, loansFile);
        ReadFile(currentReservations, reservationsFile);
    }
    
    public boolean checkDetails(String name, String pass)
    {
        return (name.matches(userName) && pass.matches((userPassword)));
    }
    
    public boolean checkName(String name)
    {
        return (userName.matches(name));
    }
    
    public void print(PrintWriter writer)
    {
        writer.print(userName + ",");
        writer.print(userPassword + ",");
        writer.println(isLibrarian);
    }
    
    public void ReadFile(ArrayList<LibraryItem> list, String fileName)
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
            Scanner in = null;
            try
            {
                in = new Scanner(readIn);
                while(in.hasNext())
                {
                    System.out.println(id);
                    String lineFromFile = in.nextLine();

                    System.out.println(lineFromFile);
                    BookManager bookManager = BookManager.getManager();
                    LibraryItem b = bookManager.findItemByName(lineFromFile);
                    if(b != null)
                    {
                        currentLoans.add(    
                                            b
                                        );
                    }
                }
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                in.close();
            }
        }
    }
    
    public User(int id, int amountOfBooks, boolean isLibrarian)
    {
        this.id = id;
        this.userName = "Sample";
        this.currentLoans = new ArrayList<>();
        this.maxLoansNumber = amountOfBooks;
        this.isLibrarian = isLibrarian;
    }
    
    public String getName()
    {
        return userName;
    }
    
    public int getId()
    {
        return id;
    }
    
    public boolean getIsLibrarian()
    {
        return isLibrarian;
    }
    
    public void setLibrarian(boolean b)
    {
        isLibrarian = b;
    }
    
    public boolean addLoan(LibraryItem b)
    {
        boolean status = currentLoans.size() < maxLoansNumber;
        if(status)
        {
            currentLoans.add(b);
            b.loanItem(id);
            save(currentLoans, loansFile);
        }
        return status;
    }
    
    public boolean addReservation(LibraryItem b)
    {
        boolean status = currentReservations.size() < maxLoansNumber;
        if(status)
        {
            currentReservations.add(b);
            save(currentReservations, reservationsFile);
        }
        return status;
    }
   
    public int getLoanListSize()
    {
        return currentLoans.size();
    }
    
    public int getReservationListSize()
    {
        return currentReservations.size();
    }
    
    public String getBookName(int i)
    {
        return currentLoans.get(i).getName();
    }
    
    public String GetReservationBookName(int i)
    {
        return currentReservations.get(i).getName();
    }
    
    public double getFine()
    {
        Date today = new Date();
        double fine = 0.0;
        for (LibraryItem currentLoan : currentLoans)
        {
            fine += currentLoan.getFine(today);
        }
        return fine;
    }
    
    public void save(ArrayList<LibraryItem> list, String fileName)
    {
        PrintWriter writer;
        try
        {
            writer = new PrintWriter(fileName, "UTF-8");
            for(LibraryItem b : list)
            {
                writer.println(b.getName());
            }
            writer.close();
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
    
    public boolean hasBook(Book b)
    {
        for(LibraryItem book : currentLoans)
        {
            if(book.getName().equals(b.getName()))
            {
                return true;
            }
        }
        return false;
    }
    
    public void returnItem(LibraryItem b)
    {
        for(int i = 0; i < currentLoans.size(); i++)
        {
            if(currentLoans.get(i).getName().equals(b.getName()))
            {
                currentLoans.remove(i);
                save(currentLoans, loansFile);
                return;
            }
        }
    }
}
