package il.ac.hit.Model;
import java.util.Date;
import java.util.UUID;


public class product {

        private String nameProduct;
        private float priceProduct;
        private Date dateProduct;
        private String categoryName;
        private currencyType currencyType;
        private String productID;

        public product(String name_Product, float price_Product, Date dateProduct, String categoryName, currencyType currencyType) {
        setNameProduct(name_Product);
        setPriceProduct(price_Product);
        setDateProduct(dateProduct);
        setCategoryName(categoryName);
        setCurrencyType(currencyType);
        this.productID = UUID.randomUUID().toString();
    }
    public product(String nameProduct, float priceProduct, Date dateProduct, String categoryName, currencyType currencyType, String productID) {
        setNameProduct(nameProduct);
        setPriceProduct(priceProduct);
        setDateProduct(dateProduct);
        setCategoryName(categoryName);
        setCurrencyType(currencyType);
       setProductID(productID);
    }
    @Override
    public String toString() {
        return "Product{" +
                "name_Product='" + nameProduct + '\'' +
                ", price_Product=" + priceProduct +
                ", date_Product=" + dateProduct +
                ", category_name='" + categoryName + '\'' +
                ", currency_Type=" + currencyType +
                ", productID='" + productID + '\'' +
                '}';
    }


    public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public void setPriceProduct(float priceProduct) {
            this.priceProduct = priceProduct;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }



    public float getPriceProduct() {
        return priceProduct;
    }

    public void setPrice_Product(int price_Product) {
        this.priceProduct = price_Product;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Date getDateProduct() {
        return dateProduct;
    }

    public void setDateProduct(Date dateProduct) {
        this.dateProduct = dateProduct;
    }

    public currencyType getCurrencyType() {
        return currencyType;
    }


    public void setCurrencyType(currencyType currencyType) {
        this.currencyType = currencyType;
    }
}

