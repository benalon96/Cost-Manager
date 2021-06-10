package il.ac.hit.Model;

import java.util.ArrayList;
import java.util.List;

public interface IModel {

    public void addPayment(product product) throws costManagerException;
    public void getProductArrayListFromDB() throws costManagerException;
    public void deletePayment(String itemId) throws costManagerException;
    public List<String> getCategories() throws costManagerException;
    public void addCategory(String categoryName) throws costManagerException;
    public void getPersonFromDB() throws costManagerException;
    public ArrayList<product> getReport(java.util.Date date1, java.util.Date date2)throws costManagerException;






}
