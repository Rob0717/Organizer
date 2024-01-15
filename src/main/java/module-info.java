module home.uursp {
    requires javafx.controls;
    requires javafx.fxml;

    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;

    opens home.uursp1 to javafx.fxml;
    exports home.uursp1;
}