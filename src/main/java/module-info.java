module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;    
    requires com.opencsv;
    requires javafx.base;    
    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}
    