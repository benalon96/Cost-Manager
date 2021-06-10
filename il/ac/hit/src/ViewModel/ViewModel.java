package il.ac.hit.ViewModel;


import il.ac.hit.Model.costManagerException;
import il.ac.hit.Model.DBConnection;
import il.ac.hit.Model.IModel;
import il.ac.hit.Model.product;
import il.ac.hit.View.IView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static il.ac.hit.Model.DBConnection.*;
import static javax.swing.UIManager.get;


public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    public static float sumMounthly=0;
    private ExecutorService pool;

    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);

    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void getPersonFromDB() throws costManagerException {
            model.getPersonFromDB();
    }

    @Override
    public void getMounthlyExp() throws costManagerException {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Date date=new Date();
                    String month;
                    int monthNow=getDayOfMonth(date);
                    model.getPersonFromDB();
                    for(int i=0;i<person.getCategoryArrayList().size();i++){
                        for(int j=0;j<person.getCategoryArrayList().get(i).getProductArrayList().size();j++){
//                            compare two date about month
                            int monthProduct=getDayOfMonth( person.getCategoryArrayList().get(i).getProductArrayList().get(j).getDateProduct());
                       if(monthNow==monthProduct) {
                           sumMounthly+=person.categoryArrayList.get(i).getProductArrayList().get(j).getPriceProduct();
                       }
                        }

                    }
                } catch ( costManagerException e) {
                    try {
                        throw new costManagerException("DB access failed", e);
                    } catch (costManagerException ex) {
                        ex.printStackTrace();
                    }
                }
               view.showMessage("cost item was added successfully");
                //CostItem[] items = model.getCostItems();
                //view.showItems(items);
            }
        });

    }
    public static int getDayOfMonth(Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void addPayment(product product) throws costManagerException {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model=new DBConnection();
                    model.addPayment(product);
                    productArrayList.add(product);
                    model.getPersonFromDB();
                } catch (costManagerException e) {
                    e.printStackTrace();

                }
                view.showMessage("cost item was added successfully");
                //CostItem[] items = model.getCostItems();
                //view.showItems(items);
            }
        });
    }


    @Override
    public void deletePayment(String itemId) throws costManagerException {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.deletePayment(itemId);
                    model.getPersonFromDB();
                } catch (costManagerException e) {
                    e.printStackTrace();
                }
               view.showMessage("cost item was deleted successfully");
                //CostItem[] items = model.getCostItems();
                //view.showItems(items);
            }
        });

    }

    @Override
    public List<String> getCategories() throws costManagerException {
        return null;
    }

    @Override
    public void addCategory(String categoryName) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model=new DBConnection();
                    model.addCategory(categoryName);
                    categoryNames.add(categoryName);
                    model.getPersonFromDB();
                } catch (costManagerException e) {
                    e.printStackTrace();
                }
                view.showMessage("Category was added successfully");
                //CostItem[] items = model.getCostItems();
                //view.showItems(items);
            }
        });
    }

    @Override
    public ArrayList<product> getReport(java.util.Date date1, java.util.Date date2) throws costManagerException {
       ArrayList<product>exp=new ArrayList<>();
       model=new DBConnection();
       exp=model.getReport(date1,date2);
        return exp;
    }



}
