package Control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ModelProduct.ProductModel;



public class ServicesControl {

    Statement state;


    public void update(ProductModel product) {
        try {
            Connection connection = ConnectionDB.openConnection(); // Obtiens une nouvelle connexion
            state = connection.createStatement();
            state.executeUpdate("UPDATE product SET name = '" + product.getName() + "', number = " + product.getNumber() + ", price = " + product.getPrice() + ", type = '" + product.getType() + "', discount = " + product.getDiscount() + " WHERE id = " + product.getId());
            ConnectionDB.closeConnection(connection); // Ferme la connexion
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ObservableList<String> getNamesProduct()
    {
        ObservableList<String> product =FXCollections.observableArrayList();
        try {
            state = ConnectionDB.openConnection().createStatement();
            ResultSet result =  state.executeQuery("SELECT name FROM Products");



            while(result.next())
            {
                ProductModel pr = new ProductModel();
                product.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }

    public ObservableList<String> getSearchNamesProduct(String name) {
        ObservableList<String> product = FXCollections.observableArrayList();
        Connection connection = null; // Déclaration de la connexion
        try {
            connection = ConnectionDB.openConnection();
            state = connection.createStatement();
            ResultSet result = state.executeQuery("SELECT name FROM Products WHERE name LIKE '%" + name + "%'");

            while (result.next()) {
                product.add(result.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionDB.closeConnection(connection);
            }
        }

        return product;
    }



    public ProductModel getProduct(String name)
    {
        ProductModel pr = new ProductModel();

        try {
            state = ConnectionDB.openConnection().createStatement();
            ResultSet result =  state.executeQuery("SELECT * FROM Products where name = '"+name+"'");



            while(result.next())
            {
                // if define object out while will store last row n time
                pr.setId(result.getInt(1));
                pr.setName(result.getString(2));
                pr.setNumber(result.getInt(3));
                pr.setPrice(result.getDouble(4));
                pr.setType(result.getString(5));
                pr.setDiscount(result.getInt(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicesControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pr;
    }


    public void update(String value, int i) {
    }
}
