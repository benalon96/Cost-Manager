package il.ac.hit.View;
import il.ac.hit.ViewModel.IViewModel;
import il.ac.hit.ViewModel.ViewModel;
import javax.swing.*;
import java.awt.*;
import static il.ac.hit.Model.DBConnection.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class addingCategory {

    private JFrame frame;
    private JPanel panelHead, panelInfo, panelBtn;
    private JLabel head;
    private JButton submitInfo;
    private IViewModel vm;
    private JTextField newCategory;


    public addingCategory() {
        frame = new JFrame("Cost Manager-Adding a Category");
        panelHead = new JPanel();
        panelHead.setBackground(Color.white);
        panelInfo = new JPanel();
        panelInfo.setBackground(Color.white);
        panelBtn = new JPanel();
        panelBtn.setBackground(Color.white);
        head = new JLabel("Write new category:");
        head.setBackground(Color.white);
        submitInfo = new JButton("Finish");
        newCategory = new JTextField();
        vm=new ViewModel();



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
        panelInfo.setLayout(new GridLayout(1,1));
        panelBtn.setLayout(new GridLayout(1,1));


        //adding components to panel one
        panelHead.add(head);

        //adding components to panel two
        panelInfo.add(newCategory);


        //adding components to panel Three
        panelBtn.add(submitInfo);

        //adding the components to the jframe
        frame.add(panelHead,BorderLayout.NORTH);
        frame.add(panelInfo,BorderLayout.CENTER);
        frame.add(panelBtn, BorderLayout.SOUTH);


        //adding an event listener to the button (btPlus)
        submitInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String new_category=newCategory.getText().toString();
                vm.addCategory(new_category);
                frame.setVisible(false);
                frame.dispose();
            }
        });

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
        frame.setSize(350,110);

        //turning on the jframe visibility
        frame.setVisible(true);
    }


}
