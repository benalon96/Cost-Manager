package il.ac.hit.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class mounthlyReportPage {

    private JFrame frame;
    private JPanel panelHead, panelInfo, panelBtn;
    private JLabel head;
    private JButton submitInfo;
    private JComboBox months, years;


    public mounthlyReportPage() {
        frame = new JFrame("Cost Manager-Monthly Report");
        panelHead = new JPanel();
        panelHead.setBackground(Color.white);
        panelInfo = new JPanel();
        panelInfo.setBackground(Color.white);
        panelBtn = new JPanel();
        panelBtn.setBackground(Color.white);
        head = new JLabel("Select a month and year to receive the monthly report");
        head.setBackground(Color.white);
        submitInfo = new JButton("Finish");

        String[] ListMonths=new String[13];
        ListMonths[0]="Select a month";
        for (int i=1;i<13;i++){
            ListMonths[i]=String.valueOf(i);
        }
        String[] ListYears=new String[13];
        ListYears[0]="Select a year";
        for (int i=1;i<13;i++){
            ListYears[i]=String.valueOf(i+2009);
        }

        months = new JComboBox(ListMonths);
        months.setBackground(Color.white);
        months.setForeground(Color.black);
        years = new JComboBox(ListYears);
        years.setBackground(Color.white);
        years.setForeground(Color.black);



    }

    public void start() {
        //setting the panels' background
        panelHead.setBackground(Color.white);
        panelHead.setBackground(Color.white);
        //frame.setLayout(new FlowLayout());
        //frame.setLayout(new GridLayout(2,1));

        //setting the frame layout manager
        //frame.setLayout(new FlowLayout());

        //setting layout managers for the two panels
        panelHead.setLayout(new GridLayout(1,1));
        panelInfo.setLayout(new GridLayout(1,2));
        panelBtn.setLayout(new GridLayout(1,1));


        //adding components to panel one
        panelHead.add(head);

        //adding components to panel two
        panelInfo.add(months);

        panelInfo.add(years);
        //adding components to panel Three
        panelBtn.add(submitInfo);

        //adding the components to the jframe
        frame.add(panelHead,BorderLayout.NORTH);
        frame.add(panelInfo,BorderLayout.CENTER);
        frame.add(panelBtn, BorderLayout.SOUTH);


//        //adding an event listener to the button (btPlus)
//        submitInfo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                double num1 = Double.parseDouble(tf1.getText());
//                double num2 = Double.parseDouble(tf2.getText());
//                double result = num1 + num2;
//                tf3.setText(String.valueOf(result));
//            }
//        });

        //handling the jframe closing
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {

                frame.setVisible(false); //you can't see me!
                frame.dispose(); //Destroy the JFrame object
            }
        });
        //setting the jframe size
        frame.setSize(350,150);

        //turning on the jframe visibility
        frame.setVisible(true);
    }


}
