package ViewControl;


import Control.ProductControl;
import ModelProduct.ProductModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class ProductFormControl implements Initializable {

    @FXML
    private Label lb ;
    @FXML
    private TextField txtName ;
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtDiscount ;
    @FXML
    private TextField txtSearch ;
    @FXML
    private ComboBox Type ;
    @FXML
    private Button btnAdd ;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnBack;
    @FXML
    private TableView table;
    @FXML
    private TableColumn id;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn number;
    @FXML
    private TableColumn price;
    @FXML
    private TableColumn type;
    @FXML
    private TableColumn discount;


    ProductControl pc = new ProductControl();
    int ID;
    String  TypeProduct[]={"foods","drinks","fruit","vegetables","sweets","other"};
    ObservableList<String> olType =FXCollections.observableArrayList(TypeProduct);

    public void search(Event e)
    {
        table.setItems(pc.getSearchProduct(txtSearch.getText()));

    }
    public void add(Event e) {
        if (!txtName.getText().isEmpty() && !txtNumber.getText().isEmpty() && !txtPrice.getText().isEmpty()) {
            ProductModel product = new ProductModel();
            product.setName(txtName.getText());
            product.setNumber(Integer.parseInt(txtNumber.getText()));
            product.setPrice(Double.parseDouble(txtPrice.getText()));
            product.setType(Type.getValue().toString());

            // Vérification du champ de réduction
            if (!txtDiscount.getText().isEmpty()) {
                try {
                    product.setDiscount(Integer.parseInt(txtDiscount.getText()));
                } catch (NumberFormatException ex) {
                    // Gestion de l'erreur si la valeur de la réduction n'est pas un entier
                    System.err.println("La réduction doit être un entier valide.");
                    return; // Arrête l'exécution de la méthode si la réduction n'est pas valide
                }
            }

            // Insertion du produit dans la base de données
            pc.insert(product);

            // Réinitialisation des champs de saisie
            txtName.clear();
            txtNumber.clear();
            txtPrice.clear();
            txtDiscount.clear();

            // Rafraîchissement de la table des produits
            table.setItems(pc.getAllProduct());
        }
    }

    public void update(Event e)
    {
            ProductModel product = new ProductModel();
            if(!txtName.getText().equals("")&&!txtNumber.getText().equals("")&&!txtPrice.getText().equals("")){
                product.setName(txtName.getText());
                product.setNumber(Integer.parseInt(txtNumber.getText()));
                product.setPrice(Double.parseDouble(txtPrice.getText()));
                product.setDiscount(Integer.parseInt(txtDiscount.getText()));
                product.setType(Type.getValue().toString());
                product.setId(ID);

                pc.update(product);

                txtName.setText("");
                txtNumber.setText("");
                txtPrice.setText("");
                txtDiscount.setText("");

                table.setItems(pc.getAllProduct());
            }
    }

    public void delete(Event e){
            //  ProductModel product = new ProductModel();
            pc.delete(ID);

            txtName.setText("");
            txtNumber.setText("");
            txtPrice.setText("");
            txtDiscount.setText("");

            table.setItems(pc.getAllProduct());

    }
    public void Back(Event e)
    {
            try {
                //add you loading or delays - ;-)
                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception ex) {
                System.out.println("y"+ex.getMessage());
            }
    }


    public void ClickTable(Event e){
            ProductModel product =  (ProductModel) table.getSelectionModel().getSelectedItem();
            txtName.setText(product.getName());
            txtNumber.setText(product.getNumber()+"");
            txtPrice.setText(product.getPrice()+"");
            txtDiscount.setText(product.getDiscount()+"");
            ID=product.getId();
            Type.setValue(product.getType());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


            //refere to attribute

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            number.setCellValueFactory(new PropertyValueFactory<>("number"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

            //show data
            table.setItems(pc.getAllProduct());
            Type.getItems().addAll(olType);
    }
}
