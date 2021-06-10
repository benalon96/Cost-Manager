package il.ac.hit.Model;

import java.util.ArrayList;

public class person {
    private float totalExpenses;
    public ArrayList<category> categoryArrayList;
    public person(float totalExpenses) {
        this.totalExpenses = totalExpenses;
        this.categoryArrayList = new ArrayList<>();
    }
    public person(float totalExpenses, ArrayList categoryArrayList) {
        setTotalExpenses(totalExpenses);
        setCategoryArrayList(categoryArrayList);
       total_Expenses();
    }


    public  void adding_Category(category new_Catgory){
        categoryArrayList.add(new_Catgory);

    }


    public float getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(float totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
    public void total_Expenses() {
            totalExpenses =0;
        for (int i = 0; i < categoryArrayList.size(); i++) {
            totalExpenses += categoryArrayList.get(i).getCategoryExpenses();

        }
    }
    public void print_All_The_Category(){
        for(int i=0;i<categoryArrayList.size();i++){
            System.out.println("name category: "+categoryArrayList.get(i).getNameCategory());
            System.out.println("category Expenses: "+categoryArrayList.get(i).getCategoryExpenses());
        }

    }
    public ArrayList<category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public void setCategoryArrayList(ArrayList<category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }

}
