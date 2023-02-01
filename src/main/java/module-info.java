module org.app {
    requires javafx.controls;
    requires javafx.fxml;
    opens org.app to javafx.fxml;
    exports org.app;
    exports org.app.iniziale;
    opens org.app.iniziale to javafx.fxml;
    exports org.app.acquisto;
    opens org.app.acquisto to javafx.fxml;
    exports org.app.utilities;
    opens org.app.utilities to javafx.fxml;
}