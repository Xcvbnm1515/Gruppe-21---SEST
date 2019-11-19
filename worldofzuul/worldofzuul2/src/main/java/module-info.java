module worldofzuul.presentation {
    requires javafx.controls;
    requires javafx.fxml;

    opens worldofzuul.presentation to javafx.fxml;
    exports worldofzuul.presentation;
}