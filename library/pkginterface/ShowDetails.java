/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkginterface;

import javax.swing.JFrame;
import library.pkgdataBaseAdapter.LibraryItem;

/**
 *
 * @author Conors PC
 */
public class ShowDetails extends JFrame
{
    LibraryItem libraryItem;
    public ShowDetails(LibraryItem l, boolean isLibrarian)
    {
        this.libraryItem = l;
        add(l.showDisplay(isLibrarian));
        setSize(350, 600);
        setLocation(500, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void dispose()
    {
        libraryItem.dispose();
        super.dispose();
    }
}
