module com.example.javafxsuppermarketapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.tunisiamarket to javafx.fxml;
    exports com.example.tunisiamarket;
    exports ViewControl;
    opens ViewControl;
    exports Control;
    opens Control;
    opens ModelProduct;

}
