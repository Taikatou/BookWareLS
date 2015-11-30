    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author Conors PC
 */
public abstract class LibraryItemDecorator implements LibraryItem
{
    protected LibraryItem item;
    public LibraryItemDecorator(LibraryItem l)
    {
        this.item = l;
    }
    
    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public double getFine(Date today) {
        return item.getFine(today);
    }

    @Override
    public void save(PrintWriter writer) {
        item.save(writer);
    }

    @Override
    public void loanItem(int userID) {
        item.loanItem(userID);
    }

    @Override
    public void returnItem() {
        item.returnItem();
    }

    @Override
    public boolean getCurrentlyOnLoan() {
        return item.getCurrentlyOnLoan();
    }

    @Override
    public int getOnLoanTo() {
        return item.getOnLoanTo();
    }

    @Override
    public String getReturnDate() {
        return item.getReturnDate();
    }
    
    @Override
    public void dispose()
    {
        item.dispose();
    }
}
