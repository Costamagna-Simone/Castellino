module org.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires MaterialFX;
    requires org.hsqldb;
    requires java.sql;
    opens org.app to javafx.fxml;
    exports org.app;
    exports org.app.iniziale;
    opens org.app.iniziale to javafx.fxml;
    exports org.app.acquisto;
    opens org.app.acquisto to javafx.fxml;
    exports org.app.vendita;
    opens org.app.vendita to javafx.fxml;
    exports org.app.raffronto;
    opens org.app.raffronto to javafx.fxml;
    exports org.app.utilities;
    opens org.app.utilities to javafx.fxml;
}