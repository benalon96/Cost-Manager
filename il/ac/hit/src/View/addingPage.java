package il.ac.hit.View;

import il.ac.hit.Model.costManagerException;
import il.ac.hit.Model.currencyType;
import il.ac.hit.Model.product;
import il.ac.hit.ViewModel.IViewModel;
import il.ac.hit.ViewModel.ViewModel;
import org.jdatepicker.JDatePicker;
import static il.ac.hit.Model.DBConnection.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class addingPage implements IView {
    private IViewModel vm;
    private ApplicationUIAdd ui;
    private ArrayList<String> catagoryList=new ArrayList<>();

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void showMessage(String text) {

    }

    @Override
    public void refreshMethod() {

    }

    public addingPage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                addingPage.this.ui = new ApplicationUIAdd();
                try {
                    addingPage.this.ui.start();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (costManagerException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class ApplicationUIAdd
    {
        private JFrame frame;
        private JLabel head,productName,price,currency,category,date;
        private JPanel panelHead, panelSubmit,centerTop,centerBottomUpper,centerBottomLower;
        private JButton save, btAddCatagory;
        private JTextField enterPrice, enterName;
        private JComboBox months, years, days, types, enterCategory;
        private JSplitPane panelCenter,bottom;
        private ArrayList<String> listCoinType = new ArrayList();
        private ArrayList<String> listOfDays = new ArrayList();
        private ArrayList<String> listOfYears = new ArrayList();
        private ArrayList<String> listOfMouths = new ArrayList();
        private JDatePicker datePicker1;
//        UtilDateModel model = new UtilDateModel();
//        JDatePanelImpl datePanel = new JDatePanelImpl();
//        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);


    public ApplicationUIAdd() {
            frame = new JFrame("Cost Manager-Adding Product");
            frame.setBackground(Color.lightGray);
            panelHead = new JPanel();

            panelSubmit = new JPanel();
            panelSubmit.setBackground(Color.lightGray);
            //head panel
            head = new JLabel("Fill in all the following details to add the product:");
            head.setBackground(Color.white);

            //center panel
            //center Top
            centerTop = new JPanel();
            productName = new JLabel("Product Name:", SwingConstants.CENTER);
            productName.setBackground(Color.lightGray);
            productName.setForeground(Color.BLACK);
            enterName = new JTextField(10);
            price = new JLabel("Price:", SwingConstants.CENTER);
            price.setBackground(Color.lightGray);
            price.setForeground(Color.BLACK);
            enterPrice = new JTextField(10);
            currency = new JLabel("Currency Type:", SwingConstants.CENTER);
            currency.setBackground(Color.lightGray);
            currency.setForeground(Color.BLACK);


            //bottom Upper - Date
            centerBottomUpper= new JPanel();
           setDateInComboBox();
            listCoinType.add("ILS");
             listCoinType.add("USD");
            listCoinType.add("EURO");
            types = new JComboBox(this.listCoinType.toArray());
             types.setBackground(Color.white);
             datePicker1=new JDatePicker(java.sql.Date.valueOf((LocalDate.now())));

            date= new JLabel("Date: ",SwingConstants.CENTER);
            //bottom Lower - Category
            centerBottomLower= new JPanel();

            category = new JLabel("Select a Category:", SwingConstants.CENTER);
            category.setBackground(Color.lightGray);
            enterCategory = new JComboBox( catagoryList.toArray());
            enterCategory.setBackground(Color.white);
            btAddCatagory = new JButton("Add A Catagory");

            bottom = new JSplitPane(SwingConstants.HORIZONTAL,centerBottomUpper,centerBottomLower);
            panelCenter = new JSplitPane(SwingConstants.HORIZONTAL,centerTop,bottom);

            //bottom panel

            save = new JButton("Save and Finish");
            enterPrice = new JTextField(10);
            enterName = new JTextField(10);



        }

        public void start () throws ClassNotFoundException, InstantiationException, costManagerException, IllegalAccessException {
            //setting the panels' background
            vm = new ViewModel();
            panelHead.setBackground(Color.WHITE);
            panelCenter.setBackground(Color.WHITE);
            panelSubmit.setBackground(Color.lightGray);
            //setting the frame layout manager
            frame.setLayout(new BorderLayout());
            //rame.setLayout(new FlowLayout());
            //setting layout managers for the two panels
            panelHead.setLayout(new GridLayout(1, 1));
            centerTop.setLayout(new GridLayout(3,2));
            centerBottomUpper.setLayout((new GridLayout(1,4)));
            centerBottomLower.setLayout((new GridLayout(1,3)));
            panelSubmit.setLayout(new GridLayout(1, 1));

            //adding components to panel one
            panelHead.add(head);
            //adding components to panel two
            centerTop.add(productName);
            centerTop.add(enterName);
            centerTop.add(price);
            centerTop.add(enterPrice);
            centerTop.add(currency);
            centerTop.add(types);
            centerBottomUpper.add(date);
            centerBottomUpper.add(datePicker1);
            centerBottomLower.add(category);
            centerBottomLower.add(enterCategory);
            centerBottomLower.add(btAddCatagory);
            //adding components to panel Submit
            panelSubmit.add(save);


            //adding the components to the jframe
            frame.add(panelHead, BorderLayout.NORTH);
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelSubmit, BorderLayout.SOUTH);
            save.addActionListener(e -> {
                float priceProduct = Float.valueOf(enterPrice.getText());
                currencyType currencyType = il.ac.hit.Model.currencyType.valueOf(types.getSelectedItem().toString());
                String nameProduct = ApplicationUIAdd.this.enterName.getText();
                Date selectedDate = (Date) datePicker1.getModel().getValue();
                String category = Objects.requireNonNull(enterCategory.getSelectedItem()).toString();
                product product = new product(nameProduct, priceProduct, selectedDate, category, currencyType);

                try {
                    vm.addPayment(product);
                } catch (costManagerException ex) {
                    ex.printStackTrace();
                }
               frame.setVisible(false);
                frame.dispose();
            });

            btAddCatagory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addingCategory o2 = new addingCategory();
                    o2.start();
                }
            });
//                //adding an event listener to the button (btPlus)
//                title.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//
//                    }
//                });

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
            frame.setSize(500, 225);

            //turning on the jframe visibility
            frame.setVisible(true);
        }

        private void setDateInComboBox() {
            catagoryList=(ArrayList)categoryNames;
//            addingPage.this.catagoryList.add("Appliances");
//            addingPage.this.catagoryList.add("Bills");
//            addingPage.this.catagoryList.add("Car expenses");
//            addingPage.this.catagoryList.add("Clothing");
//            addingPage.this.catagoryList.add("Education");
//            addingPage.this.catagoryList.add("Food");
//            listOfMouths.add(0, "Select a Mouth");

            int i;
            for(i = 1; i < 13; ++i) {
                listOfMouths.add(String.valueOf(i));
            }

            listOfYears.add(0, "Select a Year");

            for(i = 1; i < 13; ++i) {
                listOfYears.add(String.valueOf(i + 2009));
            }

            listOfDays.add(0, "Select a Day");

            for(i = 1; i <= 31; ++i) {
                listOfDays.add(String.valueOf(i));
            }

        }
    }

    }



