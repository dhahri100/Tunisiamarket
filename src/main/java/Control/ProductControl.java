package Control;


import ModelProduct.ProductModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductControl {

    Statement state ;

    public void insert(ProductModel product) {
        try {
            Connection connection = ConnectionDB.openConnection(); // Obtiens une nouvelle connexion
            state = connection.createStatement();
            state.executeUpdate("INSERT INTO product (name, number, price, type, discount) VALUES ('" + product.getName() + "', " + product.getNumber() + ", " + product.getPrice() + ", '" + product.getType() + "', " + product.getDiscount() + ")");
            ConnectionDB.closeConnection(connection); // Ferme la connexion
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        try {
            Connection connection = ConnectionDB.openConnection(); // Obtiens une nouvelle connexion
            state = connection.createStatement();
            state.executeUpdate("DELETE FROM product WHERE id = " + id);
            ConnectionDB.closeConnection(connection); // Ferme la connexion
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    // return all data as observable list because table parmetar is observable
    public ObservableList<ProductModel> getAllProduct() {
        ObservableList<ProductModel> product = FXCollections.observableArrayList();
        try {
            Connection connection = ConnectionDB.openConnection(); // Obtiens une nouvelle connexion
            state = connection.createStatement();
            ResultSet result =  state.executeQuery("SELECT * FROM product");

            while (result.next()) {
                ProductModel pr = new ProductModel();
                pr.setId(result.getInt(1));
                pr.setName(result.getString(2));
                pr.setNumber(result.getInt(3));
                pr.setPrice(result.getDouble(4));
                pr.setType(result.getString(5));
                pr.setDiscount(result.getInt(6));
                product.add(pr);
            }
            ConnectionDB.closeConnection(connection); // Ferme la connexion
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }



    public ObservableList<ProductModel> getSearchProduct(String name) {
        ObservableList<ProductModel> product = FXCollections.observableArrayList();
        try {
            Connection connection = ConnectionDB.openConnection(); // Obtiens une nouvelle connexion
            state = connection.createStatement();
            ResultSet result =  state.executeQuery("SELECT * FROM product WHERE name LIKE '%" + name + "%'");

            while (result.next()) {
                ProductModel pr = new ProductModel();
                pr.setId(result.getInt(1));
                pr.setName(result.getString(2));
                pr.setNumber(result.getInt(3));
                pr.setPrice(result.getDouble(4));
                pr.setType(result.getString(5));
                pr.setDiscount(result.getInt(6));
                product.add(pr);
            }
            ConnectionDB.closeConnection(connection); // Ferme la connexion
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }




}
