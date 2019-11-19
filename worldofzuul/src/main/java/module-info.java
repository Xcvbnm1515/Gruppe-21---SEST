module worldofzuul.presentation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens worldofzuul.presentation to javafx.fxml;
    exports worldofzuul.presentation;
}