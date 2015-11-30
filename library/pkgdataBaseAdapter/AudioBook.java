/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkgdataBaseAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.JButton;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Conors PC
 */
public class AudioBook extends Book
{
    private String songLocation;
    private AudioStream as = null;    
    private boolean hit = false;
    private JButton playButton;
    public AudioBook(String bookName, int loanDuration, boolean currentlyOnLoan, int onLoanTo, Date onLoanSince, boolean reserved, String songLocation)
    {
        super(bookName, loanDuration, currentlyOnLoan, onLoanTo, onLoanSince, reserved);
        this.songLocation = songLocation;
    }
    
    @Override
    public void save(PrintWriter writer)
    {
        writer.print(songLocation + ",");
        writer.print(this.getName() + ",");
        writer.print(this.getLoanDuration() + ",");
        writer.print(this.getCurrentlyOnLoan() + ",");
        writer.print(this.getOnLoanTo() + ",");
        writer.print(dateFormat.format(this.getOnLoanSince()) + ",");
        writer.print(this.getReserved());
    }
    
    @Override
    public JPanel showDisplay(boolean isLibrarian)
    {
        JPanel panel = super.showDisplay(isLibrarian);
        playButton = new JButton("play");
        playButton.addActionListener(new ActionListener()
                                        {
                                            @Override
                                            public void actionPerformed(ActionEvent ae)
                                            {
                                                hit = !hit;
                                                if(hit)
                                                {
                                                    InputStream in = null;
                                                    try
                                                    {
                                                        in = new FileInputStream(songLocation);
                                                    }
                                                    catch (FileNotFoundException ex)
                                                    {
                                                        Logger.getLogger(AudioBook.class.getName()).log(Level.SEVERE, null, ex);
                                                    }

                                                    // Create an AudioStream object from the input stream.     
                                                    try
                                                    {
                                                        as = new AudioStream(in);
                                                    }
                                                    catch (IOException ex)
                                                    {
                                                        Logger.getLogger(AudioBook.class.getName()).log(Level.SEVERE, null, ex);
                                                    }

                                                    // Use the static class member "player" from class AudioPlayer to play
                                                    // clip.
                                                    AudioPlayer.player.start(as);
                                                    playButton.setText("pause");
                                                }
                                                else
                                                {
                                                    AudioPlayer.player.stop(as); 
                                                    playButton.setText("play");
                                                }
                                            }
                                        }
                                    );
        panel.add(playButton);
        return panel;
    }
    @Override
    public void dispose()
    {
        if(hit)
        {
            AudioPlayer.player.stop(as);
            hit = false;
        }
    }
}
