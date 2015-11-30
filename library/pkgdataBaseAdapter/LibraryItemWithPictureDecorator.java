/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Conors PC
 */
public class LibraryItemWithPictureDecorator extends LibraryItemDecorator
{
    protected String imageFile;
    public LibraryItemWithPictureDecorator(LibraryItem l, String imageFile)
    {
        super(l);
        this.imageFile = imageFile;
    }

    @Override
    public JPanel showDisplay(boolean isLibrarian) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        File file = new File(imageFile);
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(file);
        }
        catch (IOException ex) {
            Logger.getLogger(LibraryItemWithPictureDecorator.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image  dimg = image.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        panel.add(label);
        panel.add(item.showDisplay(isLibrarian));
        return panel;
    }
    
    @Override
    public void save(PrintWriter writer)
    {
        super.save(writer);
        writer.write("," + imageFile);
    }

}
