package il.ac.hit.View;

import il.ac.hit.Model.costManagerException;
import il.ac.hit.Model.product;
import il.ac.hit.ViewModel.IViewModel;
import il.ac.hit.ViewModel.ViewModel;
import org.jdatepicker.JDatePicker;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class reportPage {
    private JFrame frame;
    private JPanel panelHead, panelInfo, panelBtn, buttonSection, btnPlaceholder;
    private JLabel head,space;
    private IViewModel vm;
    private JLabel titleLabel;
    private JTable expenseList;
    private JDatePicker datePicker1, datePicker2;
    private JLabel firstDateLbl, secondDateLbl;
    private JButton reportBtn;

    public reportPage()  {
        frame = new JFrame("Manager Report");
        panelHead = new JPanel();
        panelHead.setBackground(Color.white);
        panelInfo = new JPanel();
        panelInfo.setBackground(Color.white);
        panelBtn = new JPanel();
        panelBtn.setBackground(Color.white);
        head = new JLabel("Select specific period:");
        space= new JLabel(" ");
        head.setBackground(Color.white);
        vm=new ViewModel();
        datePicker1 = new JDatePicker(Date.valueOf((LocalDate.now())));
        datePicker2 = new JDatePicker(Date.valueOf((LocalDate.now())));
        firstDateLbl = new JLabel("First Date :");
        secondDateLbl = new JLabel("Second Date :");
        reportBtn = new JButton("Make a report");

        ActionListener addAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                java.util.Date selectedDate1 = (java.util.Date) datePicker1.getModel().getValue();
               java.util.Date selectedDate2 = (java.util.Date) datePicker2.getModel().getValue();
                try {
                     expensePage(selectedDate1, selectedDate2);
                } catch (costManagerException e) {
                    e.printStackTrace();
                }


            }
        };

        reportBtn.addActionListener(addAction);
    }
    public void expensePage(java.util.Date date1, java.util.Date date2) throws costManagerException {
        ArrayList<product> expensesReport = new ArrayList<>();
        expensesReport = vm.getReport(date1,date2);
        panelBtn.removeAll();
        panelInfo.removeAll();

        String[][] table = new String[expensesReport.size()+1][];
        ArrayList<String> table1=new ArrayList<>();
        String[] columnNames = {"ID", "DESCRIPTION", "CATEGORY", "DATE", "PRICE", "CURRENCY"};
        table[0] = columnNames;
      int index = 1;
        int countArray = expensesReport.size();
        btnPlaceholder = new JPanel();
        for (int i = 0; i <countArray; i++) {
            String[] row = new String[6];
            row[0] = expensesReport.get(i).getProductID();
            row[1] = expensesReport.get(i).getNameProduct();
            row[2] = expensesReport.get(i).getCategoryName();
            row[3] = expensesReport.get(i).getDateProduct().toString();
            row[4] = String.valueOf(expensesReport.get(i).getPriceProduct());
            row[5] = expensesReport.get(i).getCurrencyType().toString();
            table[i+1] = row;
        }

        expenseList = new JTable(table, columnNames);
        expenseList.setBounds(0, 0, 800, 800);




        panelInfo.add(expenseList);
        expenseList.setBackground(Color.lightGray);
//        panelInfo.add("East",buttonSection);
        JButton addExpenseBtn = new JButton("Add New Expense");
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for(int i=0; i<expensesReport.size(); i++)
            pieDataset.setValue(expensesReport.get(i).getCategoryName(),expensesReport.get(i).getPriceProduct());
        JFreeChart chart = ChartFactory.createPieChart("",pieDataset,true,true,true);
        PiePlot p= (PiePlot)chart.getPlot();
        frame = new ChartFrame("Pie Chart", chart);
        frame.setLocation(380,150);
        frame.setVisible(true);
        frame.setSize(600,400);
    }



//    public void specificReport(java.util.Date date1, java.util.Date date2) throws CostManagerException {
//        ArrayList<Product> expensesReport = new ArrayList<>();
//        expensesReport = vm.getReport(date1,date2);
//        frame.removeAll();
//        titleLabel=new JLabel();
//        titleLabel.setText("Detailed report");
//        frame.add(titleLabel);
//
//        frame.setSize(600, 350);
//        JLabel balance = new JLabel();
//        frame.add("North", balance);
//        String sign = "Report you requested between date:" + date1 + "and date" + date2;
//        balance.setText(sign);

//    }

    public void start() {

        //setting the panels' background
        //frame.setLayout(new FlowLayout());
        //frame.setLayout(new GridLayout(2,1));
        //setting the frame layout manager
        //frame.setLayout(new FlowLayout());
        //setting layout managers for the two panels
        panelHead.setLayout(new GridLayout(5, 2));
        panelInfo.setLayout(new GridLayout(5, 1, 0, 0));
        panelBtn.setLayout(new GridLayout(1, 1));


        //adding components to panel one
        panelHead.add(head);
        panelHead.add(space);
        panelHead.add(firstDateLbl);
        panelHead.add(datePicker1);
        panelHead.add(secondDateLbl);
        panelHead.add(datePicker2);
        //adding components to panel two

        //adding components to panel Three
        panelHead.add(reportBtn);


        //adding the components to the jframe
        frame.add(panelHead, BorderLayout.NORTH);
        frame.add(panelInfo, BorderLayout.CENTER);
        frame.add(panelBtn, BorderLayout.SOUTH);



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
        frame.setSize(600, 500);

        //turning on the jframe visibility
        frame.setVisible(true);
    }
    }






