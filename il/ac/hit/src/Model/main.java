package il.ac.hit.Model;


import il.ac.hit.View.MainPage;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class main {
  static public   ArrayList<category> categoryArrayList=new ArrayList<>();
    static ArrayList<product> productArrayList =new ArrayList<>();
//    static public Person person;
   static List<String> catergory=new ArrayList<>();
    public static void main(String[] args) throws costManagerException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {


//0        person.adding_Category(new Category("food", 0));
//        person.adding_Category(new Category("Education", 0));
//        person.adding_Category(new Category("Clothing", 0));
//        person.adding_Category(new Category("Appliances", 0));
//        person.adding_Category(new Category("Car expenses", 0));
//        person.adding_Category(new Category("Bills", 0));
//

        DBConnection dbConnection = new DBConnection();

//        Product product= new Product("apple",10,new Date(),"food",CurrencyType.ILS);
//           dbConnection.addPayment(product);
//        dbConnection.getProductFromDB();
        for (int i = 0; i < DBConnection.productArrayList.size(); i++) {
            System.out.println(DBConnection.productArrayList.get(i).toString() + "");
        }


//        for (int i = 0; i < person.categoryArrayList.size(); i++) {
//            if(i>0){
//                catergory.add(person.categoryArrayList.get(i).getName_Category());
//                dbConnection.addCategory(person.categoryArrayList.get(i).getName_Category());
//            }
//
//        }
//            for (int i = 0; i < person.categoryArrayList.size(); i++) {
//
//                for (int j = 0; j < productArrayList.size(); j++) {
//
//                    if (person.categoryArrayList.get(i).getName_Category().equals(productArrayList.get(j).getCategory_name())) {
//                        person.categoryArrayList.get(i).adding_Product(productArrayList.get(j));
//                    }
//
//                }
//            }
           catergory=dbConnection.getCategories();
           for (int i=0;i<catergory.size();i++){
               System.out.println(catergory.get(i).toString() + "");
           }
//               categoryArrayList.get(i).print_Product_List();

//               System.out.println( person.categoryArrayList.get(i).getCategory_Expenses()+"");
//           }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MainPage ob = new MainPage();
//                ob.start();

//               DateTextField o5 = new DateTextField();
//                o5.start();

//                AddingPage o2 = new AddingPage();
//                o2.start();

////
//                MounthlyReportPage o3 = new MounthlyReportPage();
//                o3.start();

            }
        };
        SwingUtilities.invokeLater(runnable);



        }
    }
