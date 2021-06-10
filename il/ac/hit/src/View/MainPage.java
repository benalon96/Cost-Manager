package il.ac.hit.View;
import il.ac.hit.Model.category;
import il.ac.hit.Model.costManagerException;;
import il.ac.hit.Model.DBConnection;
import il.ac.hit.ViewModel.IViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import static il.ac.hit.Model.DBConnection.person;
import static il.ac.hit.ViewModel.ViewModel.sumMounthly;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.ImageIcon;

import java.awt.Color;
import javax.swing.JFrame;


public class MainPage implements IView {

    private IViewModel vm;
    private ApplicationUI ui;
    public  Container container;

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void showMessage(String text) {

    }

    @Override
    public void refreshMethod() {
        container.invalidate();
        container.validate();
    }
    class ImagePanel extends JComponent {
        private Image image;
        public ImagePanel(Image image) {
            this.image = image;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    // elsewhere

    public MainPage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPage.this.ui = new ApplicationUI();
                try {
                    MainPage.this.ui.start();
                } catch (costManagerException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public class ApplicationUI {
        private  JFrame frame;
        private JPanel panelHead, panelInfo,panelSelect,buttonSpace;
        private JLabel welcomeTitle,totalExpenses, totalExpensesMonth,currency;
        private JTextField totalExpnsesText,totalExpnsesmMonthText;
        private JButton btnMonthRep, btnYearRep, btnAddNew;
        private JComboBox types;
        private ChartFrame chartFrame;
        private JFreeChart jFreeChart;
        private JLabel space1,space2,space3;
        private JLabel background;






        public ApplicationUI() {

            frame = new JFrame("Cost Manager");

            panelHead = new JPanel();
            panelInfo = new JPanel();
            panelSelect = new JPanel();
            buttonSpace = new JPanel();
            welcomeTitle = new JLabel("Welcome back to your cost manager what you want to do?", welcomeTitle.CENTER);
            welcomeTitle.setBorder(new EmptyBorder(20, 5, 10, 5));
            welcomeTitle.setForeground(Color.BLACK);
            welcomeTitle.setFont(new Font("Serif", Font.TYPE1_FONT, 25));
            totalExpenses = new JLabel("The Total Expenses Are: ");
            totalExpnsesText = new JTextField("560.0", 8);
            totalExpensesMonth = new JLabel("Total expenses this month: ");
            totalExpnsesmMonthText = new JTextField("0.0", 15);
            currency = new JLabel("Currency Type: ");
            String[] ListCoinType = {"\n" +
                    "United States Dollar - USD",
                    "Euro - EUR",
                    "Israeli New Shekel - ILS"};
            types = new JComboBox(ListCoinType);

            btnAddNew = new JButton("Add new expense");
            Border border = btnAddNew.getBorder();
            Border margin = new EmptyBorder(0,0,0,0);
            btnAddNew.setBorder(new CompoundBorder(border, margin));
            btnMonthRep = new JButton("Month Report");
            space1=new JLabel(" ");



        }
        public void start() throws costManagerException, SQLException {
            //setting the panels' background
            setBarChart();
            container=frame.getContentPane();
            vm.getPersonFromDB();
            vm.getMounthlyExp();
            totalExpnsesText.setText(String.valueOf(person.getTotalExpenses()));
            totalExpnsesmMonthText.setText(String.valueOf(sumMounthly));
            panelHead.add(welcomeTitle);
            panelHead.setBackground(Color.white);
            panelInfo.add(totalExpenses);
            panelInfo.add(totalExpnsesText);
            panelInfo.add(totalExpensesMonth);
            panelInfo.add(totalExpnsesmMonthText);
            panelInfo.add(currency);
            panelInfo.add(types);

            panelInfo.setBackground(Color.white);
            panelInfo.setLayout(new GridLayout(4,2));
            buttonSpace.setLayout(new GridLayout(3,1));
            buttonSpace.setBorder(new EmptyBorder(100, 5, 10, 5));
            frame.add(panelHead,BorderLayout.NORTH);
            frame.add(panelInfo, BorderLayout.SOUTH);
//            frame.getContentPane().add(new MyComponent(),BorderLayout.CENTER);
            buttonSpace.add(btnMonthRep);
            buttonSpace.add(space1);
            buttonSpace.add(btnAddNew);
            buttonSpace.setBackground(Color.white);
            panelSelect.setBackground(Color.white);
            panelSelect.add(buttonSpace,BorderLayout.CENTER);
            frame.add(panelSelect,BorderLayout.EAST);


            btnAddNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IView view = (IView) new addingPage();
                    vm.setView(view);
                }
            });

            btnMonthRep.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reportPage o3 = new reportPage();
                    o3.start();
                }
            });
            types.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String types1=(String)types.getSelectedItem().toString();
                    switch (types1) {

                        case "United States Dollar - USD":
                            totalExpnsesText.setText(String.valueOf(person.getTotalExpenses()*3.5));
                            break;
                        case "Euro - EUR":
                            totalExpnsesText.setText(String.valueOf(person.getTotalExpenses()*4));
                            break;
                        case "Israeli New Shekel - ILS":
                            totalExpnsesText.setText(String.valueOf(person.getTotalExpenses()));
                            break;
                    }
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
                    //super.windowClosing(e);
                    frame.setVisible(false);
                    frame.dispose();
                    System.exit(0);

                }
            });

            //setting the jframe size
            frame.setSize(900, 600);

            //turning on the jframe visibility
            frame.setVisible(true);

        }


        private void setBarChart() throws SQLException, costManagerException {
          vm.getPersonFromDB();
            DefaultCategoryDataset dcd = new DefaultCategoryDataset();

            for(int i = 0; i < DBConnection.person.categoryArrayList.size(); ++i) {
                dcd.setValue(((category)DBConnection.person.categoryArrayList.get(i)).getCategoryExpenses(), ((category)DBConnection.person.categoryArrayList.get(i)).getNameCategory(), ((category)DBConnection.person.categoryArrayList.get(i)).getNameCategory());
            }

            jFreeChart = ChartFactory.createBarChart3D("your Report:", "Expenses", "Category", dcd, PlotOrientation.VERTICAL, true, true, false);
            CategoryPlot plot = jFreeChart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.black);
           chartFrame = new ChartFrame("Your Report:", jFreeChart, true);
            ChartPanel chartPanel = new ChartPanel(jFreeChart);
           frame.add(chartPanel, "Center");
        }
      }

}





