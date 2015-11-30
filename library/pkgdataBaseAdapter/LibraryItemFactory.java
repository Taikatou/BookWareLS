/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Conors PC
 */
public class LibraryItemFactory
{
    protected static DateFormat dateFormat = new SimpleDateFormat("MMMM/d/yyyy", Locale.ENGLISH);
    
    public LibraryItem getLibraryItem(String[] details)
    {
        if(details == null)
        {
            return null;
        }
        else if(details.length == 7)
        {
            try
            {
                if(details[0].matches("book"))
                {
                    return new Book(
                        details[1], //this is the name
                        Integer.parseInt(details[2]), //this is the loan duration
                        Boolean.parseBoolean(details[3]), //this is weather it is currently on loan (details[2] == "false" || details[2] == "true")
                        Integer.parseInt(details[4]),   //this is the user id it is on loan to 0 if not on loan
                        dateFormat.parse(details[5]),        //date of last loan if it was never on loan it is the date of when it was added
                        Boolean.parseBoolean(details[6]) //this is weather it is currently reserved
                    );
                }
                else
                {
                    return new AudioBook(
                        details[1], //this is the name
                        Integer.parseInt(details[2]), //this is the loan duration
                        Boolean.parseBoolean(details[3]), //this is weather it is currently on loan (details[2] == "false" || details[2] == "true")
                        Integer.parseInt(details[4]),   //this is the user id it is on loan to 0 if not on loan
                        dateFormat.parse(details[5]),        //date of last loan if it was never on loan it is the date of when it was added
                        Boolean.parseBoolean(details[6]), //this is weather it is currently reserved
                        details[0]
                    );
                }
            }
            catch (ParseException ex)
            {
                Logger.getLogger(LibraryItemFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(details.length == 8)
        {
            try
            {
                if(details[0].matches("book"))
                {
                    return new LibraryItemWithPictureDecorator(
                            new Book(
                                        details[1], //this is the name
                                        Integer.parseInt(details[2]), //this is the loan duration
                                        Boolean.parseBoolean(details[3]), //this is weather it is currently on loan (details[2] == "false" || details[2] == "true")
                                        Integer.parseInt(details[4]),   //this is the user id it is on loan to 0 if not on loan
                                        dateFormat.parse(details[5]),        //date of last loan if it was never on loan it is the date of when it was added
                                        Boolean.parseBoolean(details[6]) //this is weather it is currently reserved
                                    ),
                            details[7]
                    );
                }
                else
                {
                    return new LibraryItemWithPictureDecorator( 
                            new AudioBook(
                                            details[1], //this is the name
                                            Integer.parseInt(details[2]), //this is the loan duration
                                            Boolean.parseBoolean(details[3]), //this is weather it is currently on loan (details[2] == "false" || details[2] == "true")
                                            Integer.parseInt(details[4]),   //this is the user id it is on loan to 0 if not on loan
                                            dateFormat.parse(details[5]),        //date of last loan if it was never on loan it is the date of when it was added
                                            Boolean.parseBoolean(details[6]), //this is weather it is currently reserved
                                            details[0]
                                        ),
                            details[7]
                    );
                }
            }
            catch (ParseException ex)
            {
                Logger.getLogger(LibraryItemFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
}
