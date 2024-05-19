package ViewControl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Control.ServicesControl;
import ModelProduct.ProductModel;

public class ServicesFormControl implements Initializable {

    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDiscount;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnBack;
    @FXML
    private Button print;
    @FXML
    private ComboBox<String> table; // Utilisation de types génériques pour ComboBox
    @FXML
    private TextArea bill;
    @FXML
    private Label ltotal;

    int numProduct;
    double total = 0;
    int num = 0;
    ServicesControl SC = new ServicesControl();
    ProductModel pr = new ProductModel();

    public void back(Event e) {
        try {
            Node node = (Node) e.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void search() { // Renommage de la méthode en minuscules selon les conventions de nommage Java
        table.getItems().clear();
        table.getItems().addAll(SC.getSearchNamesProduct(txtSearch.getText()));
    }

    public void clickTable(Event e) {
        String product = table.getSelectionModel().getSelectedItem();
        pr = SC.getProduct(product);
        txtPrice.setText(String.valueOf(pr.getPrice()));
        txtDiscount.setText(String.valueOf(pr.getDiscount()));
        numProduct = pr.getNumber();
    }

    public void buy() {
        String s = bill.getText();
        SC.update(table.getValue(), numProduct - Integer.parseInt(txtNumber.getText()));
        bill.setText(s + "Name Of Product : " + table.getValue() + "\n" + "Price Of Product : " + txtPrice.getText() + "\n"
                + "Discount Of Product : " + txtDiscount.getText() + "%\n--------------------------------------\n");

        txtNumber.setText("");
        double x = Double.parseDouble(txtPrice.getText());
        int y = Integer.parseInt(txtDiscount.getText());
        total += x - (x * (y / 100.0));
        ltotal.setText(String.valueOf(total));
    }

    public void print() {
        try {
            num++;
            PrintWriter f = new PrintWriter("bill " + String.valueOf(num) + ".txt");
            f.println(bill.getText());
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServicesFormControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        bill.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bill != null) {
            bill.setText("*****************************  فاتوره******************************\n");
        } else {
            // Handle the case where bill is null, such as logging an error or displaying a message
            System.err.println("Error: Bill text area is null.");
        }

        if (table != null) {
            table.getItems().addAll(SC.getNamesProduct());
        } else {
            // Handle the case where table is null, such as logging an error or displaying a message
            System.err.println("Error: Table is null.");
        }

        num = 0;
    }


}
