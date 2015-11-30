/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkginterface;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Conors PC
 */
public class LibraryJList extends JList
{
    public LibraryJList(DefaultListModel listModel)
    {
        super(listModel);
        setVisibleRowCount(25);
        setFixedCellHeight(20);
        setFixedCellWidth(250);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
}
