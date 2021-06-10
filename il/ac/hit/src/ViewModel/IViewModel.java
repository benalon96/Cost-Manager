package il.ac.hit.ViewModel;

//import Model.CostItem;
//import Model.CostManagerException;

import il.ac.hit.Model.costManagerException;
import il.ac.hit.Model.IModel;
import il.ac.hit.Model.product;
import il.ac.hit.View.IView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IViewModel {
    public void setView(IView view);
    public void setModel(IModel model);
    public void getPersonFromDB() throws SQLException, costManagerException;
    public void getMounthlyExp() throws SQLException, costManagerException;
    public void addPayment(product product) throws costManagerException;
    public void deletePayment(String itemId) throws costManagerException;
    public List<String> getCategories() throws costManagerException;
    public void addCategory(String categoryName);
    ArrayList<product> getReport(java.util.Date date1, java.util.Date date2) throws costManagerException;
}
