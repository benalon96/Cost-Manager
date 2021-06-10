package il.ac.hit.Model;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class DBConnection implements IModel {

    static public ArrayList<category> categoryArrayList = new ArrayList<>();
    static public List<String> categoryNames = new ArrayList<>();
    static public ArrayList<product> productArrayList;
    static public il.ac.hit.Model.person person;
    final String databaseConnectionString = "jdbc:derby:";
    private String dbName = "derbyDB";

    public DBConnection() throws costManagerException {
        try {
            createTables(false);
        } catch (costManagerException e) {
            throw new costManagerException("ERROR Loading the DataBase", e);
        }

    }

    public DBConnection(String dbName) throws costManagerException {
        try {
            this.dbName = dbName; // derbyDB
            createTables(true);
        } catch (costManagerException e) {
            throw new costManagerException("ERROR Loading the DataBase", e);
        }

    }

    private void createTables(boolean forceRecreate) throws costManagerException {

        try (Connection conn = getConnection(true)) {
            DatabaseMetaData metadata = conn.getMetaData();

            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance();

            // drop tables if need
            if (forceRecreate && metadata.getTables(null, null, "PAYMENTS", null).next()) {
                conn.createStatement().execute("DROP TABLE PAYMENTS");
            }

            if (forceRecreate && metadata.getTables(null, null, "SAVEDREPORTS", null).next()) {
                conn.createStatement().execute("DROP TABLE SAVEDREPORTS");
            }

            if (forceRecreate && metadata.getTables(null, null, "CATEGORIES", null).next()) {
                conn.createStatement().execute("DROP TABLE CATEGORIES");
            }

            // create tables
            if (!metadata.getTables(null, null, "PAYMENTS", null).next()) {
                conn.createStatement().execute("CREATE TABLE PAYMENTS (\n" +
                        "    ItemID varchar(36),\n" +
                        "    nameProduct varchar(255),\n" +
                        "    priceProduct REAL,\n" +
                        "    Category varchar(255),\n" +
                        "    Currency varchar(255),\n" +
                        "    Date BIGINT,\n" +
                        "    PRIMARY KEY (itemID)\n" +
                        ")");
            }

            if (!metadata.getTables(null, null, "SAVEDREPORTS", null).next()) {
                conn.createStatement().execute("CREATE TABLE SAVEDREPORTS (\n" +
                        "    ReportName varchar(255),\n" +
                        "    StartDate BIGINT,\n" +
                        "    EndDate BIGINT,\n" +
                        "    CreationDate BIGINT\n" +
                        ")");
            }

            if (!metadata.getTables(null, null, "CATEGORIES", null).next()) {
                conn.createStatement().execute("CREATE TABLE CATEGORIES (\n" +
                        "    Category varchar(255),\n" +
                        "unique (Category))");
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw new costManagerException("DB access failed", e);
        }

    }

    private Connection getConnection() throws costManagerException {
            return getConnection(false);
    }

    private Connection getConnection(boolean toCreate) throws costManagerException {
        try {
            String url = databaseConnectionString + dbName;
            if (toCreate) {
                url = url + ";create=true";
            }
            return DriverManager.getConnection(url, null);
        } catch (SQLException e) {
            throw new costManagerException("SQL access failed", e);

        }
    }


    public void addPayment(product product) throws costManagerException {

        try (Connection conn = getConnection()) {
            String query = "insert into PAYMENTS (ItemID, nameProduct ,priceProduct, Category,Currency,Date)\n" +
                    "values (" +
                    "'" + product.getProductID() + "'," +
                    "'" + product.getNameProduct() + "'," +
                    product.getPriceProduct() + "," +
                    "'" + product.getCategoryName() + "'," +
                    "'" + product.getCurrencyType().name() + "'," +
                    product.getDateProduct().getTime() + ")";
            System.out.println(query);//todo delete
            conn.createStatement().execute(query);

        } catch (SQLException e) {
            System.out.println(e);
            throw new costManagerException("DB access failed", e);
        }
    }


    public void getProductArrayListFromDB() throws costManagerException {
        productArrayList=new ArrayList<>();
        ArrayList<product> listFromDb = new ArrayList<>();
        listFromDb = null;
        product product1 = null;
        try (Connection conn = getConnection();
        ) {
            ResultSet resultSet = conn.createStatement().executeQuery("select * from PAYMENTS");
            product product;
            float priceProduct = 0;
            String currency = null;
            currencyType currencyType = null;
            String ItemID = null;
            String nameProduct = null;
            String category = null;
            java.sql.Date Date = null;
            while (resultSet.next()) {

                nameProduct = resultSet.getString("nameProduct");
                priceProduct = resultSet.getFloat("priceProduct");
                category = resultSet.getString("Category");
                currency = resultSet.getString("Currency");
                long date = resultSet.getLong("Date");
                currencyType = currencyType.valueOf(currency);
//                Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(Date);
                Date date1 = new Date(date);
//                listFromDb.add(new Product(nameProduct, priceProduct, date1, category, currencyType));
                productArrayList.add(new product(nameProduct, priceProduct, date1, category, currencyType));

            }



        } catch (SQLException e) {
            System.out.println(e);
            throw new costManagerException("DB access failed", e);
        }

    }


    public void deletePayment(String itemId) throws costManagerException {

        if (isNullOrBlank(itemId))

            try (Connection conn = getConnection()) {
                conn.createStatement().execute("delete from PAYMENTS where ItemID='" + itemId + "' \n");//

            } catch (SQLException e) {
                System.out.println(e);
                throw new costManagerException("DB access failed", e);
            }
    }



    public List<String> getCategories() throws costManagerException {

        List<String> categories = null;
        try (Connection conn = getConnection();
        ) {
            ResultSet resultSet = conn.createStatement().executeQuery("select Category from CATEGORIES");
            categories = new ArrayList<String>();
            while (resultSet.next()) {
                categories.add(resultSet.getString("Category"));
            }

        } catch (SQLException e) {
            System.out.println(e);
            throw new costManagerException("DB access failed", e);
        }

        return categories;
    }


    public void addCategory(String categoryName) throws costManagerException {

        if (!isNullOrBlank(categoryName))


            try (Connection conn = getConnection()) {

                String query = "insert into CATEGORIES (Category)\n" +
                        "values ('" +
                        categoryName + "')";
                System.out.println(query);//todo delete
                conn.createStatement().execute(query);

            } catch (SQLException e) {
                System.out.println(e);
                throw new costManagerException("DB access failed", e);
            }
    }

    @Override
    public void getPersonFromDB() throws costManagerException {

        getProductArrayListFromDB();
      categoryNames= getCategories();
      for(int i=0;i<categoryNames.size();i++){
          category category=new category(categoryNames.get(i),0);
         categoryArrayList.add(category);
      }
        for (int i = 0; i < categoryArrayList.size(); i++) {

            for (int j = 0; j < productArrayList.size(); j++) {

                if (categoryArrayList.get(i).getNameCategory().equals(productArrayList.get(j).getCategoryName())) {
                    categoryArrayList.get(i).addingProduct(productArrayList.get(j));
                }

            }
        }
        person=new person(0,categoryArrayList);

    }


    private static boolean isNullOrBlank(String param) {
        if (param == null || param.trim().length() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<product> getReport(java.util.Date date1, java.util.Date date2) throws costManagerException {
        ArrayList<product> result = null;
        try (Connection conn = getConnection()) {

            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * from PAYMENTS where Date>=" + date1.getTime() + " AND Date<=" + date2.getTime() + " \n");//todo
            result = new ArrayList<>();
            float priceProduct = 0;
            String currency = null;
            currencyType currencyType = null;
            String ItemID = null;
            String nameProduct = null;
            String category = null;
            java.sql.Date Date = null;
            while (resultSet.next()) {

                nameProduct = resultSet.getString("nameProduct");
                priceProduct = resultSet.getFloat("priceProduct");
                category = resultSet.getString("Category");
                currency = resultSet.getString("Currency");
                long date = resultSet.getLong("Date");
                Date dateOfProduct = new Date(date);
                currencyType = currencyType.valueOf(currency);
                result.add(new product(nameProduct, priceProduct, dateOfProduct, category, currencyType));

            }
            System.out.println(resultSet);
        } catch (SQLException e) {
            System.out.println(e);
            throw new costManagerException("DB access failed", e);
        }

        return result;
    }
}

//    @Override
//    public void saveReport(Date date1, Date date2, String reportName) throws CostManagerException {
//        validateDates(date1, date2);
//        if (isNullOrBlank(reportName)) throw new CostManagerException("reportName is missing");
//
//        Date currentDate = new Date();
//        try (Connection conn = getConnection()) {
//            String query = "insert into SAVEDREPORTS (reportName ,StartDate,endDate,creationDate)\n" +
//                    "values (" +
//                    "'" + reportName + "'," +
//                    date1.getTime() + "," +
//                    date2.getTime() + "," +
//                    currentDate.getTime() + ")";
//            System.out.println(query);//todo delete
//            conn.createStatement().execute(query);
//
//        } catch (SQLException e) {
//            System.out.println(e);
//            throw new CostManagerException("DB access failed", e);
//        }
//    }

//    @Override
//    public List<SavedReport> getSavedReports() throws CostManagerException {
//        List<SavedReport> result = null;
//        try (Connection conn = getConnection();
//        ) {
//            ResultSet resultSet = conn.createStatement().executeQuery("select * from SAVEDREPORTS order by creationDate");
//            result = new LinkedList<>();
//            while (resultSet.next()) {
//                SavedReport report = new SavedReport(
//                        resultSet.getString("ReportName"),
//                        new Date(resultSet.getLong("StartDate")),
//                        new Date(resultSet.getLong("EndDate")),
//                        new Date(resultSet.getLong("CreationDate")));
//                result.add(report);
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e);
//            throw new CostManagerException("DB access failed", e);
//        }
//
//        return result;
//    }
//



//    @Override
//    public List getPieChartReport(Date date1, Date date2) throws CostManagerException {
//
//        validateDates(date1, date2);
//
//        ResultSet resultSetSum = null;
//        List<String> categories = getCategories();
//        List<Double> sums = new ArrayList<Double>();
//
//        try (Connection conn = getConnection()) {
//            for (int i = 0; i < categories.size(); i++) {
//
//
//                resultSetSum = conn.createStatement().executeQuery("SELECT SUM(Sum2) from PAYMENTS where Date>=" + date1.getTime() + " AND Date<=" + date2.getTime()
//                        + "AND Category =" + categories.get(i) + " \n;");
//                sums.add(resultSetSum.getDouble(1));
//
//            }
//            System.out.println(resultSetSum);
//        } catch (SQLException e) {
//            System.out.println(e);
//            throw new CostManagerException("DB access failed", e);
//        }
//
//        return sums;
//    }


//    private static void validateDates(Date date1, Date date2) throws CostManagerException {
//        if (date1.getTime() > date2.getTime())
//            throw new CostManagerException("please input correct dates");
//    }
//
//    private static boolean validateCostItem(CostItem costItem){
//        return !isNullOrBlank(costItem.getCategory()) &&
//                !isNullOrBlank(costItem.getDescription()) &&
//                costItem.getSum() >= 0;
//    }