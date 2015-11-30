/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import library.pkginterface.ShowDetails;

/**
 *
 * @author Conors PC
 */
public class Book implements LibraryItem
{
    protected static    DateFormat dateFormat = new SimpleDateFormat("MMMM/d/yyyy", Locale.ENGLISH);
    private String      bookName;
    private int         loanDuration;
    private boolean     currentlyOnLoan;
    private int         onLoanTo;
    private Date        onLoanSince;
    private boolean     reserved;
    
    public Book(String bookName, int loanDuration, boolean currentlyOnLoan, int onLoanTo, Date onLoanSince, boolean reserved)
    {
        this.bookName = bookName;
        this.loanDuration = loanDuration;
        this.currentlyOnLoan = currentlyOnLoan;
        this.reserved = reserved;
        if(this.currentlyOnLoan == true)
        {
            this.onLoanTo = onLoanTo;
            this.onLoanSince = onLoanSince;
        }
        else
        {
            this.onLoanTo = 0;
        }
        this.onLoanSince = onLoanSince;
    }
    
    public boolean getReserved()
    {
        return reserved;
    }
    
    public void toggleReserved()
    {
        reserved = !reserved;
    }
    
    @Override
    public String getName()
    {
        return bookName;
    }
    
    public int getLoanDuration()
    {
        return loanDuration;
    }
    
    @Override
    public boolean getCurrentlyOnLoan()
    {
        return currentlyOnLoan;
    }
    
    @Override
    public int getOnLoanTo()
    {
        return onLoanTo;
    }
    
    public Date getOnLoanSince()
    {
        return onLoanSince;
    }
    
    @Override
    public String getReturnDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM/d/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(onLoanSince); // Now use today date.
        c.add(Calendar.DATE, onLoanTo); // Adding 5 days
        String output = sdf.format(c.getTime());
        System.out.println(output);
        return output;
    }
    
    @Override
    public void loanItem(int userID)
    {
        currentlyOnLoan = true;
        onLoanTo = userID;
    }
    
    @Override
    public void returnItem()
    {
        currentlyOnLoan = false;
    }
    
    @Override
    public double getFine(Date today)
    {
        int diffInDays = (int)( (today.getTime() - onLoanSince.getTime()) 
                 / (1000 * 60 * 60 * 24) );
        double fine;
        if(diffInDays > 0)
        {
            fine = diffInDays * 0.5;
        }
        else
        {
            fine = 0.0;
        }
        return fine;
    }

    @Override
    public void save(PrintWriter writer)
    {
        writer.print("book,");
        writer.print(this.getName() + ",");
        writer.print(this.getLoanDuration() + ",");
        writer.print(this.getCurrentlyOnLoan() + ",");
        writer.print(this.getOnLoanTo() + ",");
        writer.print(dateFormat.format(this.getOnLoanSince()) + ",");
        writer.print(this.getReserved());
    }


    @Override
    public JPanel showDisplay(boolean isLibrarian) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Item name: " + bookName));
        panel.add(new JLabel("Loan duration : " + loanDuration));
        if(isLibrarian)
        {
            if(currentlyOnLoan)
            {
                panel.add(new JLabel("Book is currently on loan"));
                panel.add(new JLabel("It is loaned to user " + onLoanTo));
                panel.add(new JLabel("Since " + dateFormat.format(onLoanSince)));
                if(reserved)
                {
                    panel.add(new JLabel("The book is currently reserved"));
                }
                else
                {
                    panel.add(new JLabel("The book is not currently reserved"));
                }
            }
            else
            {
                panel.add(new JLabel("Book is not currently on loan"));
            }
        }
        return panel;
    }

    @Override
    public void dispose()
    {
        
    }
        

}
