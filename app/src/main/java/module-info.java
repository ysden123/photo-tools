module com.stulsoft.photo.tools {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    requires org.apache.commons.imaging;

    opens com.stulsoft.photo.tools to javafx.fxml;

    exports com.stulsoft.photo.tools;
}