module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;    
    requires com.opencsv;
    requires javafx.base;
    requires org.kordamp.ikonli.javafx;    
    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}
    