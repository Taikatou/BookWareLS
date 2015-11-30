/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.io.PrintWriter;
import java.util.Date;
import javax.swing.JPanel;

/**
 *
 * @author Conors PC
 */
public interface LibraryItem
{
    public String getName();
    
    public double getFine(Date today);
    
    public void save(PrintWriter writer);
    
    public void loanItem(int userID);
    
    public void returnItem();
    
    public boolean getCurrentlyOnLoan();
    
    public int getOnLoanTo();

    public String getReturnDate();
    
    public JPanel showDisplay(boolean isLibrarian);
    
    public void dispose();
}
