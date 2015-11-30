/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.pkginterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Conors PC
 */
public class PayFinesInterface extends JFrame implements ItemListener
{
    FlowLayout flow = new FlowLayout();
    JComboBox payMethod = new JComboBox();
    JLabel fineLabel;
    JLabel totFees = new JLabel("");
    JButton payButton = new JButton("Pay Now");
    String pctMsg = new String("per cent will be added to your bill");
    int[] fees = {5,2,0};
    int feePct = 0;
    String output;
    int fee = 0;
    public PayFinesInterface(double fine)
    {
        super("Pay List");
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            
        JLabel fineLabel = new JLabel("Total Fines equals " + fine + " Pay by");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(flow);
        payMethod.addItemListener(this);
        container.add(fineLabel);
        container.add(payMethod);
        
        payMethod.addItem("Credit card");
        payMethod.addItem("Cheque");
        payMethod.addItem("Cash");

        container.add(totFees);
        container.add(payButton);
        setSize(350, 150);
        setLocation(500, 280);
        
        payButton.addActionListener((ActionEvent ae) -> {
            dispose();
        });
        add(container);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent list)
    {
        Object source = list.getSource();
        if(source == payMethod)
        fee = payMethod.getSelectedIndex();
        feePct = fees[fee];
        output = feePct + " " + pctMsg;
        totFees.setText(output);
    }
}