package il.ac.hit.Model;

import java.util.ArrayList;

/**
 * Category class is the category of the products
 */
public class category {

    private String nameCategory;
    private ArrayList<product> productArrayList;
    private float categoryExpenses;


    /**
     * Function that crate a list to a category
     */
    public category(String nameCategory, float category_expenses) {
        setNameCategory(nameCategory);     // crate the category name
        this.productArrayList = new ArrayList<>(); // crate the category list of products
        setCategoryExpenses(category_expenses);
    }


    @Override
    public String toString() {
        return "Category{" +
                "name Category='" + nameCategory + '\'' +
                ", product Array List=" + productArrayList +
                ", category Expenses=" + categoryExpenses +
                '}';
    }

    //getters and setters

    public ArrayList<product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    /**
     * Function that adding a product to a list and his price
     */
    public void addingProduct(product new_product){
        productArrayList.add(new_product); //adding a product to a list
        if(new_product.getCurrencyType()== currencyType.USD){ // Check whether the price is in dollars
            categoryExpenses += (new_product.getPriceProduct()*3.5);//Updates accordingly
        }
         if(new_product.getCurrencyType()== currencyType.EURO){ //Check whether the price is in euros
            categoryExpenses += (new_product.getPriceProduct()*4);//Updates accordingly
        }
        if(new_product.getCurrencyType()== currencyType.ILS){ //Check whether the price is in shekels
            categoryExpenses += new_product.getPriceProduct();//Updates accordingly
        }
    }

    /**
     * Function that display the products in the list
     */
    public void print_Product_List(){
        System.out.println("category Expenses: "+ getCategoryExpenses()+"");
    for (int i=0;i<productArrayList.size();i++){
        System.out.println("price product: "+productArrayList.get(i).getPriceProduct()) ;
        System.out.println("name product: "+productArrayList.get(i).getNameProduct()) ;
        System.out.println("date product: "+productArrayList.get(i).getDateProduct()) ;
        System.out.println("Currency Type product: "+productArrayList.get(i).getCurrencyType()) ;
    }
    }
    //getters and setters
    public Float getCategoryExpenses() {
        return categoryExpenses;
    }

    public void setCategoryExpenses(float categoryExpenses) {
        this.categoryExpenses = categoryExpenses;
    }

}
