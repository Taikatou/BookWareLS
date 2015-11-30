/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.util.Date;

/**
 *
 * @author Conors PC
 */
public class Journal extends Book
{
    public Journal(String bookName, int loanDuration, boolean currentlyOnLoan, int onLoanTo, Date onLoanSince, boolean reserved) {
        super(bookName, loanDuration, currentlyOnLoan, onLoanTo, onLoanSince, reserved);
    }
}
