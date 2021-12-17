module com.demo.reversi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.json;


    opens com.demo.reversi to javafx.fxml;
    exports com.demo.reversi;
}